package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.craig.scholar.happy.model.BigFraction;
import com.craig.scholar.happy.model.Fraction;
import com.craig.scholar.happy.model.SternBrocotTree;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SternBrocotTest {

  SternBrocot sternBrocot = new SternBrocot();

  @Test
  void execute() {
    sternBrocot.execute(5);
  }

  static Stream<Arguments> rnTestCases() {
    return Stream.of(
        Arguments.of(
            1,
            new Fraction(1, 1)
        ),
        Arguments.of(
            2,
            new Fraction(1, 2)
        ),
        Arguments.of(
            3,
            new Fraction(2, 1)
        ),
        Arguments.of(
            4,
            new Fraction(1, 3)
        ),
        Arguments.of(
            5,
            new Fraction(3, 2)
        ),
        Arguments.of(
            6,
            new Fraction(2, 3)
        ),
        Arguments.of(
            7,
            new Fraction(3, 1)
        ),
        Arguments.of(
            8,
            new Fraction(1, 4)
        ),
        Arguments.of(
            9,
            new Fraction(4, 3)
        ),
        Arguments.of(
            10,
            new Fraction(3, 5)
        ),
        Arguments.of(
            11,
            new Fraction(5, 2)
        ),
        Arguments.of(
            12,
            new Fraction(2, 5)
        ),
        Arguments.of(
            13,
            new Fraction(5, 3)
        ),
        Arguments.of(
            14,
            new Fraction(3, 4)
        ),
        Arguments.of(
            15,
            new Fraction(4, 1)
        ),
        Arguments.of(
            16,
            new Fraction(1, 5)
        ),
        Arguments.of(
            17,
            new Fraction(5, 4)
        ),
        Arguments.of(
            18,
            new Fraction(4, 7)
        ),
        Arguments.of(
            19,
            new Fraction(7, 3)
        ),
        Arguments.of(
            20,
            new Fraction(3, 8)
        ),
        Arguments.of(
            50,
            new Fraction(7, 12)
        ),
        Arguments.of(
            100,
            new Fraction(7, 19)
        ),
        Arguments.of(
            1000,
            new Fraction(11, 39)
        )
    );
  }

  @Test
  void findFraction() {
    System.out.println(sternBrocot.findFraction(
        new BigFraction(BigInteger.valueOf(200), BigInteger.valueOf(450))));
  }

  record FractionNodeTest(int n, int d, int level, int position) {

  }

  @ParameterizedTest
  @MethodSource("rnTestCases")
  void r_n(int n, Fraction exectedFraction) {
    assertThat(sternBrocot.r_n(n)).isEqualTo(exectedFraction);
  }

  @Test
  void executeTree() {
    SternBrocotTree<Fraction, Integer> tree = sternBrocot.executeTree(4);
    assertAndVerify(tree);
  }

  @Test
  void executeTreeRecursion() {
    SternBrocotTree<Fraction, Integer> tree = sternBrocot.executeTreeRecursion(4);
    assertAndVerify(tree);
  }

  private void assertAndVerify(SternBrocotTree<Fraction, Integer> tree) {
    assertThat(tree).isNotNull();
    Queue<SternBrocotTree<Fraction, Integer>> q = new LinkedList<>();
    q.add(tree);
    FractionNodeTest[] fractionNodeTests = getFractionNodeTests();
    int i = 0;
    while (!q.isEmpty()) {
      SternBrocotTree<Fraction, Integer> n = q.poll();
      FractionNodeTest fractionNodeTest = fractionNodeTests[i++];
      assertThat(n.getFraction().n()).isEqualTo(fractionNodeTest.n());
      assertThat(n.getFraction().d()).isEqualTo(fractionNodeTest.d());
      assertThat(n.getLevel()).isEqualTo(fractionNodeTest.level());
      assertThat(n.getPosition()).isEqualTo(fractionNodeTest.position());
      assertThat(n.getLeftFraction()).isNull();
      assertThat(n.getRightFraction()).isNull();
      if (n.getLeft() != null) {
        q.add(n.getLeft());
      }
      if (n.getRight() != null) {
        q.add(n.getRight());
      }
    }
    assertThat(fractionNodeTests)
        .hasSize(i);
  }

  FractionNodeTest[] getFractionNodeTests() {
    return new FractionNodeTest[]{
        new FractionNodeTest(1, 1, 1, 1),
        new FractionNodeTest(1, 2, 2, 1),
        new FractionNodeTest(2, 1, 2, 2),
        new FractionNodeTest(1, 3, 3, 1),
        new FractionNodeTest(2, 3, 3, 2),
        new FractionNodeTest(3, 2, 3, 3),
        new FractionNodeTest(3, 1, 3, 4),
        new FractionNodeTest(1, 4, 4, 1),
        new FractionNodeTest(2, 5, 4, 2),
        new FractionNodeTest(3, 5, 4, 3),
        new FractionNodeTest(3, 4, 4, 4),
        new FractionNodeTest(4, 3, 4, 5),
        new FractionNodeTest(5, 3, 4, 6),
        new FractionNodeTest(5, 2, 4, 7),
        new FractionNodeTest(4, 1, 4, 8)
    };
  }
}