package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.controller.FreePolyController.FREE_POLY_QUEUE;
import static com.craig.scholar.happy.controller.FreePolyController.POLY_BATCH_SIZE;

import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnumerateFreePolyServiceAsync {

  @NonNull
  private final EnumerateFreePolyServiceImpl enumerateFreePolyService;

  @NonNull
  private final SimpMessagingTemplate simpMessagingTemplate;

  public Flux<FreePolyominoesResponse> enumerateFlux(int n, String sessionId) {
    UUID polysId = UUID.randomUUID();
    List<int[]> polys = enumerateFreePolyService.enumerate(n).stream()
        .toList();
    log.info("Sending data");
    return Flux.fromIterable(polys)
        .buffer(POLY_BATCH_SIZE)
        .map(polyList -> FreePolyominoesResponse.builder()
            .numberOfPolys(polys.size())
            .numberOfBlocks(n)
            .polysId(polysId)
            .polys(EnumerateFreePolyUtil.getMatrices(polyList))
            .build())
        .doOnComplete(
            () -> log.info("Finish enumerating. Number of blocks: {}, Number of polys: {}", n,
                polys.size()));
  }

  @Async
  public void enumerate(int n, String sessionId) {
    UUID polysId = UUID.randomUUID();
    List<int[]> polys = enumerateFreePolyService.enumerate(n).stream()
        .toList();
//    for (int i = 0; i < polys.size(); i = i + POLY_BATCH_SIZE) {
    log.info("Sending data");
//    return Flux.fromIterable(polys)
//        .limitRate(10)
//        .delayElements(Duration.ofMillis(5000))
//        .map(poly -> {
//          log.info("Printing out stuff");
//          return FreePolyominoesResponse.builder()
//              .numberOfPolys(polys.size())
//              .numberOfBlocks(n)
//              .polysId(polysId)
//              .polys(List.of(EnumerateFreePolyUtil.getMatrix(poly)))
//              .build();
//        })
//        .doOnComplete(
//            () -> log.info("Finish enumerating. Number of blocks: {}, Number of polys: {}", n, polys.size()));
////      convertAndSendToUser(, sessionId);
////    }

    polys.forEach(poly -> convertAndSendToUser(FreePolyominoesResponse.builder()
        .numberOfPolys(polys.size())
        .numberOfBlocks(n)
        .polysId(polysId)
        .polys(List.of(EnumerateFreePolyUtil.getMatrix(poly)))
        .build(), sessionId));
//    for (int i = 0; i < polys.size(); i = i + 1000) {
//      simpMessagingTemplate.convertAndSendToUser(sessionId, "/queue/poly", FreePolyominoesResponse.builder()
//              .numberOfPolys(polys.size())
//              .numberOfBlocks(n)
//              .polysId(polysId)
//              .polys(List.of(EnumerateFreePolyUtil.getMatrix(polys[i])))
//              .build(););
//    }
  }

  private void convertAndSendToUser(FreePolyominoesResponse response, String sessionId) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
        .create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);
    headerAccessor.setLeaveMutable(true);
    headerAccessor.setHeader(SimpMessageHeaderAccessor.IGNORE_ERROR, true);
    simpMessagingTemplate.convertAndSendToUser(sessionId, FREE_POLY_QUEUE, response,
        headerAccessor.getMessageHeaders());
  }
}
