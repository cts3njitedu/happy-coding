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
  void getPassages() {
    when(bibleService.getPassages(any())).thenReturn("Bible verse");
    BibleResponse response = bibleController.getPassages(BibleRequest.builder()
        .reference("referenceId")
        .build());
    assertThat(response.getPassages()).isEqualTo("Bible verse");
  }

  @Test
  void getPassages_Exception() {
    when(bibleService.getPassages(any())).thenThrow(new IllegalArgumentException("Error"));
    assertThatThrownBy(() -> bibleController.getPassages(BibleRequest.builder()
        .reference("referenceId")
        .build()))
        .isInstanceOf(ResponseStatusException.class);
  }
}