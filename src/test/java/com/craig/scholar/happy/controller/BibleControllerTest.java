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
  void getText() {
    when(bibleService.getText(any())).thenReturn("Bible verse");
    BibleResponse response = bibleController.getText(BibleRequest.builder()
        .referenceId("referenceId")
        .build());
    assertThat(response.getText()).isEqualTo("Bible verse");
  }

  @Test
  void getText_Exception() {
    when(bibleService.getText(any())).thenThrow(new IllegalArgumentException("Error"));
    assertThatThrownBy(() -> bibleController.getText(BibleRequest.builder()
        .referenceId("referenceId")
        .build()))
        .isInstanceOf(ResponseStatusException.class);
  }
}