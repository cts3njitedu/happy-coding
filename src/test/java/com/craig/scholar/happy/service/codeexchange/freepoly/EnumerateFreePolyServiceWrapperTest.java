package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.service.codeexchange.freepoly.EnumerateFreePolyServiceWrapper.POLY_BATCH_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class EnumerateFreePolyServiceWrapperTest extends EnumerateFreePolyServiceTest {

  @Mock
  private EnumerateFreePolyServiceImpl enumerateFreePolyService;

  @InjectMocks
  private EnumerateFreePolyServiceWrapper enumerateFreePolyServiceWrapper;

  @Test
  void testEnumerate() {
    when(enumerateFreePolyService.enumerate(anyInt())).thenReturn(getPolys(1));
    StepVerifier.create(enumerateFreePolyServiceWrapper.enumerate(1, "1"))
        .expectNextCount(1)
        .verifyComplete();
  }

  @Test
  void testEnumerateBatching() {
    when(enumerateFreePolyService.enumerate(anyInt())).thenReturn(
        getPolys(4 * POLY_BATCH_SIZE)
    );
    StepVerifier.create(enumerateFreePolyServiceWrapper.enumerate(1, "1"))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .verifyComplete();
  }

  @Test
  void testEnumerateBatchingLeftOver() {
    when(enumerateFreePolyService.enumerate(anyInt())).thenReturn(
        getPolys((int) (4.5 * POLY_BATCH_SIZE))
    );
    StepVerifier.create(enumerateFreePolyServiceWrapper.enumerate(1, "1"))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE))
        .assertNext(response -> assertThat(response.getFreePolys()).hasSize(POLY_BATCH_SIZE / 2))
        .verifyComplete();
  }

  private List<int[]> getPolys(int n) {
    return IntStream.rangeClosed(1, n)
        .mapToObj(i -> new int[]{i, i + 1, i + 2})
        .collect(Collectors.toList());
  }
}