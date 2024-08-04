package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class BubbleTheBrackets {

    static class Node {
        List<Node> children = new ArrayList<>();
        String openBracket = "";
        String closeBracket = "";
    }

    private static final Map<Character, Character> BRACKETS = Map.of(
            '(', ')',
            '[', ']',
            '{', '}'
    );

    public Set<String> getBubbleBracketsV2(String brackets) {
        if (brackets == null) return Set.of();
        Map<Integer, List<Set<String>>> m = new HashMap<>();
        Stack<Character> s = new Stack<>();
        int c = 0;
        for (int i = 0; i < brackets.length(); i++) {
            char bc = brackets.charAt(i);
            if (BRACKETS.containsKey(bc)) {
                m.computeIfAbsent(++c, k -> new ArrayList<>());
                s.push(bc);
            } else {
                List<Set<String>> cb = m.remove(c + 1);
                char cc = s.pop();
                m.get(c--).add(getBubbles(cb, "")
                        .stream()
                        .map(b -> cc + b + BRACKETS.get(cc))
                        .collect(Collectors.toSet()));
            }
        }
        return getBubbles(m.get(1), "");
    }

    public Set<String> getBubbleBrackets(String brackets) {
        Node node = new Node();
        Stack<Node> s = new Stack<>();
        s.push(node);
        for (int i = 0; i < brackets.length(); i++) {
            if (BRACKETS.containsKey(brackets.charAt(i))) {
                Node child = new Node();
                child.openBracket += brackets.charAt(i);
                child.closeBracket += BRACKETS.get(brackets.charAt(i));
                s.peek().children.add(child);
                s.push(child);
            } else {
                s.pop();
            }
        }
        return new HashSet<>(getBubbleBrackets(node));
    }

    private Set<String> getBubbleBrackets(Node node) {
        if (node.children.isEmpty()) return Set.of(node.openBracket + node.closeBracket);
        List<Set<String>> childBubbles = node.children.stream()
                .map(this::getBubbleBrackets)
                .collect(Collectors.toList());
        return getBubbles(childBubbles, "")
                .stream()
                .map(b -> node.openBracket + b + node.closeBracket)
                .collect(Collectors.toSet());
    }

    private Set<String> getBubbles(List<Set<String>> childBubbles, String s) {
        if (childBubbles == null || childBubbles.isEmpty()) return Set.of(s);
        Set<String> mergedBubbles = new HashSet<>();
        for (int i = 0; i < childBubbles.size(); i++) {
            Set<String> b = childBubbles.get(i);
            for (String string : b) {
                List<Set<String>> temp = new ArrayList<>();
                temp.addAll(childBubbles.subList(0, i));
                temp.addAll(childBubbles.subList(i + 1, childBubbles.size()));
                mergedBubbles.addAll(getBubbles(temp, s + string));
            }
        }
        return mergedBubbles;
    }
}
