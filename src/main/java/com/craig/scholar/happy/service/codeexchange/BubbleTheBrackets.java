package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class BubbleTheBrackets {

    static class Node {
        List<Node> children = new ArrayList<>();
        boolean isRoot;
    }

    public Set<String> getBracketTree(String brackets) {
        Node node = new Node();
        node.isRoot = true;
        Stack<Node> s = new Stack<>();
        s.push(node);
        for (int i = 0; i < brackets.length(); i++) {
            if (brackets.charAt(i) == '(') {
                Node child = new Node();
                s.peek().children.add(child);
                s.push(child);
            } else {
                s.pop();
            }
        }
        return new HashSet<>(printBubbleBracket(node));
    }

    private List<String> printBubbleBracket(Node node) {
        if (node.children.isEmpty()) return List.of("()");
        List<List<String>> childBubbles = node.children.stream()
                .map(this::printBubbleBracket)
                .collect(Collectors.toList());
        return getBubbles(childBubbles, "")
                .stream()
                .map(b -> node.isRoot ? b : "(" + b + ")")
                .collect(Collectors.toList());
    }

    private List<String> getBubbles(List<List<String>> childBubbles, String s) {
        if (childBubbles.isEmpty()) return List.of(s);
        List<String> bb = new ArrayList<>();
        for (int i = 0; i < childBubbles.size(); i++) {
            List<String> b = childBubbles.get(i);
            for (String string : b) {
                List<List<String>> t = new ArrayList<>();
                t.addAll(childBubbles.subList(0, i));
                t.addAll(childBubbles.subList(i + 1, childBubbles.size()));
                bb.addAll(getBubbles(t, s + string));
            }
        }
        return bb;
    }
}
