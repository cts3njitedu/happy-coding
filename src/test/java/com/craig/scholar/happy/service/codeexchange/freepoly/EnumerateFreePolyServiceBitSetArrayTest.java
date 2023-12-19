package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceBitSetArrayTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceBitSetArray enumerateFreePolyServiceBitSetArray = new EnumerateFreePolyServiceBitSetArray();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyServiceBitSetArray);
  }
}