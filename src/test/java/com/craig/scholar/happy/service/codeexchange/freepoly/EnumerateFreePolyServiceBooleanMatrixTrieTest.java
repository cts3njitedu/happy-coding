package com.craig.scholar.happy.service.codeexchange.freepoly;

import org.junit.jupiter.api.Test;

class EnumerateFreePolyServiceBooleanMatrixTrieTest extends EnumerateFreePolyServiceTest {

  private final EnumerateFreePolyServiceBooleanMatrixTrie enumerateFreePolyServiceBooleanMatrixTrie = new EnumerateFreePolyServiceBooleanMatrixTrie();

  @Test
  void enumerate() {
    enumerate(enumerateFreePolyServiceBooleanMatrixTrie);
  }
}