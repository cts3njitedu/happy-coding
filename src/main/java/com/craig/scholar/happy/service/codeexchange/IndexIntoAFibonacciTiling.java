package com.craig.scholar.happy.service.codeexchange;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

import com.craig.scholar.happy.model.BigPoint;
import com.craig.scholar.happy.model.Point;
import java.math.BigInteger;

public class IndexIntoAFibonacciTiling implements HappyCodingV2<Point, Integer> {

  record FibonacciTile(int n, Point min, Point max) {

    public FibonacciTile(int v, Point min) {
      this(v, min, new Point(min.x() + (v - 1), min.y() + (v - 1)));
    }

    public boolean isIndex(Point point) {
      return point.x() >= min.x() && point.x() <= max.x()
          && point.y() >= min.y() && point.y() <= max.y();
    }
  }

  record FibonacciBigTile(BigInteger n, BigPoint min, BigPoint max) {

    public FibonacciBigTile(BigInteger n, BigPoint min) {
      this(n, min, new BigPoint(min.x().add(n.subtract(ONE)),
          min.y().add(n.subtract(ONE))));
    }

    public boolean isIndex(BigPoint point) {
      return point.x().compareTo(min.x()) >= 0
          && point.x().compareTo(max.x()) <= 0
          && point.y().compareTo(min.y()) >= 0
          && point.y().compareTo(max.y()) <= 0;
    }

    public boolean isOrigin() {
      return min.isOrigin() && max().isOrigin();
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
    FibonacciTile t1 = new FibonacciTile(1, new Point(0, 0));
    FibonacciTile t2 = t1;
    Direction direction = Direction.RIGHT;
    while (!t2.isIndex(point)) {
      int v = t1 == t2 ? 1 : t1.n + t2.n;
      FibonacciTile h = t2;
      t2 = switch (direction) {
        case RIGHT -> new FibonacciTile(v, new Point(t2.max.x() + 1, t2.min.y()));
        case UP -> new FibonacciTile(v, new Point(t1.min.x(), t2.max.y() + 1));
        case LEFT -> new FibonacciTile(v, new Point(t2.min.x() - v, t1.min.y()));
        case DOWN -> new FibonacciTile(v, new Point(t2.min.x(), t2.min.y() - v));
      };
      direction = direction.getAntiClockwiseDirection();
      t1 = h;
    }
    return t2.n;
  }

  public BigInteger execute(BigPoint point) {
    var smallTile = new FibonacciBigTile(ONE, new BigPoint(ZERO, ZERO));
    var bigTile = smallTile;
    Direction direction = Direction.RIGHT;
    while (!bigTile.isIndex(point)) {
      var n = bigTile.isOrigin() && smallTile.isOrigin() ? ONE : smallTile.n.add(bigTile.n);
      var hold = bigTile;
      bigTile = switch (direction) {
        case RIGHT ->
            new FibonacciBigTile(n, new BigPoint(bigTile.max.x().add(ONE), bigTile.min.y()));
        case UP ->
            new FibonacciBigTile(n, new BigPoint(smallTile.min.x(), bigTile.max.y().add(ONE)));
        case LEFT ->
            new FibonacciBigTile(n, new BigPoint(bigTile.min.x().subtract(n), smallTile.min.y()));
        case DOWN ->
            new FibonacciBigTile(n, new BigPoint(bigTile.min.x(), bigTile.min.y().subtract(n)));
      };
      direction = direction.getAntiClockwiseDirection();
      smallTile = hold;
    }
    return bigTile.n;
  }

}
