package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class NQueens implements HappyCodingV2<Integer, List<List<String>>> {

  public static final char EMPTY_CELL = '.';
  public static final char QUEEN = 'Q';

  @Override
  public List<List<String>> execute(Integer n) {
    return execute(IntStream.range(0, n)
        .mapToObj(i -> new char[n])
        .peek(row -> Arrays.fill(row, EMPTY_CELL))
        .toList(), 0);
  }

  public List<List<String>> executeBin(int n) {
    List<List<String>> boards = new ArrayList<>();
    execute(new int[n], 0, boards);
    return boards;
  }

  private void execute(int[] board, int r, List<List<String>> boards) {
    if (r == board.length) {
      List<String> qb = new ArrayList<>();
      StringBuilder row = new StringBuilder(" ".repeat(board.length));
      for (int k : board) {
        for (int j = 0; j < board.length; j++) {
          row.setCharAt(j, (k & (1 << j)) != 0 ? QUEEN : EMPTY_CELL);
        }
        qb.add(row.toString());
      }
      boards.add(qb);
      return;
    }
    for (int c = board.length - 1; c >= 0; c--) {
      if (isValidCell(board, r, c)) {
        board[r] |= (1 << c);
        execute(board, r + 1, boards);
        board[r] = 0;
      }
    }
  }

  public long executeBinCount(int n) {
    return execute(new int[n], 0);
  }

  private long execute(int[] board, int r) {
    if (r == board.length) {
      return 1;
    }
    long total = 0;
    for (int c = board.length - 1; c >= 0; c--) {
      if (isValidCell(board, r, c)) {
        board[r] |= (1 << c);
        total += execute(board, r + 1);
        board[r] = 0;
      }
    }
    return total;
  }

  private boolean isValidCell(int[] board, int r, int c) {
    int dc = c;
    int ac = c;
    for (; r >= 0; r--) {
      if ((board[r] & (1 << c)) != 0
          || dc < board.length && (board[r] & (1 << dc++)) != 0
          || ac >= 0 && (board[r] & (1 << ac--)) != 0) {
        return false;
      }
    }
    return true;
  }

  private List<List<String>> execute(List<char[]> chessBoard, int r) {
    var chessBoards = new ArrayList<List<String>>();
    if (r == chessBoard.size()) {
      chessBoards.add(chessBoard.stream()
          .map(String::valueOf)
          .toList());
      return chessBoards;
    }
    var row = chessBoard.get(r);
    for (int c = 0; c < row.length; c++) {
      if (isValidCell(chessBoard, r, c)) {
        row[c] = QUEEN;
        chessBoards.addAll(execute(chessBoard, r + 1));
        row[c] = EMPTY_CELL;
      }
    }
    return chessBoards;
  }

  private boolean isValidCell(List<char[]> chessBoard, int r, int c) {
    int diagonalC = c;
    int antiDiagonalC = c;
    for (; r >= 0; r--) {
      if (chessBoard.get(r)[c] == QUEEN
          || diagonalC >= 0 && chessBoard.get(r)[diagonalC--] == QUEEN
          || antiDiagonalC < chessBoard.size() && chessBoard.get(r)[antiDiagonalC++] == QUEEN) {
        return false;
      }
    }
    return true;
  }
}
