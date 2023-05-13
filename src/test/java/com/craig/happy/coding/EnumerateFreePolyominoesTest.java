package com.craig.happy.coding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class EnumerateFreePolyominoesTest {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  private EnumerateFreePolyominoes enumerateFreePolyominoes = new EnumerateFreePolyominoes();

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
    List<boolean[][]> freePolyominoes = enumerateFreePolyominoes.enumerate(n);
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
