package com.craig.scholar.happy.service.codeexchange;

import org.junit.jupiter.api.Test;

import java.util.Set;

class BubbleTheBracketsTest {

    private final BubbleTheBrackets bubbleTheBrackets = new BubbleTheBrackets();

    @Test
    void getBracketTree() {
        Set<String> bracketTree = bubbleTheBrackets.getBracketTree("()()");
        System.out.println(bracketTree);

    }
}