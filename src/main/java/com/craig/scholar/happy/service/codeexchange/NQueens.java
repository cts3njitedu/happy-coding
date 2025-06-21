package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class NQueens implements HappyCodingV2<Integer, List<List<String>>> {

  @Override
  public List<List<String>> execute(Integer n) {
    List<char[]> board = IntStream.range(0, n)
        .mapToObj(i -> new char[n])
        .peek(row -> Arrays.fill(row, '.'))
        .toList();
    return execute(board, 0);
  }

  private List<List<String>> execute(List<char[]> board, int r) {
    var boards = new ArrayList<List<String>>();
    if (r == board.size()) {
      boards.add(board.stream()
          .map(String::valueOf)
          .toList());
      return boards;
    }
    var row = board.get(r);
    for (int c = 0; c < row.length; c++) {
      if (isValid(board, r, c)) {
        var s = row[c];
        row[c] = 'Q';
        boards.addAll(execute(board, r + 1));
        row[c] = s;
      }
    }
    return boards;
  }

  private boolean isValid(List<char[]> board, int row, int column) {
    int diagonalColumn = column;
    int antiDiagonalColumn = column;
    while (row >= 0) {
      if (board.get(row)[column] == 'Q'
          || diagonalColumn >= 0 && board.get(row)[diagonalColumn--] == 'Q'
          || antiDiagonalColumn < board.size() && board.get(row)[antiDiagonalColumn++] == 'Q') {
        return false;
      }
      row--;
    }
    return true;
  }
}
