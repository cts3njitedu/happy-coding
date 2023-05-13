package com.craig.happy.coding.util;

import java.util.Arrays;

public class MatrixUtil {

  public static boolean isCongruent(boolean[][] matrix, boolean[][] newMatrix) {
    boolean[] isEqual = new boolean[8];
    Arrays.fill(isEqual, true);
    boolean isMatrixSizeEqual = (matrix.length == newMatrix.length)
        && (matrix[0].length == newMatrix[0].length);
    boolean isMatrixRotateEqual = (matrix.length == newMatrix[0].length)
        && (matrix[0].length == newMatrix.length);
    isEqual[0] = isEqual[1] = isEqual[4] = isEqual[5] = isMatrixSizeEqual;
    isEqual[2] = isEqual[3] = isEqual[6] = isEqual[7] = isMatrixRotateEqual;
    if (isMatrixSizeEqual || isMatrixRotateEqual) {
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          if (isEqual[0]) {
            isEqual[0] = matrix[i][j] == newMatrix[i][j];
          }
          if (isEqual[1]) {
            isEqual[1] = matrix[i][j] == newMatrix[i][newMatrix[0].length - 1 - j];
          }
          if (isEqual[2]) {
            isEqual[2] = matrix[i][j] == newMatrix[j][newMatrix[0].length - 1 - i];
          }
          if (isEqual[3]) {
            isEqual[3] =
                matrix[i][j] == newMatrix[newMatrix.length - 1 - j][newMatrix[0].length - 1 - i];
          }
          if (isEqual[4]) {
            isEqual[4] = matrix[i][j] == newMatrix[newMatrix.length - 1 - i][j];
          }
          if (isEqual[5]) {
            isEqual[5] =
                matrix[i][j] == newMatrix[newMatrix.length - 1 - i][newMatrix[0].length - 1 - j];
          }
          if (isEqual[6]) {
            isEqual[6] = matrix[i][j] == newMatrix[j][i];
          }
          if (isEqual[7]) {
            isEqual[7] = matrix[i][j] == newMatrix[newMatrix.length - 1 - j][i];
          }
        }
      }
      for (boolean b : isEqual) {
        if (b) {
          return true;
        }
      }
    }
    return false;
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
