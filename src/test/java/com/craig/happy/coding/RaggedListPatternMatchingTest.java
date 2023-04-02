package com.craig.happy.coding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RaggedListPatternMatchingTest {

  public RaggedListPatternMatching raggedListPatternMatching = new RaggedListPatternMatching();

  private static Stream<Arguments> raggedListPatternMatchingCases() {
    return Stream.of(
        Arguments.of(
            List.of(),
            List.of(),
            true
        ),
        Arguments.of(
            List.of(0),
            List.of(1),
            true
        ),
        Arguments.of(
            List.of(1, 0),
            List.of(1, List.of(2, 3)),
            true
        ),
        Arguments.of(
            List.of(1, 0),
            List.of(1, List.of(List.of(2, 3))),
            true
        ),
        Arguments.of(
            List.of(1, List.of(2, 0), 4),
            List.of(1, List.of(2, 3), 4),
            true
        ),
        Arguments.of(
            List.of(0, List.of(4, List.of(5), 0)),
            List.of(List.of(1, 2, List.of(3)), List.of(4, List.of(5), List.of(6, List.of(7)))),
            true
        ),
        Arguments.of(
            List.of(1),
            List.of(),
            false
        ),
        Arguments.of(
            List.of(0),
            List.of(),
            false
        ),
        Arguments.of(
            List.of(List.of()),
            List.of(),
            false
        ),
        Arguments.of(
            List.of(List.of()),
            List.of(3),
            false
        ),
        Arguments.of(
            List.of(List.of(4)),
            List.of(4),
            false
        ),
        Arguments.of(
            List.of(0),
            List.of(1, 2),
            false
        ),
        Arguments.of(
            List.of(1, 0),
            List.of(1),
            false
        ),
        Arguments.of(
            List.of(0),
            List.of(List.of(1), List.of(2)),
            false
        ),
        Arguments.of(
            List.of(1, 0, List.of(2, 3)),
            List.of(1, List.of(2, 3)),
            false
        ),
        Arguments.of(
            List.of(1, List.of(0, 2), 4),
            List.of(1, List.of(2, 3), 4),
            false
        ),
        Arguments.of(
            List.of(List.of(0), List.of(4, List.of(5), 0)),
            List.of(List.of(1, 2, List.of(3)), List.of(4, List.of(5), List.of(6, List.of(7)))),
            false
        )
    );
  }

  @ParameterizedTest
  @MethodSource("raggedListPatternMatchingCases")
  void testZipRaggedLists(Object object1, Object object2, boolean isMatch) {
    assertEquals(isMatch, raggedListPatternMatching.isMatch(object1, object2));
  }
}
