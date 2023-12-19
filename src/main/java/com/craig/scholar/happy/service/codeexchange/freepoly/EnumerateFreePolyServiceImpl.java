package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class EnumerateFreePolyServiceImpl implements EnumerateFreePolyService<int[]> {

  @Override
  public Collection<int[]> enumerate(int n) {
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
}
