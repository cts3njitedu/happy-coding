package com.craig.scholar.happy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.craig.scholar.happy.service.codeexchange.EnumerateFreePolyominoes;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

public class EnumerateFreePolyominoesTest {

  // 1, 1, 1, 2, 5, 12, 35, 108, 369, 1285, 4655, 17073, 63600,
  // 238591, 901971, 3426576, 13079255, 50107909, 192622052, 742624232, 2870671950,
  // 11123060678, 43191857688, 168047007728, 654999700403, 2557227044764,
  // 9999088822075, 39153010938487, 153511100594603
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
    arguments.forEach(argument -> assertThat(argument.expectedCount).isEqualTo(
        enumerateFreePolyominoes.getNumberOfFreePolyominoes(argument.n)));
  }

  @Test
  void testGetNumberOfFreePolyominoesV2() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertThat(argument.expectedCount).isEqualTo(
        enumerateFreePolyominoes.enumerateFreePolyominoesV2(argument.n)));
  }

  @Test
  void testGetNumberOfFreePolyominoesV3() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertThat(argument.expectedCount).isEqualTo(
        enumerateFreePolyominoes.enumerateFreePolyominoesV3(argument.n)));
  }

  @Test
  void testGetNumberOfFreePolyominoesV4() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertThat(argument.expectedCount).isEqualTo(
        enumerateFreePolyominoes.enumerateFreePolyominoesV4(argument.n)));
  }

  @Test
  void testGetNumberOfFreePolyominoesV5() {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertThat(argument.expectedCount).isEqualTo(
        enumerateFreePolyominoes.enumerateFreePolyominoesV5(argument.n)));
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
    int n = 16;
    Collection<?> polys = enumerateFreePolyominoes.enumerateFreePolyominoesV5(n);
    System.out.println(polys.size());
//    MatrixUtil.print2(n, polys, "<>");
  }

  @Test
  void enumerateFreePolyominoes_NumberGreaterThan15_Exception() {
    assertThatThrownBy(() -> enumerateFreePolyominoes.enumerateFreePolyominoesV5(16))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please pass in positive number less than or equal")
        .hasMessageContaining("16");
  }

  @Test
  void enumerateFreePolyominoes_NegativeNumber_Exception() {
    assertThatThrownBy(() -> enumerateFreePolyominoes.enumerateFreePolyominoesV5(-1))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Please pass in positive number less than or equal")
        .hasMessageContaining("-1");
  }
}
