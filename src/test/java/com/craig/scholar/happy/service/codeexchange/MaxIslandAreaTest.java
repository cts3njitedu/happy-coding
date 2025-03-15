package com.craig.scholar.happy.service.codeexchange;

import org.junit.jupiter.api.Test;

class MaxIslandAreaTest {

  MaxIslandArea maxIslandArea = new MaxIslandArea();

  @Test
  void execute() {
    System.out.println(maxIslandArea.execute(new int[][]{
        {1, 1, 1, 1},
        {1, 1, 1, 1},
        {1, 1, 1, 1},
        {1, 1, 1, 1}
    }));

  }
}