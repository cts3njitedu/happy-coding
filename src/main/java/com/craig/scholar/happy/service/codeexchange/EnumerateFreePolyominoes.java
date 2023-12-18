package com.craig.scholar.happy.service.codeexchange;

import static com.craig.scholar.happy.util.MatrixUtil.collapseMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;
import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import com.craig.scholar.happy.trie.MatrixTrie;
import com.craig.scholar.happy.util.MatrixUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
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

  public List<BitSet[]> enumerateFreePolyominoesV10(int n) {
    BitSet[] rootPoly = new BitSet[1];
    BitSet bitSet = new BitSet();
    bitSet.set(0);
    rootPoly[0] = bitSet;
    LinkedList<BitSet[]> freePolys = new LinkedList<>();
    freePolys.add(rootPoly);
    Set<String> mem = new HashSet<>();
    mem.add(Arrays.toString(rootPoly));
    for (int i = 2; i <= n; i++) {
      int size = freePolys.size();
      while (size > 0) {
        BitSet[] freePoly = freePolys.poll();
        for (int _r = 0; _r < freePoly.length; _r++) {
          final int r = _r;
          BitSet row = freePoly[r];
          row.stream()
              .forEach(c -> Stream.of(
                      extendNorth(freePoly, r, c),
                      extendEast(freePoly, r, c),
                      extendSouth(freePoly, r, c),
                      extendWest(freePoly, r, c))
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .forEach(newPoly -> {
                    if (isUniquePoly(newPoly, mem)) {
                      freePolys.add(newPoly);
                    }
                  }));
        }
        size--;
      }
    }
    return freePolys;
  }

  private Optional<BitSet[]> extendNorth(BitSet[] freePoly, int r, int c) {
    boolean northBit = r != 0 && freePoly[r - 1].get(c);
    if (!northBit) {
      BitSet[] newPoly = new BitSet[r == 0 ? freePoly.length + 1 : freePoly.length];
      int northRowIndex = r == 0 ? 0 : r - 1;
      newPoly[northRowIndex] = new BitSet();
      for (int i = 0; i < freePoly.length; i++) {
        BitSet bitSet = (BitSet) freePoly[i].clone();
        newPoly[r == 0 ? i + 1 : i] = bitSet;
      }
      newPoly[northRowIndex].set(c);
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private Optional<BitSet[]> extendEast(BitSet[] freePoly, int r, int c) {
    boolean eastBit = c != 0 && freePoly[r].get(c - 1);
    if (!eastBit) {
      BitSet[] newPoly = new BitSet[freePoly.length];
      for (int i = 0; i < newPoly.length; i++) {
        BitSet newRow = new BitSet();
        freePoly[i].stream()
            .map(j -> c == 0 ? j + 1 : j)
            .forEach(newRow::set);
        if (i == r) {
          newRow.set(c == 0 ? 0 : c - 1);
        }
        newPoly[i] = newRow;
      }
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private Optional<BitSet[]> extendSouth(BitSet[] freePoly, int r, int c) {
    boolean southBit = r != freePoly.length - 1 && freePoly[r + 1].get(c);
    if (!southBit) {
      BitSet[] newPoly = new BitSet[r == freePoly.length - 1 ? freePoly.length + 1
          : freePoly.length];
      int southRowIndex = r == freePoly.length - 1 ? freePoly.length : r + 1;
      newPoly[southRowIndex] = new BitSet();
      for (int i = 0; i < freePoly.length; i++) {
        BitSet bitSet = (BitSet) freePoly[i].clone();
        newPoly[i] = bitSet;
      }
      newPoly[southRowIndex].set(c);
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private Optional<BitSet[]> extendWest(BitSet[] freePoly, int r, int c) {
    boolean westBit = freePoly[r].get(c + 1);
    if (!westBit) {
      BitSet[] newPoly = new BitSet[freePoly.length];
      for (int i = 0; i < newPoly.length; i++) {
        BitSet newRow = (BitSet) freePoly[i].clone();
        if (i == r) {
          newRow.set(c + 1);
        }
        newPoly[i] = newRow;
      }
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  public boolean isUniquePoly(BitSet[] freePoly, Set<String> mem) {
    List<BitSet[]> transformations = getTransformations(freePoly);
    String polyString = Arrays.toString(freePoly);
    boolean isExist = transformations.stream()
        .map(Arrays::toString)
        .anyMatch(mem::contains);
    if (!isExist) {
      mem.add(polyString);
    }
    return !isExist;
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

  public enum ReturnType {
    LIST,
    INT,
    VOID
  }

  private static class PolyWrapper {

    private final int targetOnesCount;

    private final boolean print;

    private final Map<Integer, Set<String>> cache = new HashMap<>();

    public PolyWrapper(int n, boolean print) {
      this.targetOnesCount = n;
      this.cache.put(0, Set.of("[]"));
      for (int i = 1; i <= n; i++) {
        this.cache.put(i, new HashSet<>());
      }
      this.print = print;
    }

    public Collection<String> getPolys() {
      return cache.get(targetOnesCount);
    }

    public int getCount() {
      return cache.get(targetOnesCount).size();
    }

    private void handlePoly(int[] poly) {
      if (this.print) {
        MatrixUtil.printMatrix(poly, "[]");
      }
    }

    private boolean isExist(int[] poly, int onesCount) {
      return getTransformations(poly)
          .stream()
          .anyMatch(s -> cache.get(onesCount).contains(s));
    }

    private void cachePoly(int[] poly, int onesCount) {
      cache.get(onesCount).add(Arrays.toString(poly));
    }
  }

  public Collection<String> enumerateFreePolyominoesV7(int n) {
    return enumerateFreePolyominoesRecursion(n, false).getPolys();
  }

  public int enumerateFreePolyominoesV8(int n) {
    return enumerateFreePolyominoesRecursion(n, false).getCount();
  }

  public void enumerateFreePolyominoesV9(int n) {
    enumerateFreePolyominoesRecursion(n, true);
  }

  private PolyWrapper enumerateFreePolyominoesRecursion(int n, boolean print) {
    PolyWrapper polyWrapper = new PolyWrapper(n, print);
    if (n < 1) {
      return polyWrapper;
    }
    int[] poly = new int[2 * n - 1];
    poly[n - 1] = (1 << (n - 1));
    enumerateFreePolyominoesRecursion(poly, n - 1, n - 1, n - 1, 1, n,
        polyWrapper);
    return polyWrapper;
  }

  private void enumerateFreePolyominoesRecursion(int[] poly, int startRow, int endRow,
      int minimumNumberOfTrailingZeros, int currentOnesCount, int targetOnesCount,
      PolyWrapper polyWrapper) {
    int[] subPoly = new int[(endRow - startRow) + 1];
    for (int i = 0; i < subPoly.length; i++) {
      subPoly[i] |= (poly[startRow + i] >> minimumNumberOfTrailingZeros);
    }
    if (polyWrapper.isExist(subPoly, currentOnesCount)) {
      return;
    }
    polyWrapper.cachePoly(subPoly, currentOnesCount);
    if (currentOnesCount == targetOnesCount) {
      polyWrapper.handlePoly(subPoly);
      return;
    }
    for (int r = startRow; r <= endRow; r++) {
      int columns = Integer.SIZE - Integer.numberOfLeadingZeros(poly[r]);
      for (int c = 0; c < columns; c++) {
        int b = (poly[r] & (1 << c)) == 0 ? 0 : 1;
        if (b == 1) {
          int nb = (r == 0 ? 1 : (poly[r - 1] & (1 << c)) == 0 ? 0 : 1);
          if (nb == 0) {
            int t = poly[r - 1];
            poly[r - 1] |= (1 << c);
            enumerateFreePolyominoesRecursion(poly, t == 0 ? startRow - 1 : startRow, endRow,
                minimumNumberOfTrailingZeros, currentOnesCount + 1, targetOnesCount,
                polyWrapper);
            poly[r - 1] = t;
          }
          nb = (c == 0 ? 1 : (poly[r] & (1 << (c - 1))) == 0 ? 0 : 1);
          if (nb == 0) {
            int t = poly[r];
            poly[r] |= (1 << (c - 1));
            int tbs = Integer.numberOfTrailingZeros(poly[r]);
            enumerateFreePolyominoesRecursion(poly, startRow, endRow,
                Math.min(minimumNumberOfTrailingZeros, tbs), currentOnesCount + 1, targetOnesCount,
                polyWrapper);
            poly[r] = t;
          }
          nb = (r == poly.length - 1 ? 1 : (poly[r + 1] & (1 << c)) == 0 ? 0 : 1);
          if (nb == 0) {
            int t = poly[r + 1];
            poly[r + 1] |= (1 << c);
            enumerateFreePolyominoesRecursion(poly, startRow, t == 0 ? endRow + 1 : endRow,
                minimumNumberOfTrailingZeros, currentOnesCount + 1, targetOnesCount,
                polyWrapper);
            poly[r + 1] = t;
          }
          nb = (c == (columns - 1) ? 0 : (poly[r] & (1 << (c + 1))) == 0 ? 0 : 1);
          if (nb == 0) {
            int t = poly[r];
            poly[r] |= (1 << (c + 1));
            enumerateFreePolyominoesRecursion(poly, startRow, endRow, minimumNumberOfTrailingZeros,
                currentOnesCount + 1, targetOnesCount, polyWrapper);
            poly[r] = t;
          }
        }
      }
    }
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
