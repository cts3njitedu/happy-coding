package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceStringRecursionTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceStringRecursion enumerateFreePolyServiceStringRecursion = new EnumerateFreePolyServiceStringRecursion();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyServiceStringRecursion);
  }
}