package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import com.craig.scholar.happy.util.MatrixUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnumerateFreePolyServiceStringRecursion implements EnumerateFreePolyService<String> {

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


  @Override
  public Collection<String> enumerate(int n) {
    return enumerateFreePolyominoesRecursion(n, false).getPolys();
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
}
