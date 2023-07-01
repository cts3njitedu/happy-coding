package com.craig.scholar.happy.service.codeexchange;

import static com.craig.scholar.happy.util.MatrixUtil.collapseMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;
import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import com.craig.scholar.happy.trie.MatrixTrie;
import com.craig.scholar.happy.util.MatrixUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class EnumerateFreePolyominoes {

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

  public Collection<int[]> enumerateFreePolyominoesV5(int n) {
    if (n < 0 || n > 15) {
      throw new IllegalArgumentException(String.format("Number %d is greater than 15."
          + " Please pass in positive number less than or equal to 15.", n));
    }
    Queue<int[]> polys = new LinkedList<>();
    if (n < 1) {
      polys.add(new int[]{});
      return polys;
    }
    int[] poly = {1};
    polys.add(poly);
    Set<String> polyMemory = new HashSet<>();
    polyMemory.add(Arrays.toString(poly));
    int[] newPoly;
    for (int i = 2; i <= n; i++) {
      int size = polys.size();
      while (size > 0) {
        poly = polys.poll();
        for (int r = 0; r < poly.length; r++) {
          int columns = Integer.SIZE - Integer.numberOfLeadingZeros(poly[r]);
          for (int c = columns - 1; c >= 0; c--) {
            int b = (poly[r] & (1 << c)) != 0 ? 1 : 0;
            if (b == 1) {
              newPoly = newPolyUp(poly, r, c);
              populatePolys(polyMemory, polys, newPoly);
              newPoly = newPolyRight(poly, r, c);
              populatePolys(polyMemory, polys, newPoly);
              newPoly = newPolyDown(poly, r, c);
              populatePolys(polyMemory, polys, newPoly);
              newPoly = newPolyLeft(poly, r, columns, c);
              populatePolys(polyMemory, polys, newPoly);
            }
          }
        }
        size--;
      }
      polyMemory.clear();
    }
    return polys;
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
    return null;
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
    return null;
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
    return null;
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
    return null;
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

  private static class Poly {

    private int r;

    private int c;

    private int ones;

    private Direction direction;

    private boolean isVisited;


  }

  private enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
  }

  public List<int[]> enumerateFreePolyominoesV6(int n) {
    if (n < 1) {
      return List.of(new int[]{});
    }
    int[] polyTemp = new int[n * 2 + 1];
    int s = n;
    int e = n;
    LinkedList<int[]> polyList = new LinkedList<>();
    Stack<Poly> stack = new Stack<>();
    Poly poly = new Poly();
    poly.ones = 1;
    poly.r = n;
    stack.push(poly);
    int[] subPoly;
    boolean isExist;
    Set<String> mem = new HashSet<>();
    while (!stack.isEmpty()) {
      poly = stack.peek();
      if (!poly.isVisited) {
        s = Math.min(poly.r, s);
        e = Math.max(poly.r, e);
        apply(polyTemp, s, e, poly);
        subPoly = Arrays.copyOfRange(polyTemp, s, e + 1);
        isExist = getTransformations(subPoly)
            .stream()
            .anyMatch(mem::contains);
        if (isExist || poly.ones == n) {
          if (poly.ones == n && !isExist) {
            mem.add(Arrays.toString(subPoly));
            polyList.add(subPoly);
          }
          stack.pop();
          reverse(polyTemp, s, e, poly);
          if (polyTemp[s] == 0) {
            s++;
          }
          if (polyTemp[e] == 0) {
            e--;
          }
        } else {
          mem.add(Arrays.toString(subPoly));
          for (int r = s; r <= e; r++) {
            int columns = Integer.SIZE - Integer.numberOfLeadingZeros(polyTemp[r]);
            for (int c = columns - 1; c >= 0; c--) {
              int b = (polyTemp[r] & (1 << c)) != 0 ? 1 : 0;
              if (b == 1) {
                int nb = (r == 0 ? 1 : (polyTemp[r - 1] & (1 << c)) != 0 ? 1 : 0);
                if (nb == 0) {
                  Poly newPoly = new Poly();
                  newPoly.direction = Direction.UP;
                  newPoly.ones = poly.ones + 1;
                  newPoly.r = r - 1;
                  newPoly.c = c;
                  stack.push(newPoly);
                }
                nb = (c == 0 ? 0 : (polyTemp[r] & (1 << (c - 1))) != 0 ? 1 : 0);
                if (nb == 0) {
                  Poly newPoly = new Poly();
                  newPoly.direction = Direction.RIGHT;
                  newPoly.ones = poly.ones + 1;
                  newPoly.r = r;
                  newPoly.c = c - 1;
                  stack.push(newPoly);
                }
                nb = (r == (polyTemp.length - 1) ? 1 : (polyTemp[r + 1] & (1 << c)) != 0 ? 1 : 0);
                if (nb == 0) {
                  Poly newPoly = new Poly();
                  newPoly.direction = Direction.DOWN;
                  newPoly.ones = poly.ones + 1;
                  newPoly.r = r + 1;
                  newPoly.c = c;
                  stack.push(newPoly);
                }
                nb = (c == columns - 1 ? 0 : (polyTemp[r] & (1 << (c + 1))) != 0 ? 1 : 0);
                if (nb == 0) {
                  Poly newPoly = new Poly();
                  newPoly.direction = Direction.LEFT;
                  newPoly.ones = poly.ones + 1;
                  newPoly.r = r;
                  newPoly.c = c + 1;
                  stack.push(newPoly);
                }
              }
            }
          }
          poly.isVisited = true;
        }
      } else {
        stack.pop();
        reverse(polyTemp, s, e, poly);
        if (polyTemp[s] == 0) {
          s++;
        }
        if (polyTemp[e] == 0) {
          e--;
        }
      }

    }
    return polyList;
  }

  private static void apply(int[] polyTemp, int s, int e, Poly poly) {
    if (Direction.RIGHT == poly.direction && poly.c == -1) {
      for (int r = s; r <= e; r++) {
        polyTemp[r] *= 2;
        if (r == poly.r) {
          polyTemp[r] += 1;
        }
      }
    } else {
      polyTemp[poly.r] |= (1 << poly.c);
    }
  }

  private static void reverse(int[] polyTemp, int s, int e, Poly poly) {
    if (Direction.RIGHT == poly.direction && poly.c == -1) {
      for (int r = s; r <= e; r++) {
        polyTemp[r] >>= 1;
      }
    } else {
      polyTemp[poly.r] &= ~(1 << poly.c);
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

  private static void populatePolys(Set<String> polyMem, Queue<int[]> polys, int[] newPoly) {
    if (Objects.isNull(newPoly)) {
      return;
    }
    String polyStr = Arrays.toString(newPoly);
    if (polyMem.contains(polyStr)) {
      return;
    }
    boolean isExist = getTransformations(newPoly)
        .stream()
        .anyMatch(polyMem::contains);
    if (!isExist) {
      polyMem.add(polyStr);
      polys.add(newPoly);
    }
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
