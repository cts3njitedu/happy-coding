package com.craig.scholar.happy.service.codeexchange;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MaximumSummedSubsequenceWithNonAdjacentItemsTest {

  private MaximumSummedSubsequenceWithNonAdjacentItems maximumSummedSubsequenceWithNonAdjacentItems = new MaximumSummedSubsequenceWithNonAdjacentItems();

  @Test
  void execute() {
    System.out.println(
        maximumSummedSubsequenceWithNonAdjacentItems.execute(new int[]{800,-31,0,0,421,726}));
  }
}