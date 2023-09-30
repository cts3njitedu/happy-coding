package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.Point;
import com.craig.scholar.happy.model.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TheAreaOfRectangles {


  public long area(List<Rectangle> rectangles) {
    return rectangles.stream()
        .reduce(new HashMap<Integer, Set<Integer>>(), (m, rect) -> {
          Point p1 = rect.p1();
          Point p2 = rect.p2();
          int lw = Math.min(p1.x(), p2.x());
          int uw = Math.max(p1.x(), p2.x());
          int lh = Math.min(p1.y(), p2.y());
          int uh = Math.max(p1.y(), p2.y());
          for (int h = lh; h < uh; h++) {
            for (int w = lw; w < uw; w++) {
              Set<Integer> set = m.computeIfAbsent(h, k -> new HashSet<>());
              set.add(w);
            }
          }
          return m;
        }, (m1, m2) -> m2)
        .values()
        .stream()
        .map(Set::size)
        .mapToLong(Integer::longValue)
        .sum();
  }
}
