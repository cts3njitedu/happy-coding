package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceBooleanMatrixTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceBooleanMatrix enumerateFreePolyServiceBooleanMatrix = new EnumerateFreePolyServiceBooleanMatrix();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyServiceBooleanMatrix);
  }
}