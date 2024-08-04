package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
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

    private List<String> getBubbleBrackets(Node node) {
        if (node.children.isEmpty()) return List.of(node.openBracket + node.closeBracket);
        List<List<String>> childBubbles = node.children.stream()
                .map(this::getBubbleBrackets)
                .collect(Collectors.toList());
        return getBubbles(childBubbles, "")
                .stream()
                .map(b -> node.openBracket + b + node.closeBracket)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> getBubbles(List<List<String>> childBubbles, String s) {
        if (childBubbles.isEmpty()) return List.of(s);
        List<String> mergedBubbles = new ArrayList<>();
        for (int i = 0; i < childBubbles.size(); i++) {
            List<String> b = childBubbles.get(i);
            for (String string : b) {
                List<List<String>> temp = new ArrayList<>();
                temp.addAll(childBubbles.subList(0, i));
                temp.addAll(childBubbles.subList(i + 1, childBubbles.size()));
                mergedBubbles.addAll(getBubbles(temp, s + string));
            }
        }
        return mergedBubbles;
    }
}
