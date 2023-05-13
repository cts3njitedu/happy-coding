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
    LinkedList<boolean[][]> freePolyominoes = new LinkedList<>();
    if (n < 1) {
      freePolyominoes.add(new boolean[][]{});
      return freePolyominoes;
    }
    boolean[][] a = new boolean[3][3];
    a[1][1] = true;
    freePolyominoes.add(a);
    for (int i = 2; i <= n; i++) {
      LinkedList<boolean[][]> newFreePolyominoes = new LinkedList<>();
      while (!freePolyominoes.isEmpty()) {
        boolean[][] polyomino = freePolyominoes.poll();
        for (int r = 0; r < polyomino.length; r++) {
          for (int c = 0; c < polyomino[0].length; c++) {
            if (polyomino[r][c]) {
              enumerate(newFreePolyominoes, polyomino, r - 1, c);
              enumerate(newFreePolyominoes, polyomino, r, c + 1);
              enumerate(newFreePolyominoes, polyomino, r + 1, c);
              enumerate(newFreePolyominoes, polyomino, r, c - 1);
            }
          }
        }
      }
      freePolyominoes.addAll(newFreePolyominoes);
    }
    return freePolyominoes;
  }

  private void enumerate(List<boolean[][]> freePolyominoes, boolean[][] matrix, int r, int c) {
    if (!matrix[r][c]) {
      matrix[r][c] = true;
      boolean[][] polyomino = getExpandedMatrix(matrix);
      matrix[r][c] = false;
      boolean isExist = isExist(freePolyominoes, polyomino);
      if (!isExist) {
        freePolyominoes.add(polyomino);
      }
    }
  }

  private boolean isExist(List<boolean[][]> expandedMatrices, boolean[][] matrix) {
    return expandedMatrices.stream()
        .anyMatch(expandedMatrix -> isCongruent(matrix, expandedMatrix));
  }

}
