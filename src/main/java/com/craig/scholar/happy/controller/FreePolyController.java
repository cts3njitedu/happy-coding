package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.model.FreePolyState;
import com.craig.scholar.happy.model.FreePolyominoesRequest;
import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.model.PolyHead;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceAsync;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import com.craig.scholar.happy.service.storage.FreePolyRabbitService;
import com.craig.scholar.happy.service.storage.FreePolySqlService;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/poly")
@Slf4j
@RequiredArgsConstructor
public class FreePolyController {

  public static final int POLY_BATCH_SIZE = 200000;
  @NonNull
  private final EnumerateFreePolyServiceAsync enumerateFreePolyServiceAsync;

  @NonNull
  private final FreePolyRabbitService freePolyRabbitService;

  @NonNull
  private final FreePolySqlService freePolySqlService;


  @PostMapping("/enumerate")
  public void enumerate(
      @RequestBody FreePolyominoesRequest request) {
    enumerateFreePolyServiceAsync.enumerate(request.getNumberOfBlocks(), request.getSessionId());
  }

  @PostMapping("/v2/enumerate")
  public void enumerateV2(
      @RequestBody FreePolyominoesRequest request) {
    enumerateFreePolyServiceAsync.enumerateSql(request.getNumberOfBlocks(), request.getSessionId());
  }

  @PostMapping("/getFreePolys")
  public FreePolyominoesResponse getFreePolys(@RequestBody FreePolyominoesRequest request) {
    FreePolyDto freePolyDto = freePolySqlService.findByPolyHeadId(
        request.getPolysId().toString(), request.getPolysPerPage(), request.getPage());
    return FreePolyominoesResponse.builder()
        .freePolys(freePolyDto.getFreePolys())
        .numberOfBlocks(freePolyDto.getNumberOfBlocks())
        .numberOfPolys(freePolyDto.getNumberOfPolys())
        .polysId(freePolyDto.getPolysId())
        .sessionId(freePolyDto.getSessionId())
        .build();
  }
  @GetMapping(path = "/queue/enumerate/{sessionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public @ResponseBody Flux<FreePolyominoesResponse> getEnumerateFlux(
      @PathVariable String sessionId) {
    log.info("Streaming events to session: {}", sessionId);
    return freePolyRabbitService.getPolys(sessionId)
        .map(freePolyDto -> FreePolyominoesResponse.builder()
            .freePolys(EnumerateFreePolyUtil.getMatrices(freePolyDto.getFreePolysOld()))
            .polysId(freePolyDto.getPolysId())
            .numberOfPolys(freePolyDto.getNumberOfPolys())
            .numberOfBlocks(freePolyDto.getNumberOfBlocks())
            .freePolyState(freePolyDto.getFreePolyState())
            .build())
        .doOnError(
            err -> log.error("Unable to retrieve polyominoes for sessionId: {}", sessionId, err))
        .onErrorReturn(FreePolyominoesResponse.builder()
            .polys(List.of())
            .polysId(UUID.randomUUID())
            .numberOfBlocks(0)
            .numberOfPolys(0)
            .freePolyState(FreePolyState.ERROR)
            .build());
  }

  @MessageMapping("/poly")
  @SendToUser("/queue/poly")
  public FreePolyominoesResponse sendEnumerate(@Payload FreePolyominoesRequest request,
      @Header("simpSessionId") String sessionId) throws Exception {
    log.info("Receiving message for session: {}", sessionId);
    PolyHead polyHead = enumerateFreePolyServiceAsync.enumerateSql(request.getNumberOfBlocks(),
        sessionId);
    log.info("Sending message to session: {}", sessionId);
    return FreePolyominoesResponse.builder()
        .numberOfPolys(polyHead.getNumberOfPolys())
        .numberOfBlocks(polyHead.getNumberOfBlocks())
        .sessionId(sessionId)
        .polysId(polyHead.getPolyHeadId())
        .build();
  }
}
