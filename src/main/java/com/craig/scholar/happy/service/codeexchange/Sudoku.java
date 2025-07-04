package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
import java.util.List;

public class Sudoku implements HappyCodingV2<int[][], List<int[][]>> {

  @Override
  public List<int[][]> execute(int[][] board) {
    List<int[][]> solutions = new ArrayList<>();
    execute(board, 0, 0, solutions);
    if (solutions.size() != 1) {
      throw new IllegalArgumentException(
          String.format("Sudoku Puzzle should only have one solution. There are %s solutions",
              solutions.size()));
    }
    return solutions;
  }

  private void execute(int[][] board, int r, int c, List<int[][]> solutions) {
    if (r == board.length) {
      int[][] newBoard = new int[board.length][board.length];
      for (int i = 0; i < board.length; i++) {
        System.arraycopy(board[i], 0, newBoard[i], 0, board.length);
      }
      solutions.add(newBoard);
    } else if (board[r][c] != 0) {
      boolean isNextRow = c == board.length - 1;
      execute(board, isNextRow ? r + 1 : r, isNextRow ? 0 : c + 1, solutions);
    } else {
      for (int n = 1; n <= board.length; n++) {
        if (isColumnUnique(board, c, n)
            && isRowUnique(board, r, n)
            && isBlockUnique(board, r, c, n)) {
          board[r][c] = n;
          boolean isNextRow = c == board.length - 1;
          execute(board, isNextRow ? r + 1 : r, isNextRow ? 0 : c + 1, solutions);
          board[r][c] = 0;
        }
      }
    }
  }

  private boolean isColumnUnique(int[][] board, int c, int n) {
    for (int[] row : board) {
      if (row[c] == n) {
        return false;
      }
    }
    return true;
  }

  private boolean isRowUnique(int[][] board, int r, int n) {
    for (int c = 0; c < board.length; c++) {
      if (board[r][c] == n) {
        return false;
      }
    }
    return true;
  }

  private boolean isBlockUnique(int[][] board, int r, int c, int n) {
    int sqrt = (int) Math.floor(Math.sqrt(board.length));
    int cBlockSize = 0;
    int rBlockSize = 0;
    for (int s = sqrt; s >= 1; s--) {
      if (board.length % s == 0) {
        rBlockSize = s;
        cBlockSize = board.length / s;
        break;
      }
    }
    int rBlockStart = (r / rBlockSize) * rBlockSize;
    int rBlockEnd = rBlockStart + rBlockSize;
    int cBlockStart = (c / cBlockSize) * cBlockSize;
    int cBlockEnd = cBlockStart + cBlockSize;
    for (; rBlockStart < rBlockEnd; rBlockStart++) {
      for (int cb = cBlockStart; cb < cBlockEnd; cb++) {
        if (board[rBlockStart][cb] == n) {
          return false;
        }
      }
    }
    return true;
  }

}
