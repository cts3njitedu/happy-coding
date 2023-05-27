package com.craig.scholar.happy.service.codeexchange;

import java.util.*;
import java.util.stream.Collectors;

public class BuildListFromDepthMap implements HappyCoding {
    @Override
    public void execute() {
//
//        String s = "jkjfiejrdfncuhfhdkakwieeggjfjdee";
//        LinkedList<String> c = new LinkedList<>(Arrays.asList(s.split("")));
//        System.out.println(depthC(c, 1));
        int[] n = {1000,1000,1000};
        System.out.println(depth(n, 1, new int[]{0}));
        LinkedList<Integer> q = Arrays.stream(n)
                .boxed().collect(Collectors.toCollection(LinkedList::new));
        System.out.println(depth(q));

    }

    private List<Object> depth(LinkedList<Integer> q) {
        return depth(q, 1);
    }

    private List<Object> depth(Queue<Integer> q, int d) {
        List<Object> l = new ArrayList<>();
        while (!q.isEmpty()) {
            if (q.peek() < d) return l;
            l.add(q.peek() == d ? q.poll() : depth(q, d + 1));
        }
        return l;
    }

    private List<Object> depthC(LinkedList<String> q, int d) {
        List<Object> l = new ArrayList<>();
        while (!q.isEmpty()) {
            int c = q.peek().charAt(0) - 'a' + 1;
            if (c < d) return l;
            l.add(c == d ? q.poll() : depthC(q, d + 1));
        }
        return l;
    }

    private List<Object> depth(int[] n, int d, int[] p) {
        List<Object> l = new ArrayList<>();
        while (n.length != p[0]) {
            if (n[p[0]] < d) return l;
            l.add(n[p[0]] == d ? n[p[0]++] : depth(n, d + 1, p));
        }
        return l;
    }
}
