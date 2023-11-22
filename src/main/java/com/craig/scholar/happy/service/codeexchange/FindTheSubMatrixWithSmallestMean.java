package com.craig.scholar.happy.service.codeexchange;

public class FindTheSubMatrixWithSmallestMean implements HappyCoding {

  @Override
  public void execute() {

  }

  public double smallestMean(double[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 1; j < matrix[0].length; j++) {
        matrix[i][j] += matrix[i][j - 1];
      }
    }
    double min = Double.MAX_VALUE;
    for (int i = 0; i + 2 < matrix.length; i++) {
      for (int j = 0; j + 2 < matrix[0].length; j++) {
        double total = 0;
        for (int k = i; k <= i + 2; k++) {
          total += matrix[k][j + 2] - (j == 0 ? 0 : matrix[k][j - 1]);
        }
        min = Math.min(min, total);
      }
    }
    return min / 9.0;
  }
}
