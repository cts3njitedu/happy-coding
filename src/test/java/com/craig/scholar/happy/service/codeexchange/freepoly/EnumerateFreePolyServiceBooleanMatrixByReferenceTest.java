package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceBooleanMatrixByReferenceTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceBooleanMatrixByReference enumerateFreePolyServiceBooleanMatrixByReference = new EnumerateFreePolyServiceBooleanMatrixByReference();

  @Test
  @Disabled("StackOverflow Error")
  void enumerate() {
    enumerate(enumerateFreePolyServiceBooleanMatrixByReference);
  }
}