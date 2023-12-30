package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyState;
import com.craig.scholar.happy.model.FreePolyominoesRequest;
import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceAsync;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceImpl;
import com.craig.scholar.happy.service.storage.FreePolyStorageService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
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

  public static final String FREE_POLY_QUEUE = "/queue/poly";
  public static final int POLY_BATCH_SIZE = 100000;
  @NonNull
  private final EnumerateFreePolyServiceAsync enumerateFreePolyServiceAsync;

  @NonNull
  private final EnumerateFreePolyServiceImpl enumerateFreePolyService;

  @NonNull
  private final FreePolyStorageService freePolyStorageService;

  @NonNull
  private final SimpMessagingTemplate simpMessagingTemplate;

//  @PostMapping("/enumerate")
//  public @ResponseBody FreePolyominoesResponse enumerate(
//      @RequestBody FreePolyominoesRequest request) {
////    Collection<int[]> matrices = getPolys(request);
//    enumerateFreePolyServiceAsync.enumerate(request.getNumberOfBlocks(), request.getSessionId());
//    return FreePolyominoesResponse.builder()
//        .numberOfBlocks(request.getNumberOfBlocks())
//        .polys(List.of())
//        .polysId(Optional.ofNullable(request.getPolysId())
//            .orElseGet(UUID::randomUUID))
//        .freePolyState(FreePolyState.START)
//        .numberOfPolys(1)
//        .build();
//  }

  @PostMapping(path = "/enumerate", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public @ResponseBody Flux<FreePolyominoesResponse> enumerate(
      @RequestBody FreePolyominoesRequest request) {
//    Collection<int[]> matrices = getPolys(request);
    return enumerateFreePolyServiceAsync.enumerateFlux(request.getNumberOfBlocks(),
        request.getSessionId());
  }

  @MessageMapping("/poly")
  @SendToUser(FREE_POLY_QUEUE)
  public FreePolyominoesResponse sendEnumerate(@Payload FreePolyominoesRequest request,
      @Header("simpSessionId") String sessionId) {
    UUID polysId = UUID.randomUUID();
    log.info("Session: {}", sessionId);
    return FreePolyominoesResponse.builder()
        .numberOfPolys(1)
        .numberOfBlocks(request.getNumberOfBlocks())
        .polysId(polysId)
        .sessionId(sessionId)
        .freePolyState(FreePolyState.START)
        .build();
//    List<int[]> polys =  enumerateFreePolyService.enumerate(request.getNumberOfBlocks()).stream()
//        .toList();
//    for (int i = 0; i < polys.size(); i = i + POLY_BATCH_SIZE) {
//      log.info("Sending data");
//      convertAndSendToUser(FreePolyominoesResponse.builder()
//          .numberOfPolys(1)
//          .numberOfBlocks(1)
//          .polysId(polysId)
//          .polys(EnumerateFreePolyUtil.getMatrices(polys.subList(i, Math.min(i+ POLY_BATCH_SIZE, polys.size()))))
//          .build(), sessionId);
//    }
//    convertAndSendToUser(FreePolyominoesResponse.builder()
//        .numberOfPolys(1)
//        .numberOfBlocks(1)
//        .polysId(polysId)
//        .freePolyState(FreePolyState.FINISH)
//        .build(), sessionId);
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

//  @SendToUser(value = "/queue/poly", broadcast = false)
//  public FreePolyominoesResponse broadCast(FreePolyDto freePolyDto) {
//    log.info("We are here let's celebrate");
//    return FreePolyominoesResponse.builder()
//        .polysId(freePolyDto.getPolysId())
//        .polys(freePolyDto.getPolys())
//        .numberOfPolys(freePolyDto.getNumberOfPolys())
//        .numberOfBlocks(freePolyDto.getNumberOfBlocks())
//        .build();
//  }

  private Collection<int[]> getPolys(FreePolyominoesRequest request) {
    log.info("Enumerate polyominoes with {} blocks", request.getNumberOfBlocks());
    Collection<int[]> matrices;
    if (Objects.isNull(request.getPolysId())) {
      matrices = enumerateFreePolyService.enumerate(request.getNumberOfBlocks());
    } else {
      matrices = new ArrayList<>();
    }
    log.info("Finish enumerating polyominoes. Number of polys: {}, Number of blocks: {}",
        matrices.size(), request.getNumberOfBlocks());
    return matrices;
  }

//  @EventListener
//  public void connectionEstablished(SessionConnectedEvent sce)
//  {
//    MessageHeaders msgHeaders = sce.getMessage().getHeaders();
//    Principal princ = (Principal) msgHeaders.get("simpUser");
//    StompHeaderAccessor sha = StompHeaderAccessor.wrap(sce.getMessage());
//    List<String> nativeHeaders = sha.getNativeHeader("userId");
//    if( nativeHeaders != null )
//    {
//      String userId = nativeHeaders.get(0);
//        log.info("Connessione websocket stabilita. ID Utente "+userId);
//    }
//    else
//    {
//      String userId = princ.getName();
//
//        log.info("Connessione websocket stabilita. ID Utente "+userId);
//    }
//  }
//
//  @EventListener
//  public void subscriptionEstablished(SessionSubscribeEvent sce)
//  {
//    MessageHeaders msgHeaders = sce.getMessage().getHeaders();
//    Principal princ = (Principal) msgHeaders.get("simpUser");
//    StompHeaderAccessor sha = StompHeaderAccessor.wrap(sce.getMessage());
//    List<String> nativeHeaders = sha.getNativeHeader("userId");
//    if( nativeHeaders != null )
//    {
//      String userId = nativeHeaders.get(0);
//      log.info("Connessione websocket stabilita. ID Utente "+userId);
//    }
//    else
//    {
//      String userId = princ.getName();
//
//      log.info("Connessione websocket stabilita. ID Utente "+userId);
//    }
//  }
}
