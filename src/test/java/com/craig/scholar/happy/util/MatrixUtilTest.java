package com.craig.scholar.happy.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.craig.scholar.happy.util.MatrixUtil.centerMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixUtilTest {

  private static Stream<Arguments> getCenteredMatrixCases() {
    return Stream.of(
        Arguments.of(
            new boolean[][]{
                {false, true, false},
                {false, true, false},
                {false, false, false}
            },
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false},
                {false, true, true},
                {false, false, false}
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, false, false}
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false}
            },
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false},
                {true, true, false},
                {false, false, false}
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, false, false}
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, true, false}
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, true, false},
                {false, false, false, false}
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, true, false, false},
                {false, true, true, false},
                {false, false, false, false}
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, false, false},
                {false, true, true, false},
                {false, false, false, false}
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false},
                {true, true, false},
                {false, true, false},
                {false, false, false},
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, true, false},
                {false, false, false, false},
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, true, false},
            },
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, true, true, false},
                {false, false, false, false}
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, true, false, false},
                {false, true, true, false},
                {false, true, true, true},
                {false, false, false, false}
            },
            new boolean[][]{
                {false, false, false, false, false},
                {false, true, false, false, false},
                {false, true, true, false, false},
                {false, true, true, true, false},
                {false, false, false, false, false}
            }
        ),
        Arguments.of(
            new boolean[][]{
                {false, true, false, false},
                {false, true, true, false},
                {true, true, true, true},
                {false, true, false, false}
            },
            new boolean[][]{
                {false, false, false, false, false, false},
                {false, false, true, false, false, false},
                {false, false, true, true, false, false},
                {false, true, true, true, true, false},
                {false, false, true, false, false, false},
                {false, false, false, false, false, false}
            }
        )
    );
  }

  @ParameterizedTest
  @MethodSource("getCenteredMatrixCases")
  void testGetExpandedMatrix(boolean[][] matrix, boolean[][] expectedCenteredMatrix) {
    boolean[][] actualCenteredMatrix = centerMatrix(matrix);
    assertEquals(actualCenteredMatrix.length, expectedCenteredMatrix.length);
    assertEquals(actualCenteredMatrix[0].length, expectedCenteredMatrix[0].length);
    for (int i = 0; i < expectedCenteredMatrix.length; i++) {
      for (int j = 0; j < expectedCenteredMatrix[0].length; j++) {
        assertEquals(expectedCenteredMatrix[i][j], actualCenteredMatrix[i][j]);
      }
    }
  }

  private static Stream<Arguments> isCongruentCases() {
    return Stream.of(
        Arguments.of(
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            },
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            },
            true
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, false, false},
            },
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            },
            true
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, false, true, true, false},
                {false, false, false, false, false},
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, false, false},
                {false, true, true, false},
                {false, false, true, false},
                {false, false, false, false},
            },
            true
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false, false, false},
                {false, true, true, false, false},
                {false, false, true, true, false},
                {false, false, false, false, false},
            },
            new boolean[][]{
                {false, false, false, false},
                {false, true, true, false},
                {false, false, true, false},
                {false, false, true, false},
                {false, false, false, false},
            },
            false
        ),
        Arguments.of(
            new boolean[][]{
                {false, false, false, false, false, false},
                {false, true, true, true, true, false},
                {false, false, false, false, false, false},
            },
            new boolean[][]{
                {false, false, false},
                {false, true, false},
                {false, true, false},
                {false, true, false},
                {false, true, false},
                {false, false, false},
            },
            true
        ),
        Arguments.of(
            new boolean[][]{
                {true},
                {true}
            },
            new boolean[][]{
                {true, true}
            },
            true
        ),
        Arguments.of(
            new boolean[][]{
                {true, false},
                {true, true}
            },
            new boolean[][]{
                {true, true},
                {false, true}
            },
            true
        )
    );
  }

  @ParameterizedTest
  @MethodSource("isCongruentCases")
  void testIsCongruent(boolean[][] matrix1, boolean[][] matrix2, boolean isCongruent) {
    assertEquals(isCongruent, isCongruent(matrix1, matrix2));
  }

}
