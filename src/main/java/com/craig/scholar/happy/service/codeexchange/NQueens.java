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
    List<char[]> chessBoard = IntStream.range(0, n)
        .mapToObj(i -> new char[n])
        .peek(row -> Arrays.fill(row, EMPTY_CELL))
        .toList();
    return execute(chessBoard, 0);
  }

  private List<List<String>> execute(List<char[]> chessBoard, int r) {
    var boards = new ArrayList<List<String>>();
    if (r == chessBoard.size()) {
      boards.add(chessBoard.stream()
          .map(String::valueOf)
          .toList());
      return boards;
    }
    var row = chessBoard.get(r);
    for (int c = 0; c < row.length; c++) {
      if (isValidCell(chessBoard, r, c)) {
        row[c] = QUEEN;
        boards.addAll(execute(chessBoard, r + 1));
        row[c] = EMPTY_CELL;
      }
    }
    return boards;
  }

  private boolean isValidCell(List<char[]> chessBoard, int r, int c) {
    int diagonalC = c;
    int antiDiagonalC = c;
    while (r >= 0) {
      if (chessBoard.get(r)[c] == QUEEN
          || diagonalC >= 0 && chessBoard.get(r)[diagonalC--] == QUEEN
          || antiDiagonalC < chessBoard.size() && chessBoard.get(r)[antiDiagonalC++] == QUEEN) {
        return false;
      }
      r--;
    }
    return true;
  }
}
