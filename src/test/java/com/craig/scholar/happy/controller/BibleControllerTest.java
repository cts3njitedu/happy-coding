package com.craig.scholar.happy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.craig.scholar.happy.model.BibleRequest;
import com.craig.scholar.happy.model.BibleResponse;
import com.craig.scholar.happy.service.codeexchange.bible.BibleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class BibleControllerTest {

  @Mock
  private BibleService bibleService;

  @InjectMocks
  private BibleController bibleController;

  @Test
  void getPassage() {
    when(bibleService.getPassage(any())).thenReturn("Bible verse");
    BibleResponse response = bibleController.getPassage(BibleRequest.builder()
        .reference("referenceId")
        .build());
    assertThat(response.getPassage()).isEqualTo("Bible verse");
  }

  @Test
  void getPassage_Exception() {
    when(bibleService.getPassage(any())).thenThrow(new IllegalArgumentException("Error"));
    assertThatThrownBy(() -> bibleController.getPassage(BibleRequest.builder()
        .reference("referenceId")
        .build()))
        .isInstanceOf(ResponseStatusException.class);
  }
}