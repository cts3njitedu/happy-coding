package com.craig.scholar.happy.service.codeexchange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TheAreaOfRectangles {

  record Point(int x, int y) {

  }


  public long area(List<List<Point>> rectangles) {
    return rectangles.stream()
        .reduce(new HashMap<Integer, Set<Integer>>(), (m, rectangle) -> {
          Point lc = rectangle.get(0);
          Point rc = rectangle.get(1);
          int lw = Math.min(lc.y, rc.y);
          int uw = Math.max(lc.y, rc.y);
          int lh = Math.min(lc.x, rc.x);
          int uh = Math.max(lc.x, rc.x);
          for (int h = lh; h <= uh; h++) {
            for (int w = lw; w <= uw; w++) {
              Set<Integer> set = m.computeIfAbsent(h, k -> new HashSet<>());
              set.add(w);
            }
          }
          return m;
        }, (m1, m2) -> m2)
        .values()
        .stream()
        .map(Set::size)
        .count();
  }
}
