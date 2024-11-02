package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WordBreakTest {

  WordBreak wordBreak = new WordBreak();

  private static Stream<Arguments> wordBreakCases() {
    return Stream.of(
        Arguments.of(
            "wordbreakproblem",
            List.of("this", "th", "is", "famous", "word", "break", "b", "r", "e", "a", "k", "br",
                "bre", "brea", "ak", "problem"),
            List.of(
                List.of("word", "break", "problem"),
                List.of("word", "br", "e", "a", "k", "problem"),
                List.of("word", "b", "r", "e", "ak", "problem"),
                List.of("word", "b", "r", "e", "a", "k", "problem"),
                List.of("word", "bre", "ak", "problem"),
                List.of("word", "brea", "k", "problem"),
                List.of("word", "br", "e", "ak", "problem"),
                List.of("word", "bre", "a", "k", "problem")
            )
        ),
        Arguments.of(
            "abc",
            List.of("ab", "bc", "a", "b", "c"),
            List.of(
                List.of("ab", "c"),
                List.of("a", "b", "c"),
                List.of("a", "bc")
            )
        ),
        Arguments.of(
            "abcd",
            List.of("a", "ab", "bc", "b", "cd"),
            List.of(
                List.of("a", "b", "cd"),
                List.of("ab", "cd")
            )
        ),
        Arguments.of(
            "choices",
            List.of("c", "ch", "ce", "oi", "hoi", "e", "s"),
            List.of(
                List.of("c", "hoi", "ce", "s"),
                List.of("ch", "oi", "ce", "s"),
                List.of("ch", "oi", "c", "e", "s")
            )
        ),
        Arguments.of(
            "cac",
            List.of("ca", "c", "a", "c"),
            List.of(
                List.of("c", "a", "c"),
                List.of("ca", "c")
            )
        )
    );
  }

  @ParameterizedTest
  @MethodSource("wordBreakCases")
  void getCombinations(String str, List<String> dictionary, List<List<String>> combos) {
    assertThat(wordBreak.getCombinations(str, dictionary)).hasSameElementsAs(combos);
  }
}