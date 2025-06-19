package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SolveStarBattleQueensPuzzle implements HappyCodingV2<int[][], List<Point>> {


  @Override
  public List<Point> execute(int[][] board) {
    LinkedList<Point> coordinates = new LinkedList<>();
    execute(board, 0, new HashSet<>(), coordinates);
    return coordinates;
  }

  private boolean execute(int[][] board, int i, Set<Integer> colors,
      LinkedList<Point> coordinates) {
    if (i == board.length) {
      return coordinates.size() == board.length;
    }
    for (int j = 0; j < board[0].length; j++) {
      int finalJ = j;
      if (coordinates.stream()
          .map(Point::y)
          .noneMatch(c -> finalJ == c)
          && !colors.contains(board[i][j])
          && (i == 0 || j == 0 || !coordinates.contains(new Point(i - 1, j - 1)))
          && (i == 0 || j == board[0].length - 1 || !coordinates.contains(new Point(i - 1, j + 1)))
          && (i == board.length - 1 || j == board[0].length - 1
          || !coordinates.contains(new Point(i + 1, j + 1)))
          && (i == board.length - 1 || j == 0 || !coordinates.contains(new Point(i + 1, j - 1)))) {
        coordinates.add(new Point(i, j));
        colors.add(board[i][j]);
        boolean solved = execute(board, i + 1, colors, coordinates);
        if (solved) {
          return solved;
        }
        coordinates.pollLast();
        colors.remove(board[i][j]);
      }
    }
    return false;
  }
}
