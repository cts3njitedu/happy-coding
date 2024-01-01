package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.controller.FreePolyController.POLY_BATCH_SIZE;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.model.PolyHead;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import com.craig.scholar.happy.service.storage.FreePolyRabbitService;
import com.craig.scholar.happy.service.storage.FreePolySqlService;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final FreePolyRabbitService freePolyRabbitService;

  @NonNull
  private final FreePolySqlService freePolySqlService;

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
    log.info("Sending data for session: {}", sessionId);
    freePolyRabbitService.savePolys(FreePolyDto.builder()
            .polysId(polysId)
            .freePolysOld(polys)
            .numberOfBlocks(n)
            .numberOfPolys(polys.size())
            .build(), sessionId)
        .block();
  }

  public PolyHead enumerateSql(int n, String sessionId) {
    UUID polyGroupId = UUID.randomUUID();
    List<int[]> polys = enumerateFreePolyService.enumerate(n).stream()
        .toList();
    log.info("Sending data for session: {}. Number of Blocks: {}", sessionId, n);
    PolyHead polyHead = freePolySqlService.savePolys(FreePolyDto.builder()
        .polysId(polyGroupId)
        .freePolysOld(polys)
        .numberOfBlocks(n)
        .numberOfPolys(polys.size())
        .sessionId(sessionId)
        .build());
    return polyHead;
  }
}
