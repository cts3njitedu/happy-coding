package com.craig.scholar.happy.util;

import static com.craig.scholar.happy.util.MatrixUtil.centerMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.getRotateMatrices;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

  private static Stream<Arguments> rotatedMatricesCases() {
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
  @MethodSource("rotatedMatricesCases")
  void testGetRotatedMatrices(boolean[][] matrix, List<boolean[][]> expectedRotatedMatrices) {
    List<boolean[][]> rotateMatrices = getRotateMatrices(matrix);
    assertEquals(expectedRotatedMatrices.size(), rotateMatrices.size());
    for (int i = 0; i < expectedRotatedMatrices.size(); i++) {
      assertArrayEquals(expectedRotatedMatrices.get(i), rotateMatrices.get(i), "Unequal " + i);
    }
  }
}
