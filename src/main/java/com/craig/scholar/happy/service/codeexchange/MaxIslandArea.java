package com.craig.scholar.happy.service.codeexchange;

import java.util.LinkedList;

public class MaxIslandArea implements HappyCodingV2<int[][], Integer> {

  @Override
  public Integer execute(int[][] matrix) {
    record Point(int i, int j) {

    }
    int p_i;
    int p_j;
    var q = new LinkedList<Point>();
    int max_area = 0;
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        if (matrix[i][j] == 1) {
          q.add(new Point(i, j));
          int area = 1;
          matrix[i][j] = 0;
          while (!q.isEmpty()) {
            var p = q.poll();
            p_i = p.i - 1;
            p_j = p.j;
            if (p_i >= 0 && matrix[p_i][p_j] == 1) {
              area++;
              matrix[p_i][p_j] = 0;
              q.add(new Point(p_i, p_j));
            }
            p_i = p.i;
            p_j = p.j + 1;
            if (p_j < matrix[p.i].length && matrix[p_i][p_j] == 1) {
              area++;
              matrix[p_i][p_j] = 0;
              q.add(new Point(p_i, p_j));
            }
            p_i = p.i + 1;
            p_j = p.j;
            if (p_i < matrix.length && matrix[p_i][p_j] == 1) {
              area++;
              matrix[p_i][p_j] = 0;
              q.add(new Point(p_i, p_j));
            }
            p_i = p.i;
            p_j = p.j - 1;
            if (p_j >= 0 && matrix[p_i][p_j] == 1) {
              area++;
              matrix[p_i][p_j] = 0;
              q.add(new Point(p_i, p_j));
            }
          }
          max_area = Math.max(max_area, area);
        }
      }
    }
    return max_area;
  }
}
