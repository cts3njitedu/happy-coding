package com.craig.scholar.happy.service.codeexchange;

import static com.craig.scholar.happy.util.MatrixUtil.collapseMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.getTransformations;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;

import com.craig.scholar.happy.trie.MatrixTrie;
import com.craig.scholar.happy.util.MatrixUtil;
import com.craig.scholar.happy.util.TransformationUtil;
import java.util.ArrayList;
import java.util.Arrays;
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
    Set<String> polyMemory = new HashSet<>();
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

  public List<int[]> enumerateFreePolyominoesV5(int n) {
    LinkedList<int[]> polys = new LinkedList<>();
    if (n < 1) {
      polys.add(new int[]{});
      return polys;
    }
    int[] poly = {1};
    polys.add(poly);
    Set<String> polyMemory = new HashSet<>();
    polyMemory.add(Arrays.toString(poly));
    for (int i = 2; i <= n; i++) {
      int size = polys.size();
      while (size > 0) {
        poly = polys.poll();
        for (int r = 0; r < poly.length; r++) {
          int columns = Integer.SIZE - Integer.numberOfLeadingZeros(poly[r]);
          for (int c = columns - 1; c >= 0; c--) {
            int b = (poly[r] & (1 << c)) != 0 ? 1 : 0;
            if (b == 1) {
              int[] newPoly;
              newPoly = newPolyUp(poly, r, c);
              if (newPoly.length > 0 && !isExist(polyMemory, newPoly)) {
                polys.add(newPoly);
              }
              newPoly = newPolyRight(poly, r, c);
              if (newPoly.length > 0 && !isExist(polyMemory, newPoly)) {
                polys.add(newPoly);
              }
              newPoly = newPolyDown(poly, r, c);
              if (newPoly.length > 0 && !isExist(polyMemory, newPoly)) {
                polys.add(newPoly);
              }
              newPoly = newPolyLeft(poly, r, columns, c);
              if (newPoly.length > 0 && !isExist(polyMemory, newPoly)) {
                polys.add(newPoly);
              }
            }
          }
        }
        size--;
      }
      polyMemory.clear();
    }
    return polys;
  }

  private static int[] newPolyLeft(int[] poly, int r, int columns, int c) {
    int leftBit = (c + 1 == columns) ? 0 : (poly[r] & (1 << (c + 1))) != 0 ? 1 : 0;
    if (leftBit == 0) {
      int[] newPolyLeft = new int[poly.length];
      for (int j = 0; j < poly.length; j++) {
        if (j == r) {
          newPolyLeft[j] = (poly[j] | (1 << (c + 1)));
        } else {
          newPolyLeft[j] = poly[j];
        }
      }
      return newPolyLeft;
    }
    return new int[0];
  }

  private static int[] newPolyDown(int[] poly, int r, int c) {
    int downBit = (r + 1 == poly.length) ? 0 : (poly[r + 1] & (1 << c)) != 0 ? 1 : 0;
    if (downBit == 0) {
      int[] newPolyDown = new int[r == poly.length - 1 ? poly.length + 1 : poly.length];
      newPolyDown[r == poly.length - 1 ? poly.length : r + 1] = (1 << c);
      for (int j = 0; j < poly.length; j++) {
        newPolyDown[j] |= poly[j];
      }
      return newPolyDown;
    }
    return new int[0];
  }

  private static int[] newPolyRight(int[] poly, int r, int c) {
    int rightBit = (c == 0) ? 0 : (poly[r] & (1 << (c - 1))) != 0 ? 1 : 0;
    if (rightBit == 0) {
      int[] newPolyRight = new int[poly.length];
      for (int j = 0; j < poly.length; j++) {
        if (c == 0) {
          newPolyRight[j] = (poly[j] * 2) | ((j == r) ? 1 : 0);
        } else {
          if (j == r) {
            newPolyRight[j] = poly[j] | (1 << (c - 1));
          } else {
            newPolyRight[j] = poly[j];
          }
        }
      }
      return newPolyRight;
    }
    return new int[0];
  }

  private static int[] newPolyUp(int[] poly, int r, int c) {
    int upBit = r == 0 ? 0 : (poly[r - 1] & (1 << c)) != 0 ? 1 : 0;
    if (upBit == 0) {
      int[] newPolyUp = new int[r == 0 ? poly.length + 1 : poly.length];
      newPolyUp[r == 0 ? 0 : r - 1] |= (1 << c);
      for (int j = 0; j < poly.length; j++) {
        newPolyUp[r == 0 ? j + 1 : j] |= poly[j];
      }
      return newPolyUp;
    }
    return new int[0];
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

  private static boolean isExist(Set<String> polyMemory, boolean[][] newPoly) {
    if (polyMemory.contains(collapseMatrix(newPoly))) {
      return true;
    }
    List<String> transformations = getTransformations(newPoly).stream()
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

  private static boolean isExist(Set<String> polyMem, int[] newPoly) {
    String polyStr = Arrays.toString(newPoly);
    if (polyMem.contains(polyStr)) {
      return true;
    }
    List<String> transformations = TransformationUtil.getTransformations(newPoly);
    boolean isExist = transformations.stream()
        .anyMatch(polyMem::contains);
    if (!isExist) {
      polyMem.addAll(transformations);
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
