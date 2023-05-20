package com.craig.happy.coding;

import static com.craig.happy.coding.util.MatrixUtil.isCongruent;
import static com.craig.happy.coding.util.MatrixUtil.print;

import java.util.LinkedList;
import java.util.List;

public class EnumerateFreePolyominoes {

  public int getNumberOfFreePolyominoes(int n) {
    return enumerateFreePolyominoes(n).size();
  }

  public List<boolean[][]> enumerateFreePolyominoes(int n) {
    return enumerateFreePolyominoes(n, false);
  }

  public List<boolean[][]> enumerateFreePolyominoes(int n, boolean isPrint) {
    LinkedList<boolean[][]> freePolyominoes = new LinkedList<>();
    if (n < 1) {
      freePolyominoes.add(new boolean[][]{});
      return freePolyominoes;
    }
    boolean[][] polyomino = new boolean[1][1];
    polyomino[0][0] = true;
    freePolyominoes.add(polyomino);
    for (int i = 2; i <= n; i++) {
      if (isPrint) {
        print(i - 1, freePolyominoes, "[]");
      }
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
    if (isPrint) {
      print(n, freePolyominoes, "[]");
    }

    return freePolyominoes;
  }

  private void enumerateFreePolyominoes(List<boolean[][]> polyominoes,
      boolean[][] polyomino, int row, int column) {
    if (isValidCell(polyomino, row, column)) {
      boolean[][] newPolyomino = getNewPolyomino(polyomino, row, column);
      boolean isExist = polyominoes.stream()
          .anyMatch(existingPolyomino -> isCongruent(existingPolyomino, newPolyomino));
      if (!isExist) {
        polyominoes.add(newPolyomino);
      }
    }
  }

  private boolean isValidCell(boolean[][] polyomino, int row, int column) {
    return row == -1 || column == -1 || row == polyomino.length || column == polyomino[0].length
        || !polyomino[row][column];
  }

  private boolean[][] getNewPolyomino(boolean[][] polyomino, int row, int column) {
    boolean[][] newPolyomino = new boolean[(row == -1 || row == polyomino.length) ?
        polyomino.length + 1 : polyomino.length][(column == -1 || column == polyomino[0].length) ?
        polyomino[0].length + 1 : polyomino[0].length];
    int firstRow = row == -1 ? 1 : 0;
    int firstColumn = column == -1 ? 1 : 0;
    for (int i = 0; i < polyomino.length; i++) {
      for (int j = 0; j < polyomino[0].length; j++) {
        newPolyomino[firstRow == 1 ? i + 1 : i][firstColumn == 1 ? j + 1 : j] = polyomino[i][j];
      }
    }
    newPolyomino[row == -1 ? 0 : row][column == -1 ? 0 : column] = true;
    return newPolyomino;
  }

}
