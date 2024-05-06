package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.craig.scholar.happy.model.either.HappyPair;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IsACartesianProductTest {

  private final IsACartesianProduct isACartesianProduct = new IsACartesianProduct();

  private static Stream<Arguments> isACartesianProductCases() {
    return Stream.of(
        Arguments.of(
            List.of(
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 3),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(5, 7),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 3),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 7)
            ),
            false
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(6, 1),
                new HappyPair<>(1, 6)
            ),
            false
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 4),
                new HappyPair<>(7, 4),
                new HappyPair<>(9, 6)
            ),
            false
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 1),
                new HappyPair<>(1, 1),
                new HappyPair<>(1, 2),
                new HappyPair<>(2, 1),
                new HappyPair<>(2, 2)
            ),
            false
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 2),
                new HappyPair<>(2, 2),
                new HappyPair<>(3, 2),
                new HappyPair<>(1, 3),
                new HappyPair<>(2, 3)
            ),
            false
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 2),
                new HappyPair<>(3, 4)
            ),
            false
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 7),
                new HappyPair<>(1, 8),
                new HappyPair<>(1, 9),
                new HappyPair<>(1, 9),
                new HappyPair<>(1, 9),
                new HappyPair<>(2, 7),
                new HappyPair<>(2, 8),
                new HappyPair<>(2, 9),
                new HappyPair<>(2, 9),
                new HappyPair<>(2, 9),
                new HappyPair<>(2, 7),
                new HappyPair<>(2, 8),
                new HappyPair<>(2, 9),
                new HappyPair<>(2, 9),
                new HappyPair<>(2, 9),
                new HappyPair<>(3, 7),
                new HappyPair<>(3, 8),
                new HappyPair<>(3, 9),
                new HappyPair<>(3, 9),
                new HappyPair<>(3, 9)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 1),
                new HappyPair<>(1, 1),
                new HappyPair<>(1, 2),
                new HappyPair<>(1, 2),
                new HappyPair<>(2, 1),
                new HappyPair<>(2, 1),
                new HappyPair<>(2, 2),
                new HappyPair<>(2, 2)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(4, 7),
                new HappyPair<>(4, 6),
                new HappyPair<>(4, 5),
                new HappyPair<>(4, 4)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 1),
                new HappyPair<>(1, 1),
                new HappyPair<>(2, 1)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 1),
                new HappyPair<>(1, 2)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 1)
            ),
            true
        ),
        Arguments.of(
            List.of(
                new HappyPair<>(1, 4),
                new HappyPair<>(1, 6),
                new HappyPair<>(2, 4),
                new HappyPair<>(2, 6),
                new HappyPair<>(7, 4),
                new HappyPair<>(7, 6),
                new HappyPair<>(1, 4),
                new HappyPair<>(1, 6)
            ),
            true
        )
    );
  }

  @ParameterizedTest
  @MethodSource("isACartesianProductCases")
  void isACartesianProduct(List<HappyPair<Integer, Integer>> products, boolean expected) {
    assertThat(isACartesianProduct.execute(products)).isEqualTo(expected);
  }

}