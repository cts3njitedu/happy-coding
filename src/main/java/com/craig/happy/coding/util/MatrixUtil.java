package com.craig.happy.coding.util;

import java.util.List;

public class MatrixUtil {

  public static final Integer ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT = 0;
  public static final Integer ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT = 1;
  public static final Integer NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT = 2;
  public static final Integer NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT = 3;
  public static final Integer ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT = 4;
  public static final Integer ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT = 5;
  public static final Integer TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT = 6;
  public static final Integer TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT = 7;

  public static final Integer MAX_TRANSFORMATIONS = 8;

  public static boolean isCongruent(boolean[][] matrix1, boolean[][] matrix2) {
    boolean[] transformations = getCongruentTransformations(matrix1, matrix2);
    if (hasEqualTransformation(transformations)) {
      for (int i = 0; i < matrix1.length; i++) {
        for (int j = 0; j < matrix1[0].length; j++) {
          checkIfTransformationsAreEqual(matrix1, matrix2, transformations, i, j);
        }
      }
      return hasEqualTransformation(transformations);
    }
    return false;
  }

  private static boolean[] getCongruentTransformations(boolean[][] matrix1, boolean[][] matrix2) {
    boolean areMatricesSimiliar = (matrix1.length == matrix2.length)
        && (matrix1[0].length == matrix2[0].length);
    boolean areMatricesRotated = (matrix1.length == matrix2[0].length)
        && (matrix1[0].length == matrix2.length);
    boolean[] transformations = new boolean[MAX_TRANSFORMATIONS];
    transformations[ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesSimiliar;
    transformations[ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesSimiliar;
    transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesSimiliar;
    transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesSimiliar;
    transformations[NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesRotated;
    transformations[NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesRotated;
    transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesRotated;
    transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesRotated;
    return transformations;
  }

  private static boolean hasEqualTransformation(boolean[] transformations) {
    for (boolean b : transformations) {
      if (b) {
        return true;
      }
    }
    return false;
  }

  private static void checkIfTransformationsAreEqual(boolean[][] matrix1, boolean[][] matrix2,
      boolean[] transformations, int i, int j) {
    if (transformations[ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT] = matrix1[i][j] == matrix2[i][j];
    }
    if (transformations[ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT] = matrix1[i][j] == matrix2[i][
          matrix2[0].length - 1 - j];
    }
    if (transformations[NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT] =
          matrix1[i][j] == matrix2[j][matrix2[0].length - 1 - i];
    }
    if (transformations[NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - j][matrix2[0].length - 1 - i];
    }
    if (transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - i][j];
    }
    if (transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - i][matrix2[0].length - 1 - j];
    }
    if (transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT] =
          matrix1[i][j] == matrix2[j][i];
    }
    if (transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT] = matrix1[i][j] == matrix2[
          matrix2.length - 1 - j][i];
    }
  }

  public static boolean[][] centerMatrix(boolean[][] matrix) {
    int rows = matrix.length;
    int columns = matrix[0].length;
    boolean startRowFromZero = true;
    boolean startColumnFromZero = true;
    for (int i = 0; i < matrix[0].length; i++) {
      if (matrix[0][i] || matrix[matrix.length - 1][i]) {
        if (matrix[0][i]) {
          rows++;
          startRowFromZero = false;
        }
        if (matrix[matrix.length - 1][i]) {
          rows++;
        }
        break;
      }
    }
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i][0] || matrix[i][matrix[0].length - 1]) {
        if (matrix[i][0]) {
          columns++;
          startColumnFromZero = false;
        }
        if (matrix[i][matrix[0].length - 1]) {
          columns++;
        }
        break;
      }
    }
    boolean[][] largerMatrix = new boolean[rows][columns];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        largerMatrix[startRowFromZero ? i : i + 1][startColumnFromZero ? j : j + 1] = matrix[i][j];
      }
    }
    return largerMatrix;
  }

  public static void print(int n, List<boolean[][]> matrices, String trueFlag) {
    System.out.printf("Number: %d, Count: %d%n\n", n, matrices.size());
    System.out.println("############");
    matrices
        .forEach(matrix -> {
          for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
              System.out.print((matrix[i][j] ? trueFlag : "".repeat(trueFlag.length())) + "");
            }
            System.out.println();
          }
          System.out.println("**************");
        });
    System.out.println("############");
  }
}
