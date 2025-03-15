package com.craig.scholar.happy.service.codeexchange;

import org.junit.jupiter.api.Test;

class SternBrocotTest {

  SternBrocot sternBrocot = new SternBrocot();

  @Test
  void execute() {
    sternBrocot.execute(null);
  }

  @Test
  void r_n() {
    for (int i = 1; i <= 10; i++) {
      System.out.printf("n=%d, r(n)=%s\n", i, sternBrocot.r_n(i));
    }
  }
}