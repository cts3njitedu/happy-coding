package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyDto;
import com.craig.scholar.happy.model.FreePolyominoesRequest;
import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceImpl;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import com.craig.scholar.happy.service.storage.FreePolyStorageService;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/poly")
@Slf4j
@RequiredArgsConstructor
public class FreePolyController {

  @NonNull
  private final EnumerateFreePolyServiceImpl enumerateFreePolyService;

  @NonNull
  private final FreePolyStorageService freePolyStorageService;

  @PostMapping("/enumerate")
  public @ResponseBody FreePolyominoesResponse enumerate(
      @RequestBody FreePolyominoesRequest request) {
    Collection<int[]> matrices = getPolys(request);
    return FreePolyominoesResponse.builder()
        .numberOfBlocks(request.getNumberOfBlocks())
        .polys(EnumerateFreePolyUtil.getMatrices(matrices))
        .polysId(Optional.ofNullable(request.getPolysId())
            .orElseGet(UUID::randomUUID))
        .numberOfPolys(matrices.size())
        .build();
  }

  @MessageMapping("/poly")
  @SendToUser("/queue/poly")
  public FreePolyominoesResponse sendEnumerate(@Payload FreePolyominoesRequest request) {
    UUID polysId = UUID.randomUUID();
    Collection<int[]> polys = getPolys(request);
    freePolyStorageService.saveFreePolys(polys, request.getNumberOfBlocks(), polysId);
    return FreePolyominoesResponse.builder()
        .numberOfPolys(polys.size())
        .numberOfBlocks(request.getNumberOfBlocks())
        .polysId(polysId)
        .build();
  }

  private Collection<int[]> getPolys(FreePolyominoesRequest request) {
    log.info("Enumerate polyominoes with {} blocks", request.getNumberOfBlocks());
    Collection<int[]> matrices;
    if (Objects.isNull(request.getPolysId())) {
      matrices = enumerateFreePolyService.enumerate(request.getNumberOfBlocks());
    } else {
      matrices = freePolyStorageService.getFreePolys(request.getPolysId())
          .map(FreePolyDto::getPolys)
          .orElse(List.of());
      freePolyStorageService.deleteFreePolys(request.getPolysId());
    }
    log.info("Finish enumerating polyominoes. Number of polys: {}, Number of blocks: {}",
        matrices.size(), request.getNumberOfBlocks());
    return matrices;
  }
}
