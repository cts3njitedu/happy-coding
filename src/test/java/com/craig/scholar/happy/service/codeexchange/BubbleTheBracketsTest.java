package com.craig.scholar.happy.service.codeexchange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BubbleTheBracketsTest {

    private final BubbleTheBrackets bubbleTheBrackets = new BubbleTheBrackets();

    private static Stream<Arguments> bubbleCases() {
        return Stream.of(
                Arguments.of(
                        "()(())",
                        Set.of("(())()", "()(())")
                ),
                Arguments.of(
                        "(()())()()",
                        Set.of(
                                "(()())()()",
                                "()(()())()",
                                "()()(()())"
                        )
                ),
                Arguments.of(
                        "(()(()))()",
                        Set.of(
                                "((())())()",
                                "()((())())",
                                "(()(()))()",
                                "()(()(()))"
                        )
                ),
                Arguments.of(
                        "(()(()((())())))()(()()())",
                        Set.of(
                                "((()((())()))())()(()()())",
                                "(()(()((())())))()(()()())",
                                "()(()()())(()(()(()(()))))",
                                "(()()())()(()(((())())()))",
                                "(()()())()((((())())())())",
                                "((((())())())())()(()()())",
                                "()(()()())((()((())()))())",
                                "(()()())(((()(()))())())()",
                                "(()(((())())()))()(()()())",
                                "()(()(((())())()))(()()())",
                                "(()(()(()(()))))()(()()())",
                                "((()(()(())))())(()()())()",
                                "(((()(()))())())()(()()())",
                                "(()()())()((()(()(())))())",
                                "()(()(()((())())))(()()())",
                                "()(()()())(()(()((())())))",
                                "(()()())((((())())())())()",
                                "(()()())(()((()(()))()))()",
                                "(()()())()(()(()(()(()))))",
                                "()(()((()(()))()))(()()())",
                                "(()((()(()))()))(()()())()",
                                "(()()())(()(((())())()))()",
                                "(()()())()((()((())()))())",
                                "((()((())()))())(()()())()",
                                "(()(()((())())))(()()())()",
                                "()(()()())(()((()(()))()))",
                                "(()()())((()(()(())))())()",
                                "()(((()(()))())())(()()())",
                                "()(()()())(((()(()))())())",
                                "((((())())())())(()()())()",
                                "(()()())(()(()(()(()))))()",
                                "(()()())()(()(()((())())))",
                                "(()(((())())()))(()()())()",
                                "()((()((())()))())(()()())",
                                "()(()()())(()(((())())()))",
                                "(()()())((()((())()))())()",
                                "((()(()(())))())()(()()())",
                                "()(()()())((((())())())())",
                                "(()(()(()(()))))(()()())()",
                                "(((()(()))())())(()()())()",
                                "()((((())())())())(()()())",
                                "(()()())(()(()((())())))()",
                                "()(()(()(()(()))))(()()())",
                                "(()((()(()))()))()(()()())",
                                "(()()())()(()((()(()))()))",
                                "()((()(()(())))())(()()())",
                                "()(()()())((()(()(())))())",
                                "(()()())()(((()(()))())())"
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("bubbleCases")
    void getBubbleBrackets(String brackets, Set<String> expected) {
        assertThat(bubbleTheBrackets.getBubbleBrackets(brackets))
                .containsOnlyOnceElementsOf(expected);
    }

    @ParameterizedTest
    @MethodSource("bubbleCases")
    void getBubbleBracketsV2(String brackets, Set<String> expected) {
        assertThat(bubbleTheBrackets.getBubbleBracketsV2(brackets))
                .containsOnlyOnceElementsOf(expected);
    }
}