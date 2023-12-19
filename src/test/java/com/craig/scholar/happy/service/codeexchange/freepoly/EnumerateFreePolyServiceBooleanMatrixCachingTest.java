package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceBooleanMatrixCachingTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceBooleanMatrixCaching enumerateFreePolyServiceBooleanMatrixCaching = new EnumerateFreePolyServiceBooleanMatrixCaching();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyServiceBooleanMatrixCaching);
  }
}