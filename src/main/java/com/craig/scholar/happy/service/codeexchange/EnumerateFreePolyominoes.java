package com.craig.scholar.happy.service.codeexchange;

import static com.craig.scholar.happy.util.MatrixUtil.collapseMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.getTransformations;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;

import com.craig.scholar.happy.trie.MatrixTrie;
import com.craig.scholar.happy.util.MatrixUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class EnumerateFreePolyominoes {

  public static final String TRUE_FLAG = "[]";

  public int getNumberOfFreePolyominoes(int n) {
    return enumerateFreePolyominoes(n).size();
  }

  public List<boolean[][]> enumerateFreePolyominoes(int n) {
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

  public List<boolean[][]> enumerateFreePolyominoesV3(int n) {
    LinkedList<boolean[][]> freePolyominoes = new LinkedList<>();
    if (n < 1) {
      freePolyominoes.add(new boolean[][]{{}});
      return freePolyominoes;
    }
    boolean[][] rootMatrix = new boolean[][]{{true}};
    freePolyominoes.add(rootMatrix);
    Set<BigInteger> polyMemory = new HashSet<>();
    polyMemory.add(collapseMatrix(rootMatrix));
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
                  .filter(newPolyomino -> !isExist(polyMemory, newPolyomino))
                  .forEach(freePolyominoes::add);
            }
          }
        }
        size--;
      }
    }
    return freePolyominoes;
  }

  public List<boolean[][]> enumerateFreePolyominoesV4(int n) {
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

  public long enumerateFreePolyominoesV2(int n) {
    boolean[][] polyomino = new boolean[1][1];
    polyomino[0][0] = true;
    Map<Integer, List<boolean[][]>> mem = new HashMap<>();
    enumerateFreePolyominoes(n, 1, mem,
        polyomino);
    return mem.get(n).size();
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

  private static boolean isExist(List<boolean[][]> polyominoes, boolean[][] newPolyomino) {
    return Optional.ofNullable(polyominoes)
        .orElse(List.of())
        .parallelStream()
        .anyMatch(existingPolyomino -> isCongruent(existingPolyomino, newPolyomino));
  }

  private static boolean isExist(Set<BigInteger> polyMemory, boolean[][] newPoly) {
    List<BigInteger> transformations = getTransformations(newPoly).stream()
        .map(MatrixUtil::collapseMatrix)
        .toList();
    boolean isExist = transformations.stream()
        .anyMatch(polyMemory::contains);
    if (!isExist) {
      polyMemory.addAll(transformations);
    }
    return isExist;
  }

  private static boolean isExist(MatrixTrie matrixTrie, boolean[][] newPoly) {
    if (matrixTrie.find(newPoly)) {
      return true;
    }
    List<boolean[][]> transformations = getTransformations(newPoly);
    boolean isExist = transformations.stream()
        .anyMatch(matrixTrie::find);
    if (!isExist) {
      transformations
          .forEach(matrixTrie::add);
    }
    return isExist;
  }

  private boolean isValidCell(boolean[][] polyomino, int row, int column) {
    return row == -1 || column == -1 || row == polyomino.length || column == polyomino[0].length
        || !polyomino[row][column];
  }

  private boolean[][] getNewPolyomino(boolean[][] polyomino, int row, int column) {
    boolean[][] newPolyomino = new boolean[(row == -1 || row == polyomino.length) ?
        polyomino.length + 1 : polyomino.length][(column == -1 || column == polyomino[0].length) ?
        polyomino[0].length + 1 : polyomino[0].length];
    int firstRow = row == -1 ? 1 : 0;
    int firstColumn = column == -1 ? 1 : 0;
    for (int i = 0; i < polyomino.length; i++) {
      for (int j = 0; j < polyomino[0].length; j++) {
        newPolyomino[firstRow == 1 ? i + 1 : i][firstColumn == 1 ? j + 1 : j] = polyomino[i][j];
      }
    }
    newPolyomino[row == -1 ? 0 : row][column == -1 ? 0 : column] = true;
    return newPolyomino;
  }
}
