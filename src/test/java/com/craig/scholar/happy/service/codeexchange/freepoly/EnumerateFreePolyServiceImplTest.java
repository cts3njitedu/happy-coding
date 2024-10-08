package com.craig.scholar.happy.service.codeexchange.freepoly;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceImplTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceImpl enumerateFreePolyService = new EnumerateFreePolyServiceImpl();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyService);
  }

  @Test
  void enumerate_individual() {
    System.out.println(enumerateFreePolyService.enumerate(10).size());
  }

  @Test
  void enumerateFreePolyominoes_NumberGreaterThan15_Exception() {
    assertThatThrownBy(() -> enumerateFreePolyService.enumerate(17))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please pass in positive number less than or equal")
        .hasMessageContaining("17");
  }

  @Test
  void enumerateFreePolyominoes_NegativeNumber_Exception() {
    assertThatThrownBy(() -> enumerateFreePolyService.enumerate(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please pass in positive number less than or equal")
        .hasMessageContaining("-1");
  }
}