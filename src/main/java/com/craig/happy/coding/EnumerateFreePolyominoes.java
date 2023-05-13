package com.craig.happy.coding;

import static com.craig.happy.coding.util.MatrixUtil.getExpandedMatrix;
import static com.craig.happy.coding.util.MatrixUtil.isCongruent;

import java.util.LinkedList;
import java.util.List;

public class EnumerateFreePolyominoes {

  public int getNumberOfPolyominoes(int n) {
    return enumerate(n).size();
  }

  public List<boolean[][]> enumerate(int n) {
    LinkedList<boolean[][]> matrices = new LinkedList<>();
    if (n < 1) {
      matrices.add(new boolean[][]{});
      return matrices;
    }
    boolean[][] a = new boolean[3][3];
    a[1][1] = true;
    matrices.add(a);
    for (int i = 2; i <= n; i++) {
      LinkedList<boolean[][]> expandedMatrices = new LinkedList<>();
      while (!matrices.isEmpty()) {
        boolean[][] oldMatrix = matrices.poll();
        for (int r = 0; r < oldMatrix.length; r++) {
          for (int c = 0; c < oldMatrix[0].length; c++) {
            if (oldMatrix[r][c]) {
              enumerate(expandedMatrices, oldMatrix, r - 1, c);
              enumerate(expandedMatrices, oldMatrix, r, c + 1);
              enumerate(expandedMatrices, oldMatrix, r + 1, c);
              enumerate(expandedMatrices, oldMatrix, r, c - 1);
            }
          }
        }
      }
      matrices.addAll(expandedMatrices);
    }
    return matrices;
  }

  private void enumerate(List<boolean[][]> expandedMatrices, boolean[][] matrix, int r, int c) {
    if (!matrix[r][c]) {
      matrix[r][c] = true;
      boolean[][] expandedMatrix = getExpandedMatrix(matrix);
      matrix[r][c] = false;
      boolean isExist = isExist(expandedMatrices, expandedMatrix);
      if (!isExist) {
        expandedMatrices.add(expandedMatrix);
      }
    }
  }

  private boolean isExist(List<boolean[][]> expandedMatrices, boolean[][] matrix) {
    return expandedMatrices.stream()
        .anyMatch(expandedMatrix -> isCongruent(matrix, expandedMatrix));
  }

}
