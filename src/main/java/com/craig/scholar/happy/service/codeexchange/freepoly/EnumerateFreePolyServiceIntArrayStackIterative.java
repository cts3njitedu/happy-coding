package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class EnumerateFreePolyServiceIntArrayStackIterative implements
    EnumerateFreePolyService<int[]> {

  static class Poly {

    private int r;

    private int c;

    private int ones;

    private Direction direction;

    private boolean isVisited;
  }

  enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT
  }

  @Override
  public Collection<int[]> enumerate(int n) {
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

  private void apply(int[] polyTemp, int s, int e, Poly poly) {
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

  private void reverse(int[] polyTemp, int s, int e, Poly poly) {
    if (Direction.RIGHT == poly.direction && poly.c == -1) {
      for (int r = s; r <= e; r++) {
        polyTemp[r] >>= 1;
      }
    } else {
      polyTemp[poly.r] &= ~(1 << poly.c);
    }
  }

}
