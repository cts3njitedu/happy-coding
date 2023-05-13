package com.craig.happy.coding;

import static com.craig.happy.coding.util.MatrixUtil.getExpandedMatrix;
import static com.craig.happy.coding.util.MatrixUtil.isCongruent;

import java.util.LinkedList;
import java.util.List;

public class EnumerateFreePolyominoes {

  public int getNumberOfFreePolyominoes(int n) {
    return enumerateFreePolyominoes(n).size();
  }

  public List<boolean[][]> enumerateFreePolyominoes(int n) {
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
        boolean[][] freePolyomino = freePolyominoes.poll();
        for (int r = 0; r < freePolyomino.length; r++) {
          for (int c = 0; c < freePolyomino[0].length; c++) {
            if (freePolyomino[r][c]) {
              enumerateFreePolyominoes(newFreePolyominoes, freePolyomino, r - 1, c);
              enumerateFreePolyominoes(newFreePolyominoes, freePolyomino, r, c + 1);
              enumerateFreePolyominoes(newFreePolyominoes, freePolyomino, r + 1, c);
              enumerateFreePolyominoes(newFreePolyominoes, freePolyomino, r, c - 1);
            }
          }
        }
      }
      freePolyominoes.addAll(newFreePolyominoes);
    }
    return freePolyominoes;
  }

  private void enumerateFreePolyominoes(List<boolean[][]> freePolyominoes,
      boolean[][] freePolyomino, int row, int column) {
    if (!freePolyomino[row][column]) {
      freePolyomino[row][column] = true;
      boolean[][] newPolyomino = getExpandedMatrix(freePolyomino);
      freePolyomino[row][column] = false;
      boolean isExist = isExist(freePolyominoes, newPolyomino);
      if (!isExist) {
        freePolyominoes.add(newPolyomino);
      }
    }
  }

  private boolean isExist(List<boolean[][]> polyominoes, boolean[][] polyomino) {
    return polyominoes.stream()
        .anyMatch(newPolyomino -> isCongruent(polyomino, newPolyomino));
  }

}
