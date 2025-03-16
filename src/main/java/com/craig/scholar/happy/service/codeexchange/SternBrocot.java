package com.craig.scholar.happy.service.codeexchange;


import static com.craig.scholar.happy.service.codeexchange.SternBrocot.SternBrocotSearch.FRACTION;
import static com.craig.scholar.happy.service.codeexchange.SternBrocot.SternBrocotSearch.LEVEL;
import static com.craig.scholar.happy.service.codeexchange.SternBrocot.SternBrocotSearch.PATH;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;
import static java.math.BigInteger.ZERO;

import com.craig.scholar.happy.model.BigFraction;
import com.craig.scholar.happy.model.Fraction;
import com.craig.scholar.happy.model.SternBrocotTree;
import com.craig.scholar.happy.model.SternBrocotTree.Direction;
import com.craig.scholar.happy.model.SternBrocotTree.PreviousFraction;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class SternBrocot implements HappyCodingV2<Integer, Void> {

  enum SternBrocotSearch {
    PATH,
    FRACTION,
    LEVEL
  }

  record SternBrocotInput(
      BigFraction fraction,
      BigFraction l,
      BigFraction m,
      BigFraction r,
      BigInteger nextLevel,
      BigInteger position,
      SternBrocotSearch sternBrocotSearch,
      BigInteger level
  ) {

    public SternBrocotInput(BigFraction fraction, BigFraction l, BigFraction r,
        BigInteger nextLevel,
        BigInteger position, SternBrocotSearch sternBrocotSearch) {
      this(fraction, l, null, r, nextLevel, position, sternBrocotSearch, null);
    }

    public SternBrocotInput(BigFraction l, BigFraction r, BigInteger nextLevel, BigInteger position,
        SternBrocotSearch sternBrocotSearch, BigInteger level) {
      this(null, l, null, r, nextLevel, position, sternBrocotSearch, level);
    }
  }

  @Override
  public Void execute(Integer n) {
    sternBrocot(new Fraction[]{new Fraction(0, 1), new Fraction(1, 0)}, n);
    return null;
  }

  public SternBrocotTree<BigFraction, BigInteger> findFraction(BigFraction fraction) {
    if (ZERO.compareTo(fraction.d()) == 0) {
      throw new IllegalArgumentException(String.format("Denominator is zero for : %s", fraction));
    }
    return getTree(new SternBrocotInput(fraction, new BigFraction(ZERO, ONE),
        new BigFraction(ONE, ZERO), ONE, ONE, FRACTION));
  }

  public SternBrocotTree<BigFraction, BigInteger> findFractionPathV2(BigFraction bigFraction) {
    SternBrocotTree<BigFraction, BigInteger> tree = new SternBrocotTree<>(
        new BigFraction(ZERO, ONE), new BigFraction(ONE, ONE), new BigFraction(ONE, ZERO), ONE,
        ONE);
    SternBrocotTree.Direction direction = Direction.START;
    LinkedList<SternBrocotTree.PreviousFraction<BigFraction>> previousFractions = new LinkedList<>();
    while (true) {
      previousFractions.add(new PreviousFraction<>(tree.getFraction(), direction));
      if (tree.getFraction().isSame(bigFraction)) {
        tree.setPreviousFractions(previousFractions);
        tree.setLeftFraction(null);
        tree.setRightFraction(null);
        return tree;
      } else if (tree.getFraction().isLarger(bigFraction)) {
        tree = new SternBrocotTree<>(
            tree.getLeftFraction(), add(tree.getLeftFraction(), tree.getFraction()),
            tree.getFraction(),
            tree.getLevel().add(ONE), TWO.multiply(tree.getPosition()).subtract(ONE));
        direction = Direction.LEFT;
      } else if (tree.getFraction().isSmaller(bigFraction)) {
        tree = new SternBrocotTree<>(
            tree.getFraction(), add(tree.getFraction(), tree.getRightFraction()),
            tree.getRightFraction(),
            tree.getLevel().add(ONE), TWO.multiply(tree.getPosition()));
        direction = Direction.RIGHT;
      }
    }
  }

  public SternBrocotTree<BigFraction, BigInteger> findFractionPath(BigFraction fraction) {
    if (ZERO.compareTo(fraction.d()) == 0) {
      throw new IllegalArgumentException(String.format("Denominator is zero for : %s", fraction));
    }
    return getTree(new SternBrocotInput(fraction, new BigFraction(ZERO, ONE),
        new BigFraction(ONE, ZERO), ONE, ONE, PATH));
  }

  public SternBrocotTree<BigFraction, BigInteger> getTree(BigInteger level) {
    return getTree(new SternBrocotInput(new BigFraction(ZERO, ONE),
        new BigFraction(ONE, ZERO), ONE, ONE, LEVEL, level));
  }

  //pointless as only left side of tree is printed
//  public void getTree() {
//    getTree(new SternBrocotInput(new BigFraction(ZERO, ONE),
//        new BigFraction(ONE, ZERO), ONE, ONE));
//  }

  private SternBrocotTree<BigFraction, BigInteger> getTree(SternBrocotInput input) {
    if (LEVEL.equals(input.sternBrocotSearch)) {
      if (input.nextLevel.compareTo(input.level) > 0) {
        return null;
      }
    }
    SternBrocotTree<BigFraction, BigInteger> tree = new SternBrocotTree<>(input.l,
        Objects.requireNonNullElseGet(input.m, () -> add(input.l, input.r)), input.r,
        input.nextLevel, input.position);
    if (input.sternBrocotSearch == null) {
      System.out.println(tree.getFraction());
    }
    if ((PATH.equals(input.sternBrocotSearch) || FRACTION.equals(input.sternBrocotSearch))
        && tree.getFraction().isSame(input.fraction)) {
      return new SternBrocotTree<>(null, tree.getFraction(), null, input.nextLevel, input.position);
    }

    boolean searchNotPathAndFraction =
        !PATH.equals(input.sternBrocotSearch) && !FRACTION.equals(input.sternBrocotSearch);
    if (searchNotPathAndFraction
        || tree.getFraction().isLarger(input.fraction)) {
      SternBrocotTree<BigFraction, BigInteger> left = getTree(
          new SternBrocotInput(input.fraction, tree.getLeftFraction(),
              add(tree.getLeftFraction(), tree.getFraction()),
              tree.getFraction(), tree.getLevel().add(ONE),
              TWO.multiply(input.position).subtract(ONE), input.sternBrocotSearch, input.level));
      if (!FRACTION.equals(input.sternBrocotSearch)) {
        tree.setLeft(left);
      } else {
        return left;
      }
    }
    if (searchNotPathAndFraction
        || tree.getFraction().isSmaller(input.fraction)) {
      SternBrocotTree<BigFraction, BigInteger> right = getTree(
          new SternBrocotInput(input.fraction, tree.getFraction(),
              add(tree.getFraction(), tree.getRightFraction()),
              tree.getRightFraction(), tree.getLevel().add(ONE),
              TWO.multiply(input.position), input.sternBrocotSearch, input.level));
      if (!FRACTION.equals(input.sternBrocotSearch)) {
        tree.setRight(right);
      } else {
        return right;
      }
    }
    tree.setLeftFraction(null);
    tree.setRightFraction(null);
    if (input.sternBrocotSearch == null) {
      return null;
    }
    return tree;
  }

  public SternBrocotTree<Fraction, Integer> executeTreeRecursion(int level) {
    return executeTreeRecursion(new Fraction(0, 1), null,
        new Fraction(1, 0), 1, level, 1);
  }

  private SternBrocotTree<Fraction, Integer> executeTreeRecursion(Fraction l, Fraction m,
      Fraction r,
      int nextLevel, int level, int position) {
    if (nextLevel > level) {
      return null;
    }
    SternBrocotTree<Fraction, Integer> tree = new SternBrocotTree<>(l,
        Objects.requireNonNullElseGet(m, () -> add(l, r)), r,
        nextLevel, position);
    tree.setLeft(executeTreeRecursion(tree.getLeftFraction(),
        add(tree.getLeftFraction(), tree.getFraction()), tree.getFraction(),
        tree.getLevel() + 1, level,
        2 * position - 1));
    tree.setRight(executeTreeRecursion(tree.getFraction(),
        add(tree.getFraction(), tree.getRightFraction()), tree.getRightFraction(),
        tree.getLevel() + 1,
        level, 2 * position));
    tree.setLeftFraction(null);
    tree.setRightFraction(null);
    return tree;
  }

  public SternBrocotTree<Fraction, Integer> executeTree(int level) {
    SternBrocotTree<Fraction, Integer> tree = new SternBrocotTree<>(new Fraction(0, 1),
        new Fraction(1, 1),
        new Fraction(1, 0), 1, 1);
    Queue<SternBrocotTree<Fraction, Integer>> queue = new LinkedList<>();
    queue.add(tree);
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size > 0) {
        SternBrocotTree<Fraction, Integer> n = queue.poll();
        if (n == null) {
          throw new IllegalArgumentException("Something went horribly wrong");
        }
        System.out.println(n.getFraction());
        if (level > 1) {
          n.setLeft(new SternBrocotTree<>(n.getLeftFraction(),
              add(n.getLeftFraction(), n.getFraction()), n.getFraction(),
              n.getLevel() + 1, 2 * n.getPosition() - 1));
          n.setRight(new SternBrocotTree<>(n.getFraction(),
              add(n.getFraction(), n.getRightFraction()),
              n.getRightFraction(), n.getLevel() + 1, 2 * n.getPosition()));
          queue.add(n.getLeft());
          queue.add(n.getRight());
        }
        n.setLeftFraction(null);
        n.setRightFraction(null);
        size--;
      }
      level--;
    }
    return tree;
  }

  public void generateTree() {
    Queue<SternBrocotTree<BigFraction, BigInteger>> queue = new LinkedList<>();
    queue.add(new SternBrocotTree<>(new BigFraction(ZERO, ONE),
        new BigFraction(ONE, ONE),
        new BigFraction(ONE, ZERO), ONE, ONE));
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size > 0) {
        SternBrocotTree<BigFraction, BigInteger> n = queue.poll();
        if (n == null) {
          throw new IllegalArgumentException("Something went horribly wrong");
        }
        System.out.println(n.getFraction());
        queue.add(new SternBrocotTree<>(n.getLeftFraction(),
            add(n.getLeftFraction(), n.getFraction()), n.getFraction(),
            n.getLevel().add(ONE), TWO.multiply(n.getPosition()).subtract(ONE)));
        queue.add(new SternBrocotTree<>(n.getFraction(),
            add(n.getFraction(), n.getRightFraction()),
            n.getRightFraction(), n.getLevel().add(ONE), TWO.multiply(n.getPosition())));
        n.setLeftFraction(null);
        n.setRightFraction(null);
        size--;
      }
    }
  }

  public Fraction r_n(int n) {
    int[] s = new int[n + 2];
    s[1] = 1;
    s[2] = 1;
    for (int i = 3; i <= n + 1; i++) {
      s[i] = i % 2 == 0 ? s[i / 2] : s[(i - 1) / 2] + s[(i + 1) / 2];
    }
    return new Fraction(s[n], s[n + 1]);
  }

  private void sternBrocot(Fraction[] fs, int order) {
    if (order == 0) {
      return;
    }
    Arrays.stream(fs)
        .forEach(f -> System.out.printf("%s ", f));
    System.out.println();
    Fraction[] nfs = new Fraction[2 * fs.length - 1];
    for (int i = 0; i < nfs.length; i++) {
      if (i % 2 == 0) {
        nfs[i] = fs[i / 2];
      } else {
        nfs[i] = add(fs[i / 2], fs[(i + 1) / 2]);
      }
    }
    sternBrocot(nfs, order - 1);
  }

  private Fraction add(Fraction f1, Fraction f2) {
    return new Fraction(f1.n() + f2.n(), f1.d() + f2.d());
  }

  private BigFraction add(BigFraction f1, BigFraction f2) {
    return new BigFraction(f1.n().add(f2.n()), f1.d().add(f2.d()));
  }
}
