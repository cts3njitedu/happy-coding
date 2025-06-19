package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.Point;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SolveStarBattleQueensPuzzleTest {

  private final SolveStarBattleQueensPuzzle solveStarBattleQueensPuzzle = new SolveStarBattleQueensPuzzle();


  static Stream<Arguments> testCases() {
    return Stream.of(
        Arguments.of(
            new int[][]{
                {1, 1, 2, 2, 2, 2, 2, 2, 2},
                {1, 3, 3, 4, 4, 4, 5, 5, 2},
                {3, 3, 4, 4, 6, 4, 4, 5, 5},
                {3, 4, 4, 4, 6, 4, 4, 4, 5},
                {3, 4, 7, 7, 7, 4, 4, 4, 5},
                {3, 4, 4, 4, 4, 4, 4, 4, 5},
                {3, 8, 4, 4, 4, 4, 4, 9, 5},
                {3, 8, 8, 4, 4, 4, 9, 9, 5},
                {3, 3, 8, 8, 8, 8, 5, 5, 5}
            },
            List.of(
                new Point(0, 1),
                new Point(1, 8),
                new Point(2, 4),
                new Point(3, 0),
                new Point(4, 3),
                new Point(5, 5),
                new Point(6, 7),
                new Point(7, 2),
                new Point(8, 6)
            )
        ),
        Arguments.of(
            new int[][]{
                {1, 1, 2, 2, 2, 2, 2, 2, 3},
                {6, 1, 8, 8, 8, 8, 2, 2, 3},
                {1, 1, 1, 8, 8, 8, 8, 3, 3},
                {4, 4, 4, 7, 7, 8, 8, 3, 3},
                {4, 9, 4, 4, 7, 8, 8, 8, 3},
                {4, 9, 4, 7, 7, 7, 8, 3, 3},
                {4, 9, 9, 9, 9, 9, 5, 5, 3},
                {4, 9, 9, 9, 9, 9, 9, 5, 3},
                {9, 9, 9, 9, 9, 9, 5, 5, 5}
            },
            List.of(
                new Point(0, 4),
                new Point(1, 0),
                new Point(2, 2),
                new Point(3, 6),
                new Point(4, 3),
                new Point(5, 5),
                new Point(6, 8),
                new Point(7, 1),
                new Point(8, 7)
            )
        ),
        Arguments.of(
            new int[][]{
                {1, 1, 1, 1, 1, 1, 2, 2, 2},
                {1, 1, 1, 1, 1, 1, 1, 1, 3},
                {1, 4, 4, 4, 5, 1, 1, 1, 3},
                {1, 1, 1, 1, 5, 1, 1, 1, 3},
                {1, 1, 1, 1, 5, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 6, 6, 6, 1},
                {1, 1, 1, 1, 1, 1, 7, 7, 7},
                {1, 1, 8, 8, 8, 1, 1, 1, 1},
                {1, 9, 9, 9, 1, 1, 1, 1, 1}
            },
            List.of(
                new Point(0, 6),
                new Point(1, 8),
                new Point(2, 2),
                new Point(3, 4),
                new Point(4, 0),
                new Point(5, 5),
                new Point(6, 7),
                new Point(7, 3),
                new Point(8, 1)
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("testCases")
  void execute(int[][] board, List<Point> expectedCoordinates) {
    Assertions.assertThat(solveStarBattleQueensPuzzle.execute(board))
        .containsExactlyElementsOf(expectedCoordinates);
  }
}