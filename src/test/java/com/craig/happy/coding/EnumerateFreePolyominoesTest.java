package com.craig.happy.coding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EnumerateFreePolyominoesTest {

  private final EnumerateFreePolyominoes enumerateFreePolyominoes = new EnumerateFreePolyominoes();

  private static Stream<Arguments> enumerateFreePolyominoesCases() {
    return Stream.of(
        Arguments.of(
            0, 1
        ),
        Arguments.of(
            1, 1
        ),
        Arguments.of(
            2, 1
        ),
        Arguments.of(
            3, 2
        ),
        Arguments.of(
            4, 5
        ),
        Arguments.of(
            5, 12
        )
    );
  }

  @ParameterizedTest
  @MethodSource("enumerateFreePolyominoesCases")
  void testGetNumberOfFreePolyominoes(int n, int expectedCount) {
    assertEquals(expectedCount, enumerateFreePolyominoes.getNumberOfPolyominoes(n));
  }

  @Test
  void testEnumerateFreePolyominoes() {
    int n = 4;
    List<boolean[][]> freePolyominoes = enumerateFreePolyominoes.enumerateFreePolyominoes(n);
    System.out.printf("Number: %d, Count: %d%n\n", n, freePolyominoes.size());
    System.out.println("--------------");
    freePolyominoes
        .forEach(freePolyomino -> {
          for (int i = 0; i < freePolyomino.length; i++) {
            for (int j = 0; j < freePolyomino[0].length; j++) {
              System.out.print((freePolyomino[i][j] ? "*" : "_") + " ");
            }
            System.out.println();
          }
          System.out.println("--------------");
        });
  }
}
