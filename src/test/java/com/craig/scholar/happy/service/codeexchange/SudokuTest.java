package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SudokuTest {

  Sudoku sudoku = new Sudoku();

  static Stream<Arguments> testCases() {
    return Stream.of(
        Arguments.of(
            new int[][]{
                {0, 0, 0, 0},
                {0, 4, 0, 3},
                {2, 0, 3, 0},
                {0, 0, 0, 0}
            },
            List.of(
                new int[][][]{
                    {
                        {3, 2, 4, 1},
                        {1, 4, 2, 3},
                        {2, 1, 3, 4},
                        {4, 3, 1, 2}
                    }
                }
            )
        ),
        Arguments.of(
            new int[][]{
                {0, 0, 4, 0, 0, 0},
                {0, 2, 0, 0, 4, 5},
                {0, 0, 0, 0, 2, 0},
                {0, 1, 0, 0, 0, 0},
                {3, 4, 0, 0, 5, 0},
                {0, 0, 0, 6, 0, 0}
            },
            List.of(
                new int[][][]{
                    {
                        {5, 3, 4, 2, 1, 6},
                        {6, 2, 1, 3, 4, 5},
                        {4, 6, 3, 5, 2, 1},
                        {2, 1, 5, 4, 6, 3},
                        {3, 4, 6, 1, 5, 2},
                        {1, 5, 2, 6, 3, 4}
                    }
                }
            )
        ),
        Arguments.of(
            new int[][]{
                {0, 5, 8, 0, 7, 0, 0, 0, 0},
                {0, 6, 0, 0, 2, 0, 0, 4, 0},
                {0, 0, 0, 0, 0, 0, 1, 3, 7},
                {6, 0, 0, 0, 0, 2, 0, 0, 0},
                {2, 0, 0, 1, 0, 4, 0, 0, 3},
                {1, 8, 0, 0, 0, 0, 9, 0, 0},
                {0, 4, 7, 0, 0, 9, 3, 5, 0},
                {0, 1, 0, 3, 0, 6, 0, 0, 8},
                {9, 0, 0, 0, 5, 0, 0, 2, 0}
            },
            List.of(
                new int[][][]{
                    {
                        {3, 5, 8, 4, 7, 1, 2, 6, 9},
                        {7, 6, 1, 9, 2, 3, 8, 4, 5},
                        {4, 2, 9, 5, 6, 8, 1, 3, 7},
                        {6, 9, 3, 7, 8, 2, 5, 1, 4},
                        {2, 7, 5, 1, 9, 4, 6, 8, 3},
                        {1, 8, 4, 6, 3, 5, 9, 7, 2},
                        {8, 4, 7, 2, 1, 9, 3, 5, 6},
                        {5, 1, 2, 3, 4, 6, 7, 9, 8},
                        {9, 3, 6, 8, 5, 7, 4, 2, 1}
                    }
                }
            )
        ),
        Arguments.of(
            new int[][]{
                {4, 0, 0, 0, 6, 8, 0, 2, 5},
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
                {5, 0, 0, 0, 4, 0, 0, 0, 6},
                {0, 0, 5, 8, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 7, 0, 0, 0, 9},
                {0, 0, 0, 1, 2, 0, 0, 0, 0},
                {6, 0, 0, 4, 0, 9, 3, 0, 0},
                {0, 3, 1, 0, 0, 0, 9, 0, 0},
                {2, 0, 0, 0, 0, 0, 0, 8, 0}
            },
            List.of(
                new int[][][]{
                    {
                        {4, 1, 3, 9, 6, 8, 7, 2, 5},
                        {7, 8, 6, 5, 3, 2, 4, 9, 1},
                        {5, 9, 2, 7, 4, 1, 8, 3, 6},
                        {1, 6, 5, 8, 9, 4, 2, 7, 3},
                        {3, 2, 8, 6, 7, 5, 1, 4, 9},
                        {9, 7, 4, 1, 2, 3, 6, 5, 8},
                        {6, 5, 7, 4, 8, 9, 3, 1, 2},
                        {8, 3, 1, 2, 5, 7, 9, 6, 4},
                        {2, 4, 9, 3, 1, 6, 5, 8, 7}
                    }
                }
            )
        ),
        Arguments.of(
            new int[][]{
                {3, 0, 10, 5, 0, 8, 4, 0, 0, 7},
                {0, 0, 0, 0, 0, 0, 0, 2, 1, 10},
                {0, 4, 5, 10, 0, 1, 3, 0, 0, 0},
                {1, 9, 0, 3, 0, 10, 8, 5, 0, 0},
                {0, 0, 0, 0, 9, 0, 2, 0, 5, 0},
                {0, 8, 3, 0, 5, 0, 1, 10, 7, 0},
                {0, 7, 0, 8, 3, 2, 0, 4, 0, 0},
                {5, 0, 0, 0, 2, 6, 0, 0, 0, 8},
                {0, 0, 8, 1, 0, 0, 0, 3, 0, 4},
                {7, 0, 0, 0, 0, 0, 6, 9, 8, 0}
            },
            List.of(
                new int[][][]{
                    {
                        {3, 2, 10, 5, 1, 8, 4, 6, 9, 7},
                        {8, 6, 9, 7, 4, 3, 5, 2, 1, 10},
                        {2, 4, 5, 10, 8, 1, 3, 7, 6, 9},
                        {1, 9, 6, 3, 7, 10, 8, 5, 4, 2},
                        {10, 1, 7, 6, 9, 4, 2, 8, 5, 3},
                        {4, 8, 3, 2, 5, 9, 1, 10, 7, 6},
                        {6, 7, 1, 8, 3, 2, 9, 4, 10, 5},
                        {5, 10, 4, 9, 2, 6, 7, 1, 3, 8},
                        {9, 5, 8, 1, 6, 7, 10, 3, 2, 4},
                        {7, 3, 2, 4, 10, 5, 6, 9, 8, 1}
                    }
                }
            )
        )
    );
  }


  @ParameterizedTest
  @MethodSource("testCases")
  void execute(int[][] board, List<int[][]> expectedSolutions) {
    List<int[][]> solutions = sudoku.execute(board);
    assertThat(solutions).hasSameSizeAs(expectedSolutions);
    for (int i = 0; i < solutions.size(); i++) {
      assertThat(solutions.get(i)).isDeepEqualTo(expectedSolutions.get(i));
    }
  }

}