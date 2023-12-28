package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyominoesRequest;
import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceImpl;
import com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil;
import java.util.Collection;
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

  @PostMapping("/enumerate")
  public @ResponseBody FreePolyominoesResponse enumerate(
      @RequestBody FreePolyominoesRequest request) {
    log.info("Enumerate polyominoes with {} blocks", request.getNumberOfBlocks());
    Collection<int[][]> matrices = EnumerateFreePolyUtil.getMatrices(
        enumerateFreePolyService.enumerate(request.getNumberOfBlocks()));
    log.info("Finish enumerating polyominoes. Number of polys: {}, Number of blocks: {}",
        matrices.size(), request.getNumberOfBlocks());
    return FreePolyominoesResponse.builder()
        .numberOfBlocks(request.getNumberOfBlocks())
        .polys(matrices)
        .numberOfPolys(matrices.size())
        .build();
  }

  @MessageMapping("/poly")
  @SendToUser("/queue/poly")
  public FreePolyominoesResponse sendEnumerate(@Payload FreePolyominoesRequest request)
      throws Exception {
    return enumerate(request);
  }
}
