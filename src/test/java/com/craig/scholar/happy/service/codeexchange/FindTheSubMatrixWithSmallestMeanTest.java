package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.assertj.core.data.Offset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FindTheSubMatrixWithSmallestMeanTest {

  private final FindTheSubMatrixWithSmallestMean findTheSubMatrixWithSmallestMean = new FindTheSubMatrixWithSmallestMean();

  private static Stream<Arguments> meanCases() {
    return Stream.of(
        Arguments.of(
            new double[][]{
                {35, 1, 6, 26, 19, 24},
                {3, 32, 7, 21, 23, 25},
                {31, 9, 2, 22, 27, 20},
                {8, 28, 33, 17, 10, 15},
                {30, 5, 34, 12, 14, 16},
                {4, 36, 29, 13, 18, 11}
            },
            14
        ),
        Arguments.of(
            new double[][]{
                {100, 65, 2, 93},
                {3, 11, 31, 89},
                {93, 15, 95, 65},
                {77, 96, 72, 34},
            },
            46.111
        ),
        Arguments.of(
            new double[][]{
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1}
            },
            1
        ),
        Arguments.of(
            new double[][]{
                {4, 0, 0, 5, 4},
                {4, 5, 8, 4, 1},
                {1, 4, 9, 3, 1},
                {0, 0, 1, 3, 9},
                {0, 3, 2, 4, 8},
                {4, 9, 5, 9, 6},
                {1, 8, 7, 2, 7},
                {2, 1, 3, 7, 9}
            },
            2.2222
        )
    );
  }

  @ParameterizedTest
  @MethodSource("meanCases")
  void smallestMean(double[][] matrix, double expectedSmallestMean) {
    assertThat(findTheSubMatrixWithSmallestMean.smallestMean(matrix)).isCloseTo(
        expectedSmallestMean, Offset.offset(0.001d));
  }
}