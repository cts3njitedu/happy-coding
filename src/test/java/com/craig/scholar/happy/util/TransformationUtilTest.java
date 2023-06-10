package com.craig.scholar.happy.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TransformationUtilTest {

  private static Stream<Arguments> transformationCases() {
    return Stream.of(
        Arguments.of(
            new int[]{7, 2, 3},
            List.of(
                new int[]{7, 2, 3},
                new int[]{7, 2, 6},
                new int[]{1, 7, 5},
                new int[]{5, 7, 1},
                new int[]{6, 2, 7},
                new int[]{3, 2, 7},
                new int[]{5, 7, 4},
                new int[]{4, 7, 5}
            )
        ),
        Arguments.of(
            new int[]{8, 14, 11},
            List.of(
                new int[]{8, 14, 11},
                new int[]{1, 7, 13},
                new int[]{7, 2, 6, 4},
                new int[]{4, 6, 2, 7},
                new int[]{13, 7, 1},
                new int[]{11, 14, 8},
                new int[]{1, 3, 2, 7},
                new int[]{7, 2, 3, 1}
            )
        )
    );

  }

  @ParameterizedTest
  @MethodSource("transformationCases")
  void testTransformations(int[] rows, List<int[]> expectedTransformations) {
    List<String> actualTransformations = TransformationUtil.getTransformations(rows);
    assertThat(expectedTransformations.size()).isEqualTo(actualTransformations.size());
    List<String> expected = expectedTransformations.stream()
        .map(Arrays::toString)
        .sorted()
        .toList();
    List<String> actual = actualTransformations.stream()
        .sorted()
        .toList();
    assertThat(expected).isEqualTo(actual);
  }
}
