package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.getNewPolyomino;
import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.isExist;
import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.isValidCell;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnumerateFreePolyServiceBooleanMatrixByReference implements
    EnumerateFreePolyService<boolean[][]> {

  @Override
  public Collection<boolean[][]> enumerate(int n) {
    boolean[][] polyomino = new boolean[1][1];
    polyomino[0][0] = true;
    Map<Integer, List<boolean[][]>> mem = new HashMap<>();
    enumerateFreePolyominoes(n, 1, mem,
        polyomino);
    return mem.get(n);
  }

  private void enumerateFreePolyominoes(int target, int n,
      Map<Integer, List<boolean[][]>> mem, boolean[][] polyomino) {
    if (isExist(mem.get(n), polyomino)) {
      return;
    }
    mem.computeIfAbsent(n, k -> new ArrayList<>());
    mem.get(n).add(polyomino);
    if (n != target) {
      for (int r = 0; r < polyomino.length; r++) {
        for (int c = 0; c < polyomino[0].length; c++) {
          List<int[]> expansions = List.of(
              new int[]{r - 1, c},
              new int[]{r, c + 1},
              new int[]{r + 1, c},
              new int[]{r, c - 1}
          );
          if (polyomino[r][c]) {
            expansions.stream()
                .filter(expansion -> isValidCell(polyomino, expansion[0], expansion[1]))
                .forEach(expansion -> enumerateFreePolyominoes(target, n + 1, mem,
                    getNewPolyomino(polyomino, expansion[0], expansion[1])));
          }
        }
      }
    }
  }
}
