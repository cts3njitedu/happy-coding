package com.craig.scholar.happy.controller;

import com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceTest;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class FreePolyControllerTest extends EnumerateFreePolyServiceTest {

  @Test
  void getEnumerateFlux() {
    Flux.range(1, 65)
        .log()
        .limitRate(10)
        .delayElements(Duration.ofMillis(5))
        .subscribe(System.out::println);
  }
}