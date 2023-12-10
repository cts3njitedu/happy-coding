package com.craig.scholar.happy.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.craig.scholar.happy.model.BibleRequest;
import com.craig.scholar.happy.model.BibleResponse;
import com.craig.scholar.happy.service.codeexchange.bible.BibleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/bible")
@Slf4j
@RequiredArgsConstructor
public class BibleController {

  @NonNull
  private final BibleService bibleService;

  @PostMapping("/getText")
  public @ResponseBody BibleResponse getText(@RequestBody BibleRequest request) {
    log.info("Get text for reference {}", request.getReferenceId());
    try {
      return BibleResponse.builder()
          .text(bibleService.getPassage(request.getReferenceId()))
          .build();
    } catch (Exception ex) {
      throw new ResponseStatusException(
          BAD_REQUEST, "Invalid Request", ex);
    }

  }
}
