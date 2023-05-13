package com.craig.happy.coding.util;

public class MatrixUtil {

  public static final Integer ZERO_DEGREE = 0;
  public static final Integer ZERO_REFLECT_DEGREE = 1;
  public static final Integer NINETY_DEGREE = 2;
  public static final Integer NINETY_REFLECT_DEGREE = 3;
  public static final Integer ONE_EIGHTY_DEGREE = 4;
  public static final Integer ONE_EIGHTY_REFLECT_DEGREE = 5;
  public static final Integer TWO_SEVENTY_DEGREE = 6;
  public static final Integer TWO_SEVENTY_REFLECT_DEGREE = 7;

  public static final Integer MAX_TRANSFORMATIONS = 8;

  public static boolean isCongruent(boolean[][] matrix1, boolean[][] matrix2) {
    boolean isMatrixSizeEqual = (matrix1.length == matrix2.length)
        && (matrix1[0].length == matrix2[0].length);
    boolean isMatrixRotateEqual = (matrix1.length == matrix2[0].length)
        && (matrix1[0].length == matrix2.length);
    if (isMatrixSizeEqual || isMatrixRotateEqual) {
      boolean[] transformations = new boolean[MAX_TRANSFORMATIONS];
      initializeTransformations(isMatrixSizeEqual, isMatrixRotateEqual, transformations);
      for (int i = 0; i < matrix1.length; i++) {
        for (int j = 0; j < matrix1[0].length; j++) {
          checkTransformations(matrix1, matrix2, transformations, i, j);
        }
      }
      for (boolean b : transformations) {
        if (b) {
          return true;
        }
      }
    }
    return false;
  }

  private static void initializeTransformations(boolean isMatrixSizeEqual,
      boolean isMatrixRotateEqual,
      boolean[] transformations) {
    transformations[ZERO_DEGREE] = transformations[ZERO_REFLECT_DEGREE] = transformations[ONE_EIGHTY_DEGREE] = transformations[ONE_EIGHTY_REFLECT_DEGREE] = isMatrixSizeEqual;
    transformations[NINETY_DEGREE] = transformations[NINETY_REFLECT_DEGREE] = transformations[TWO_SEVENTY_DEGREE] = transformations[TWO_SEVENTY_REFLECT_DEGREE] = isMatrixRotateEqual;
  }

  private static void checkTransformations(boolean[][] matrix1, boolean[][] matrix2,
      boolean[] transformations, int i, int j) {
    if (transformations[ZERO_DEGREE]) {
      transformations[ZERO_DEGREE] = matrix1[i][j] == matrix2[i][j];
    }
    if (transformations[ZERO_REFLECT_DEGREE]) {
      transformations[ZERO_REFLECT_DEGREE] = matrix1[i][j] == matrix2[i][
          matrix2[0].length - 1 - j];
    }
    if (transformations[NINETY_DEGREE]) {
      transformations[NINETY_DEGREE] = matrix1[i][j] == matrix2[j][matrix2[0].length - 1 - i];
    }
    if (transformations[NINETY_REFLECT_DEGREE]) {
      transformations[NINETY_REFLECT_DEGREE] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - j][matrix2[0].length - 1 - i];
    }
    if (transformations[ONE_EIGHTY_DEGREE]) {
      transformations[ONE_EIGHTY_DEGREE] = matrix1[i][j] == matrix2[matrix2.length - 1 - i][j];
    }
    if (transformations[ONE_EIGHTY_REFLECT_DEGREE]) {
      transformations[ONE_EIGHTY_REFLECT_DEGREE] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - i][matrix2[0].length - 1 - j];
    }
    if (transformations[TWO_SEVENTY_DEGREE]) {
      transformations[TWO_SEVENTY_DEGREE] = matrix1[i][j] == matrix2[j][i];
    }
    if (transformations[TWO_SEVENTY_REFLECT_DEGREE]) {
      transformations[TWO_SEVENTY_REFLECT_DEGREE] = matrix1[i][j] == matrix2[
          matrix2.length - 1 - j][i];
    }
  }


  public static boolean[][] getExpandedMatrix(boolean[][] matrix) {
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
}
