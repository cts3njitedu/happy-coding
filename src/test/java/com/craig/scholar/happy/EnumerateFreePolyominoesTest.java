package com.craig.scholar.happy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.craig.scholar.happy.service.codeexchange.EnumerateFreePolyominoes;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class EnumerateFreePolyominoesTest {

  private final EnumerateFreePolyominoes enumerateFreePolyominoes = new EnumerateFreePolyominoes();

  private static class EnumerateFreePolyominoesArgument {

    int n;
    int expectedCount;

    public EnumerateFreePolyominoesArgument(int n, int expectedCount) {
      this.n = n;
      this.expectedCount = expectedCount;
    }
  }

  @Test
  void testGetNumberOfFreePolyominoes() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertEquals(argument.expectedCount,
        enumerateFreePolyominoes.getNumberOfFreePolyominoes(argument.n)));
  }

  @Test
  void testGetNumberOfFreePolyominoesV2() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertEquals(argument.expectedCount,
        enumerateFreePolyominoes.enumerateFreePolyominoesV2(argument.n)));
  }

  @Test
  void testGetNumberOfFreePolyominoesV3() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertEquals(argument.expectedCount,
        enumerateFreePolyominoes.enumerateFreePolyominoesV3(argument.n).size()));
  }

  @Test
  void testGetNumberOfFreePolyominoesV4() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertEquals(argument.expectedCount,
        enumerateFreePolyominoes.enumerateFreePolyominoesV4(argument.n).size()));
  }

  @Test
  void testGetNumberOfFreePolyominoesV5() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertEquals(argument.expectedCount,
        enumerateFreePolyominoes.enumerateFreePolyominoesV5(argument.n).size()));
  }

  @NotNull
  private static List<EnumerateFreePolyominoesArgument> getEnumerateFreePolyominoesArguments() {
    return List.of(
        new EnumerateFreePolyominoesArgument(0, 1),
        new EnumerateFreePolyominoesArgument(1, 1),
        new EnumerateFreePolyominoesArgument(2, 1),
        new EnumerateFreePolyominoesArgument(3, 2),
        new EnumerateFreePolyominoesArgument(4, 5),
        new EnumerateFreePolyominoesArgument(5, 12),
        new EnumerateFreePolyominoesArgument(6, 35),
        new EnumerateFreePolyominoesArgument(7, 108),
        new EnumerateFreePolyominoesArgument(8, 369),
        new EnumerateFreePolyominoesArgument(9, 1285),
        new EnumerateFreePolyominoesArgument(10, 4655)
    );
  }

  @Test
  void testEnumerateFreePolyominoes() {
    int n = 10;
    Collection<?> polys = enumerateFreePolyominoes.enumerateFreePolyominoesV5(n);
    System.out.println(polys.size());
//    MatrixUtil.print(n, polys, "[]");
  }
}
