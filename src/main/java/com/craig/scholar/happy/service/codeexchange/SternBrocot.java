package com.craig.scholar.happy.service.codeexchange;


import com.craig.scholar.happy.model.Fraction;
import com.craig.scholar.happy.model.SternBrocotTree;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class SternBrocot implements HappyCodingV2<Integer, Void> {


  @Override
  public Void execute(Integer n) {
    sternBrocot(new Fraction[]{new Fraction(0, 1), new Fraction(1, 0)}, n);
    return null;
  }

  public SternBrocotTree<Fraction> executeTreeRecursion(int level) {
    return executeTreeRecursion(new Fraction(0, 1), null,
        new Fraction(1, 0), 1, level, 1);
  }

  public SternBrocotTree<Fraction> executeTreeRecursion(Fraction l, Fraction m, Fraction r,
      int nextLevel, int level, int position) {
    if (nextLevel > level) {
      return null;
    }
    SternBrocotTree<Fraction> tree = new SternBrocotTree<>(l,
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

  public SternBrocotTree<Fraction> executeTree(int level) {
    SternBrocotTree<Fraction> tree = new SternBrocotTree<>(new Fraction(0, 1), new Fraction(1, 1),
        new Fraction(1, 0), 1, 1);
    Queue<SternBrocotTree<Fraction>> queue = new LinkedList<>();
    queue.add(tree);
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size > 0) {
        SternBrocotTree<Fraction> n = queue.poll();
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

  public Fraction r_n(int n) {
    int[] s = new int[n + 2];
    s[1] = 1;
    s[2] = 1;
    for (int i = 3; i <= n + 1; i++) {
      if (i % 2 == 0) {
        s[i] = s[i / 2];
      } else {
        s[i] = s[(i - 1) / 2] + s[(i + 1) / 2];
      }
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

  Fraction add(Fraction f1, Fraction f2) {
    return new Fraction(f1.n() + f2.n(), f1.d() + f2.d());
  }
}
