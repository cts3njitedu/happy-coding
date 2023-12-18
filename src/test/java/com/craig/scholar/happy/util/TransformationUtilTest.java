package com.craig.scholar.happy.util;

import static com.craig.scholar.happy.util.TransformationUtil.flip;
import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;
import static com.craig.scholar.happy.util.TransformationUtil.reflect;
import static com.craig.scholar.happy.util.TransformationUtil.rotate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
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
    List<String> actualTransformations = getTransformations(rows);
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

  @Test
  void testFlipped() {
    int[] rows = {4, 6, 2, 7};
    int[] expected = {7, 2, 6, 4};
    assertThat(expected).isEqualTo(flip(rows));
  }

  @Test
  void testReflected() {
    int[] rows = {4, 6, 2, 7};
    int[] expected = {1, 3, 2, 7};
    assertThat(expected).isEqualTo(reflect(rows));
  }

  @Test
  void testRotated() {
    int[] rows = {8, 14, 11};
    int[] expected = {7, 2, 6, 4};
    assertThat(expected).isEqualTo(rotate(rows));
  }


  private static Stream<Arguments> transformationCasesMatrix() {
    return Stream.of(
        Arguments.of(
            new boolean[][]{
                {true, true, true},
                {false, true, false},
                {false, true, true}

            },
            List.of(
                new boolean[][]{
                    {true, true, true},
                    {false, true, false},
                    {false, true, true}
                },
                new boolean[][]{
                    {true, true, true},
                    {false, true, false},
                    {true, true, false}
                },
                new boolean[][]{
                    {false, false, true},
                    {true, true, true},
                    {true, false, true}
                },
                new boolean[][]{
                    {true, false, true},
                    {true, true, true},
                    {false, false, true}
                },
                new boolean[][]{
                    {false, true, true},
                    {false, true, false},
                    {true, true, true}
                },
                new boolean[][]{
                    {true, true, false},
                    {false, true, false},
                    {true, true, true}
                },
                new boolean[][]{
                    {true, false, false},
                    {true, true, true},
                    {true, false, true}
                },
                new boolean[][]{
                    {true, false, true},
                    {true, true, true},
                    {true, false, false}
                }
            )
        ),
        Arguments.of(
            new boolean[][]{
                {true, false, false, false},
                {true, true, true, false},
                {true, false, true, true}
            },
            List.of(
                new boolean[][]{
                    {true, false, false, false},
                    {true, true, true, false},
                    {true, false, true, true}
                },
                new boolean[][]{
                    {false, false, false, true},
                    {false, true, true, true},
                    {true, true, false, true}
                },
                new boolean[][]{
                    {true, true, true},
                    {false, true, false},
                    {true, true, false},
                    {true, false, false}
                },
                new boolean[][]{
                    {true, false, false},
                    {true, true, false},
                    {false, true, false},
                    {true, true, true}
                },
                new boolean[][]{
                    {true, false, true, true},
                    {true, true, true, false},
                    {true, false, false, false}
                },
                new boolean[][]{
                    {true, true, false, true},
                    {false, true, true, true},
                    {false, false, false, true}
                },
                new boolean[][]{
                    {true, true, true},
                    {false, true, false},
                    {false, true, true},
                    {false, false, true}
                },
                new boolean[][]{
                    {false, false, true},
                    {false, true, true},
                    {false, true, false},
                    {true, true, true}
                }

            )
        )
    );

  }

  @ParameterizedTest
  @MethodSource("transformationCasesMatrix")
  void testGetTransformations(boolean[][] matrix, List<boolean[][]> expectedRotatedMatrices) {
    List<boolean[][]> rotateMatrices = getTransformations(matrix);
    assertEquals(expectedRotatedMatrices.size(), rotateMatrices.size());
    for (int i = 0; i < expectedRotatedMatrices.size(); i++) {
      assertArrayEquals(expectedRotatedMatrices.get(i), rotateMatrices.get(i), "Unequal " + i);
    }
  }

  @Test
  void testGetTransformationsBitSet() {
    BitSet[] matrix = {
        BitSet.valueOf(new byte[]{10}),
        BitSet.valueOf(new byte[]{12}),
        BitSet.valueOf(new byte[]{3})
    };
    System.out.println(getTransformations(matrix));
  }
}
