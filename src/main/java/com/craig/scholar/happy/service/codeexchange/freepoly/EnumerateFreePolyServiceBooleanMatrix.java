package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.getNewPolyomino;
import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.isExist;
import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.isValidCell;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public class EnumerateFreePolyServiceBooleanMatrix implements
    EnumerateFreePolyService<boolean[][]> {

  @Override
  public Collection<boolean[][]> enumerate(int n) {
    LinkedList<boolean[][]> freePolyominoes = new LinkedList<>();
    if (n < 1) {
      freePolyominoes.add(new boolean[][]{{}});
      return freePolyominoes;
    }
    freePolyominoes.add(new boolean[][]{{true}});
    for (int i = 2; i <= n; i++) {
      LinkedList<boolean[][]> newFreePolyominoes = new LinkedList<>();
      while (!freePolyominoes.isEmpty()) {
        boolean[][] freePolyomino = freePolyominoes.poll();
        for (int r = 0; r < freePolyomino.length; r++) {
          for (int c = 0; c < freePolyomino[0].length; c++) {
            if (freePolyomino[r][c]) {
              Stream.of(new int[]{r - 1, c},
                      new int[]{r, c + 1},
                      new int[]{r + 1, c},
                      new int[]{r, c - 1})
                  .filter(expansion -> isValidCell(freePolyomino, expansion[0], expansion[1]))
                  .map(expansion -> getNewPolyomino(freePolyomino, expansion[0], expansion[1]))
                  .filter(newPolyomino -> !isExist(newFreePolyominoes, newPolyomino))
                  .forEach(newFreePolyominoes::add);
            }
          }
        }
      }
      freePolyominoes.addAll(newFreePolyominoes);
    }
    return freePolyominoes;
  }
}
