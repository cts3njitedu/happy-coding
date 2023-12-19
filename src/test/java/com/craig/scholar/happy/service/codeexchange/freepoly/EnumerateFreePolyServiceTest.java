package com.craig.scholar.happy.service.codeexchange.freepoly;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.jetbrains.annotations.NotNull;

public class EnumerateFreePolyServiceTest {

  // 1, 1, 1, 2, 5, 12, 35, 108, 369, 1285, 4655, 17073, 63600,
  // 238591, 901971, 3426576, 13079255, 50107909, 192622052, 742624232, 2870671950,
  // 11123060678, 43191857688, 168047007728, 654999700403, 2557227044764,
  // 9999088822075, 39153010938487, 153511100594603

  public void enumerate(EnumerateFreePolyService<?> enumerateFreePolyService) {
    List<EnumerateFreePolyominoesArgument> arguments = getEnumerateFreePolyominoesArguments();
    arguments.forEach(argument -> assertThat(argument.expectedCount).isEqualTo(
        enumerateFreePolyService.enumerate(argument.n).size()));
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

  static class EnumerateFreePolyominoesArgument {

    int n;
    int expectedCount;

    public EnumerateFreePolyominoesArgument(int n, int expectedCount) {
      this.n = n;
      this.expectedCount = expectedCount;
    }
  }
}
