package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.craig.scholar.happy.model.BigPoint;
import com.craig.scholar.happy.model.Point;
import java.math.BigInteger;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IndexIntoAFibonacciTilingTest {

  IndexIntoAFibonacciTiling indexIntoAFibonacciTiling = new IndexIntoAFibonacciTiling();

  static Stream<Arguments> testCases() {
    return Stream.of(
        Arguments.of(0, 0, 1),
        Arguments.of(1, 0, 1),
        Arguments.of(1, 1, 2),
        Arguments.of(-3, 1, 3),
        Arguments.of(-3, -1, 5),
        Arguments.of(3, -2, 8),
        Arguments.of(6, 15, 13),
        Arguments.of(-4, -5, 21),
        Arguments.of(-4, -6, 34),
        Arguments.of(145, 214, 610)
    );
  }

  @ParameterizedTest
  @MethodSource("testCases")
  void execute(int x, int y, int expectedFibonacci) {
    assertThat(indexIntoAFibonacciTiling.execute(new Point(x, y))).isEqualTo(expectedFibonacci);
  }

  static Stream<Arguments> bigTestCases() {
    return Stream.of(
        Arguments.of("0", "0", "1"),
        Arguments.of("1", "0", "1"),
        Arguments.of("1", "1", "2"),
        Arguments.of("-3", "1", "3"),
        Arguments.of("-3", "-1", "5"),
        Arguments.of("3", "-2", "8"),
        Arguments.of("6", "15", "13"),
        Arguments.of("-4", "-5", "21"),
        Arguments.of("-4", "-6", "34"),
        Arguments.of("145", "214", "610")
    );
  }

  @ParameterizedTest
  @MethodSource("bigTestCases")
  void executeBig(String x, String y, String expectedFibonacci) {
    assertThat(indexIntoAFibonacciTiling.execute(
        new BigPoint(new BigInteger(x), new BigInteger(y)))).isEqualTo(
        new BigInteger(expectedFibonacci));
  }

  @Test
  void executeBigSingle() {
    System.out.println(indexIntoAFibonacciTiling.execute(
        new BigPoint(new BigInteger("4351241234"), new BigInteger("743452314"))));
  }
}