package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.controller.FreePolyController.POLY_BATCH_SIZE;

import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
@Slf4j
public class EnumerateFreePolyServiceAsync {

  @NonNull
  private final EnumerateFreePolyServiceImpl enumerateFreePolyService;

  public Flux<FreePolyominoesResponse> enumerate(int n, String sessionId) {
    UUID polysId = UUID.randomUUID();
    log.info("Start enumerating. Number of blocks: {} for session id: {}", n, sessionId);
    List<int[]> polys = enumerateFreePolyService.enumerate(n).stream()
        .toList();
    log.info("Sending data");
    return Flux.fromIterable(polys)
        .buffer(POLY_BATCH_SIZE)
        .doOnNext(polyList -> log.info("Number of polys: {}", polyList.size()))
        .map(polyList -> FreePolyominoesResponse.builder()
            .numberOfPolys(polys.size())
            .numberOfBlocks(n)
            .polysId(polysId)
            .freePolys(EnumerateFreePolyUtil.getMatrices(polyList))
            .build())
        .doOnComplete(
            () -> log.info(
                "Finish enumerating. Number of blocks: {}, Number of polys: {} for session id: {}",
                n,
                polys.size(), sessionId));
  }
}
