package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceIntArrayStackIterativeTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceIntArrayStackIterative enumerateFreePolyServiceIntArrayStackIterative = new EnumerateFreePolyServiceIntArrayStackIterative();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyServiceIntArrayStackIterative);
  }
}