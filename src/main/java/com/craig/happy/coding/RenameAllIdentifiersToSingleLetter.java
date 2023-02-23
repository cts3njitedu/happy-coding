package com.craig.happy.coding;

import java.util.*;
import java.util.stream.Collectors;

public class RenameAllIdentifiersToSingleLetter implements HappyCoding {
    @Override
    public void execute() {
//       (((((do) re) mi) fa) so)
//        System.out.println(rename("(chicken (fun you (are chicken)) you)"));
        List<Object> l = List.of(List.of(List.of(List.of(List.of("do"), "re"), "me"),"fa"), "so");
        System.out.println(rename(l));
    }

    private StringBuilder rename(String s) {
        Queue<Character> l = s
                .chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new));
        return rename(l, new HashMap<>());
    }

    private List<Object> rename(List<Object> l) {
        return rename(l, new HashMap<>());
    }

    private List<Object> rename(List<Object> l, Map<String, Character> m) {
        Queue<String> q = new LinkedList<>();
        List<Object> nl =  l.stream()
                .map(o -> {
                    if (o instanceof List) return rename((List<Object>) o, m);
                    String s = (String) o;
                    return m.compute(s, (k, v) -> {
                        if (v != null) return v;
                        q.add(s);
                        return (char) ('a' + m.size());
                    });

                })
                .collect(Collectors.toList());
        while (!q.isEmpty()) {
            m.remove(q.poll());
        }
        return nl;
    }

    private StringBuilder rename(Queue<Character> l, Map<String, Character> m) {
        Queue<String> q = new LinkedList<>();
        StringBuilder b = new StringBuilder();
        StringBuilder w = new StringBuilder();
        while (!l.isEmpty()) {
            char c = l.poll();
            if (Character.isLetterOrDigit(c)) {
                w.append(c);
            } else if (Character.isWhitespace(c) || c == ')') {
                if (w.length() > 0) {
                    String ws = w.toString();
                    b.append(m.compute(w.toString(), (k, v) -> {
                        if (v != null) return v;
                        q.add(ws);
                        return (char) ('a' + m.size());
                    }));
                    w = new StringBuilder();
                }
                b.append(c);
                if (c == ')') {
                    while (!q.isEmpty()) {
                        m.remove(q.poll());
                    }
                    return b;
                }
            } else {
                b.append(c);
                b.append(rename(l, m));
            }
        }
        return b;
    }
}
