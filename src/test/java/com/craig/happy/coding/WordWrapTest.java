package com.craig.happy.coding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordWrapTest {

    private WordWrap wordWrap = new WordWrap();

    private static Stream<Arguments> wordWrapCases() {
        return Stream.of(
                Arguments.of("The fox jump over the moon", 4, "The\nfox\njump\nover\nthe\nmoon"),
                Arguments.of("test", 7, "test"),
                Arguments.of("hello world", 7, "hello\nworld"),
                Arguments.of("a lot of words for a single line", 10, "a lot of\nwords for\na single\nline"),
                Arguments.of("this is a test", 4, "this\nis a\ntest"),
                Arguments.of("a long word", 6, "a long\nword"),
                Arguments.of("areallylongword", 6, "areall\nylongw\nord"),
                Arguments.of("greedy whenthewordistoolong", 6, "greedy\nwhenth\newordi\nstoolo\nng"),
                Arguments.of("greedy whenthewordistoolong", 7, "greedy\nwhenthe\nwordist\noolong")
        );
    }

    @ParameterizedTest
    @MethodSource("wordWrapCases")
    void testWordWrap(String word, int column, String expectedWordWrap) {
        assertEquals(expectedWordWrap, wordWrap.execute(word, column));
    }
}
