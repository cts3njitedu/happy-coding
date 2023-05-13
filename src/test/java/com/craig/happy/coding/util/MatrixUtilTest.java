package com.craig.happy.coding.util;

import static com.craig.happy.coding.util.MatrixUtil.getExpandedMatrix;
import static com.craig.happy.coding.util.MatrixUtil.isCongruent;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MatrixUtilTest {

  private static Stream<Arguments> getExpandedMatrixCases() {
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
  @MethodSource("getExpandedMatrixCases")
  void testGetExpandedMatrix(boolean[][] matrix, boolean[][] expectedExpandedMatrix) {
    boolean[][] actualExpandedMatrix = getExpandedMatrix(matrix);
    assertEquals(actualExpandedMatrix.length, expectedExpandedMatrix.length);
    assertEquals(actualExpandedMatrix[0].length, expectedExpandedMatrix[0].length);
    for (int i = 0; i < expectedExpandedMatrix.length; i++) {
      for (int j = 0; j < expectedExpandedMatrix[0].length; j++) {
        assertEquals(expectedExpandedMatrix[i][j], actualExpandedMatrix[i][j]);
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
        )
    );
  }

  @ParameterizedTest
  @MethodSource("isCongruentCases")
  void testIsCongruent(boolean[][] matrix1, boolean[][] matrix2, boolean isCongruent) {
    assertEquals(isCongruent, isCongruent(matrix1, matrix2));
  }

}
