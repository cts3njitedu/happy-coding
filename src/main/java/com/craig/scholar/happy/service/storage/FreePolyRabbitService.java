package com.craig.scholar.happy.service.storage;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.model.FreePolyState;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ConsumeOptions;
import reactor.rabbitmq.CorrelableOutboundMessage;
import reactor.rabbitmq.OutboundMessageResult;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;

@Service
@Slf4j
@RequiredArgsConstructor
public class FreePolyRabbitService {

  @NonNull
  private final AmqpAdmin amqpAdmin;

  @NonNull
  private final AmqpTemplate amqpTemplate;

  @NonNull
  private final MessageListenerContainerFactory messageListenerContainerFactory;

  @NonNull
  private final Receiver receiver;

  @NonNull
  private final Sender sender;
  static final String EXCHANGE = "poly-exchange";

  public Mono<Boolean> savePolys(FreePolyDto freePolyDto, String sessionId) {
    return Flux.fromIterable(freePolyDto.getFreePolys())
        .map(poly -> FreePolyDto.builder()
            .polysId(freePolyDto.getPolysId())
            .freePolys(List.of(poly))
            .numberOfPolys(freePolyDto.getNumberOfPolys())
            .numberOfBlocks(freePolyDto.getNumberOfBlocks())
            .build())
        .transform(dtoFlux -> Flux.concat(dtoFlux,
            Flux.just(getFreePoly(freePolyDto, FreePolyState.FINISH))))
        .map(dto -> getMessage(sessionId, dto))
        .transform(sender::sendWithTypedPublishConfirms)
        .all(OutboundMessageResult::isAck);
  }

  private static FreePolyDto getFreePoly(FreePolyDto freePolyDto, FreePolyState freePolyState) {
    return FreePolyDto.builder()
        .freePolys(List.of())
        .numberOfPolys(freePolyDto.getNumberOfPolys())
        .numberOfBlocks(freePolyDto.getNumberOfBlocks())
        .freePolyState(freePolyState)
        .polysId(freePolyDto.getPolysId())
        .build();
  }

  private Mono<Boolean> sendMessage(FreePolyDto freePolyDto, String sessionId) {
    return Mono.just(getMessage(sessionId, freePolyDto))
        .flux()
        .transform(sender::sendWithTypedPublishConfirms)
        .all(OutboundMessageResult::isAck);
  }

  private static CorrelableOutboundMessage<FreePolyDto> getMessage(
      String sessionId, FreePolyDto dto) {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos)) {
      oos.writeObject(dto);
      byte[] byteArray = bos.toByteArray();
      return new CorrelableOutboundMessage<>(EXCHANGE, sessionId, byteArray, dto);
    } catch (Exception ex) {
      log.error("Error", ex);
      throw new RuntimeException(ex);
    }
  }

  public Flux<FreePolyDto> getPolys(String sessionId) {
    ConsumeOptions consumeOptions = new ConsumeOptions();
    return receiver.consumeAutoAck(sessionId, consumeOptions)
        .delaySubscription(sender.declareQueue(QueueSpecification.queue(sessionId)
                .autoDelete(true)
                .exclusive(true))
            .then(sender.bindQueue(
                BindingSpecification.queueBinding(EXCHANGE, sessionId, sessionId))))
        .map(delivery -> {
          try {
            ByteArrayInputStream bi = new ByteArrayInputStream(delivery.getBody());
            ObjectInputStream objectInputStream = new ObjectInputStream(bi);
            FreePolyDto freePolyDto = (FreePolyDto) objectInputStream.readObject();
            bi.close();
            objectInputStream.close();
            return freePolyDto;
          } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void createQueue(String sessionId) {
    QueueInformation queueInfo = amqpAdmin.getQueueInfo(sessionId);
    if (Objects.isNull(queueInfo)) {
      Queue queue = new Queue(sessionId);
      Binding binding = new Binding(sessionId, Binding.DestinationType.QUEUE, EXCHANGE, sessionId,
          null);
      amqpAdmin.declareQueue(queue);
      amqpAdmin.declareBinding(binding);
    }
  }

}
