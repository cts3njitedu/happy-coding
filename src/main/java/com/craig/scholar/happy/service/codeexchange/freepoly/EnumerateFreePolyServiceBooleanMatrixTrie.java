package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.getNewPolyomino;
import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.isExist;
import static com.craig.scholar.happy.service.codeexchange.freepoly.util.EnumerateFreePolyUtil.isValidCell;

import com.craig.scholar.happy.trie.MatrixTrie;
import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Stream;

public class EnumerateFreePolyServiceBooleanMatrixTrie implements
    EnumerateFreePolyService<boolean[][]> {

  @Override
  public Collection<boolean[][]> enumerate(int n) {
    LinkedList<boolean[][]> freePolyominoes = new LinkedList<>();
    if (n < 1) {
      freePolyominoes.add(new boolean[][]{{}});
      return freePolyominoes;
    }
    boolean[][] rootMatrix = new boolean[][]{{true}};
    freePolyominoes.add(rootMatrix);
    MatrixTrie matrixTrie = new MatrixTrie();
    matrixTrie.add(rootMatrix);
    for (int i = 2; i <= n; i++) {
      int size = freePolyominoes.size();
      while (size > 0) {
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
                  .filter(newPolyomino -> !isExist(matrixTrie, newPolyomino))
                  .forEach(freePolyominoes::add);
            }
          }
        }
        size--;
      }
    }
    return freePolyominoes;
  }
}
