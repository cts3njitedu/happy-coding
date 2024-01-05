package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.model.FreePolyominoesRequest;
import com.craig.scholar.happy.model.FreePolyominoesResponse;
import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceAsync;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/poly")
@Slf4j
@RequiredArgsConstructor
public class FreePolyController {

  @NonNull
  private final EnumerateFreePolyServiceAsync enumerateFreePolyServiceAsync;

  @PostMapping(path = "/enumerate", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<FreePolyominoesResponse> enumerate(
      @RequestBody FreePolyominoesRequest request) {
    return enumerateFreePolyServiceAsync.enumerate(request.getNumberOfBlocks(),
        request.getSessionId());
  }

}
