package com.craig.scholar.happy.trie;

import java.util.Objects;

public class MatrixTrie {

  private boolean isEndOfMatrix;
  private final MatrixTrie[] matrixTries = new MatrixTrie[3];

  public boolean find(boolean[][] matrix) {
    return find(matrix, 0, 0);
  }

  public boolean find(boolean[][] matrix, int r, int c) {
    if (r == matrix.length) {
      return isEndOfMatrix;
    } else {
      int childIndex = c == matrix[0].length ? 2 : matrix[r][c] ? 1 : 0;
      MatrixTrie child = matrixTries[childIndex];
      if (Objects.isNull(child)) {
        return false;
      }
      return child.find(matrix, childIndex == 2 ? r + 1 : r,
          childIndex == 2 ? 0 : c + 1);
    }
  }

  public void add(boolean[][] matrix) {
    add(matrix, 0, 0);
  }

  public void add(boolean[][] matrix, int r, int c) {
    if (r == matrix.length) {
      isEndOfMatrix = true;
    } else {
      int childIndex = c == matrix[0].length ? 2 : matrix[r][c] ? 1 : 0;
      MatrixTrie child = matrixTries[childIndex];
      if (Objects.isNull(child)) {
        child = new MatrixTrie();
        matrixTries[childIndex] = child;
      }
      child.add(matrix, childIndex == 2 ? r + 1 : r,
          childIndex == 2 ? 0 : c + 1);
    }
  }
}
