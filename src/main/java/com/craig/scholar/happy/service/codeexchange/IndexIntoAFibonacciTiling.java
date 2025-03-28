package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.Point;

public class IndexIntoAFibonacciTiling implements HappyCodingV2<Point, Integer> {

  record FibonacciTile(int v, Point min, Point max) {

    public FibonacciTile(int v, Point min) {
      this(v, min, new Point(min.x() + (v - 1), min.y() + (v - 1)));
    }

    public boolean isIndex(Point point) {
      return point.x() >= min.x() && point.x() <= max.x()
          && point.y() >= min.y() && point.y() <= max.y();
    }
  }

  private enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public Direction getAntiClockwiseDirection() {
      return switch (Direction.valueOf(this.name())) {
        case UP -> LEFT;
        case LEFT -> DOWN;
        case DOWN -> RIGHT;
        case RIGHT -> UP;
      };
    }
  }

  @Override
  public Integer execute(Point point) {
    FibonacciTile t1 = new FibonacciTile(1, new Point(0, 0), new Point(0, 0));
    FibonacciTile t2 = t1;
    int d = 0;
    while (!t2.isIndex(point)) {
      int v = t1 == t2 ? 1 : t1.v + t2.v;
      FibonacciTile h = t2;
      if (d == 0) {
        t2 = new FibonacciTile(v, new Point(t2.max.x() + 1, t2.min.y()));
      } else if (d == 1) {
        t2 = new FibonacciTile(v, new Point(t1.min.x(), t2.max.y() + 1));
      } else if (d == 2) {
        t2 = new FibonacciTile(v, new Point(t2.min.x() - v, t1.min.y()));
      } else {
        t2 = new FibonacciTile(v, new Point(t2.min.x(), t2.min.y() - v));
      }
      d = (d + 1) % 4;
      t1 = h;
    }
    return t2.v;
  }

}
