package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.craig.scholar.happy.model.BigFraction;
import com.craig.scholar.happy.model.Fraction;
import com.craig.scholar.happy.model.SternBrocotTree;
import com.craig.scholar.happy.model.SternBrocotTree.Direction;
import com.craig.scholar.happy.model.SternBrocotTree.PreviousFraction;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

  @ParameterizedTest
  @MethodSource("findFractionPathV2Cases")
  void findFractionPathV2(BigFraction fraction,
      List<PreviousFraction<BigFraction>> expectedFractions) {
    SternBrocotTree<BigFraction, BigInteger> tree = sternBrocot.findFractionPathV2(
        fraction);
    assertThat(tree.getPreviousFractions()).containsExactlyElementsOf(expectedFractions);
  }

  @Test
  void generateTree() {
//    new BigFraction("99999999999", "1"))
    SternBrocotTree<BigFraction, BigInteger> tree = sternBrocot.findFractionPathV2(
        new BigFraction("2000", "1000"));
    System.out.println(tree.getPreviousFractions());
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

  @Test
  void getTree_BigInteger() {
    SternBrocotTree<BigFraction, BigInteger> tree = sternBrocot.getTree(BigInteger.valueOf(4));
    assertAndVerifyBig(tree);
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

  private void assertAndVerifyBig(SternBrocotTree<BigFraction, BigInteger> tree) {
    assertThat(tree).isNotNull();
    Queue<SternBrocotTree<BigFraction, BigInteger>> q = new LinkedList<>();
    q.add(tree);
    FractionNodeTest[] fractionNodeTests = getFractionNodeTests();
    int i = 0;
    while (!q.isEmpty()) {
      SternBrocotTree<BigFraction, BigInteger> n = q.poll();
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

  static Stream<Arguments> findFractionTestCases() {
    return Stream.of(
        Arguments.of(
            new BigFraction("2", "3"),
            new BigFractionNodeTest("2", "3", "3", "2")
        ),
        Arguments.of(
            new BigFraction("5", "3"),
            new BigFractionNodeTest("5", "3", "4", "6")
        ),
        Arguments.of(
            new BigFraction("4", "6"),
            new BigFractionNodeTest("2", "3", "3", "2")
        ),
        Arguments.of(
            new BigFraction("1", "1"),
            new BigFractionNodeTest("1", "1", "1", "1")
        ),
        Arguments.of(
            new BigFraction("16", "4"),
            new BigFractionNodeTest("4", "1", "4", "8")
        ),
        Arguments.of(
            new BigFraction("5", "25"),
            new BigFractionNodeTest("1", "5", "5", "1")
        ),
        Arguments.of(
            new BigFraction("405", "709"),
            new BigFractionNodeTest("405", "709", "106", "11408855402054064613470328848384")
        )
    );
  }

  @ParameterizedTest
  @MethodSource("findFractionTestCases")
  void findFraction(BigFraction fraction, BigFractionNodeTest fractionNodeTest) {
    SternBrocotTree<BigFraction, BigInteger> tree = sternBrocot.findFraction(fraction);
    assertThat(tree.getFraction())
        .isEqualTo(new BigFraction(new BigInteger(fractionNodeTest.n),
            new BigInteger(fractionNodeTest.d)));
    assertThat(tree.getLevel()).isEqualTo(fractionNodeTest.level);
    assertThat(tree.getPosition()).isEqualTo(fractionNodeTest.position);
    assertThat(tree.getLeftFraction()).isNull();
    assertThat(tree.getRightFraction()).isNull();
    assertThat(tree.getLeft()).isNull();
    assertThat(tree.getRight()).isNull();
  }

  @Test
  void findFraction_ZeroDenominator() {
    assertThatThrownBy(() -> sternBrocot.findFraction(new BigFraction("1", "0")))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Denominator is zero");
  }

  record BigFractionNodeTest(String n, String d, String level, String position) {

  }

  static Stream<Arguments> findFractionPathCases() {
    return Stream.of(
        Arguments.of(
            new BigFraction("2", "3"), List.of(
                new BigFraction("1", "1"),
                new BigFraction("1", "2"),
                new BigFraction("2", "3")
            )
        ),
        Arguments.of(
            new BigFraction("5", "3"), List.of(
                new BigFraction("1", "1"),
                new BigFraction("2", "1"),
                new BigFraction("3", "2"),
                new BigFraction("5", "3")
            )
        ),
        Arguments.of(
            new BigFraction("1", "5"), List.of(
                new BigFraction("1", "1"),
                new BigFraction("1", "2"),
                new BigFraction("1", "3"),
                new BigFraction("1", "4"),
                new BigFraction("1", "5")
            )
        ),
        Arguments.of(
            new BigFraction("5", "1"), List.of(
                new BigFraction("1", "1"),
                new BigFraction("2", "1"),
                new BigFraction("3", "1"),
                new BigFraction("4", "1"),
                new BigFraction("5", "1")
            )
        ),
        Arguments.of(
            new BigFraction("5", "2"), List.of(
                new BigFraction("1", "1"),
                new BigFraction("2", "1"),
                new BigFraction("3", "1"),
                new BigFraction("5", "2")
            )
        ),
        Arguments.of(
            new BigFraction("1", "1"), List.of(
                new BigFraction("1", "1")
            )
        )
    );
  }

  static Stream<Arguments> findFractionPathV2Cases() {
    return Stream.of(
        Arguments.of(
            new BigFraction("2", "3"), List.of(
                new PreviousFraction<>(new BigFraction("1", "1"), Direction.START),
                new PreviousFraction<>(new BigFraction("1", "2"), Direction.LEFT),
                new PreviousFraction<>(new BigFraction("2", "3"), Direction.RIGHT)
            )
        ),
        Arguments.of(
            new BigFraction("5", "3"), List.of(
                new PreviousFraction<>(new BigFraction("1", "1"), Direction.START),
                new PreviousFraction<>(new BigFraction("2", "1"), Direction.RIGHT),
                new PreviousFraction<>(new BigFraction("3", "2"), Direction.LEFT),
                new PreviousFraction<>(new BigFraction("5", "3"), Direction.RIGHT)
            )
        ),
        Arguments.of(
            new BigFraction("1", "5"), List.of(
                new PreviousFraction<>(new BigFraction("1", "1"), Direction.START),
                new PreviousFraction<>(new BigFraction("1", "2"), Direction.LEFT),
                new PreviousFraction<>(new BigFraction("1", "3"), Direction.LEFT),
                new PreviousFraction<>(new BigFraction("1", "4"), Direction.LEFT),
                new PreviousFraction<>(new BigFraction("1", "5"), Direction.LEFT)
            )
        ),
        Arguments.of(
            new BigFraction("5", "1"), List.of(
                new PreviousFraction<>(new BigFraction("1", "1"), Direction.START),
                new PreviousFraction<>(new BigFraction("2", "1"), Direction.RIGHT),
                new PreviousFraction<>(new BigFraction("3", "1"), Direction.RIGHT),
                new PreviousFraction<>(new BigFraction("4", "1"), Direction.RIGHT),
                new PreviousFraction<>(new BigFraction("5", "1"), Direction.RIGHT)
            )
        ),
        Arguments.of(
            new BigFraction("5", "2"), List.of(
                new PreviousFraction<>(new BigFraction("1", "1"), Direction.START),
                new PreviousFraction<>(new BigFraction("2", "1"), Direction.RIGHT),
                new PreviousFraction<>(new BigFraction("3", "1"), Direction.RIGHT),
                new PreviousFraction<>(new BigFraction("5", "2"), Direction.LEFT)
            )
        ),
        Arguments.of(
            new BigFraction("1", "1"), List.of(
                new PreviousFraction<>(new BigFraction("1", "1"), Direction.START)
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("findFractionPathCases")
  void findFractionPath(BigFraction fraction, List<BigFraction> expectedFractions) {
    SternBrocotTree<BigFraction, BigInteger> tree = sternBrocot.findFractionPath(
        fraction);
    List<BigFraction> actualFractions = getActualFractions(tree);
    assertThat(actualFractions).containsExactlyElementsOf(expectedFractions);
  }

  private List<BigFraction> getActualFractions(SternBrocotTree<BigFraction, BigInteger> tree) {
    List<BigFraction> fractions = new ArrayList<>();
    fractions.add(tree.getFraction());
    while (tree.getLeft() != null || tree.getRight() != null) {
      if (tree.getLeft() != null) {
        tree = tree.getLeft();
      } else {
        tree = tree.getRight();
      }
      fractions.add(tree.getFraction());
    }
    return fractions;
  }

  @Test
  void getTree() {
    System.out.println(sternBrocot.findFraction(new BigFraction("405", "709")));
  }
}