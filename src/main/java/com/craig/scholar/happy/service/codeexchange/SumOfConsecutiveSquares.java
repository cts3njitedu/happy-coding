package com.craig.scholar.happy.service.codeexchange;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class SumOfConsecutiveSquares implements HappyCoding {
    @Override
    public void execute() {
        long s = System.nanoTime();
//        execute(10000000, 2, true);
        execute(new BigInteger("1000"), 2, true);
        long e = System.nanoTime();
        System.out.printf("Time taken: %s", (e - s) * Math.pow(10, -9));
    }

    private void execute(int n) {
        Map<Integer, Integer> m = new HashMap<>();
        int p = 0;
        int j = 0;
        for (int i = 1; i <= n; i++) {
            int k = j;
            while (k * k < i) {
                m.put(p + k * k, k);
                p += k * k;
                j = ++k;
            }
            print(m, i);
        }
    }

    private void execute(int n, int e) {
        execute(n, e, true);
    }

    private void execute(int n, int e, boolean d) {
        Map<Integer, Integer> m = new HashMap<>();
        int p = 0;
        int j = 0;
        for (int i = 1; i <= n; i++) {
            int k = j;
            while (Math.pow(k, e) < i) {
                int ke = (int) Math.pow(k, e);
                m.put(p + ke, k);
                p += ke;
                j = ++k;
            }
            if (d || i == n) {
                print(m, i, e);
            }
        }
    }

    private void execute(BigInteger n, int e, boolean d) {
        Map<BigInteger, BigInteger> m = new HashMap<>();
        BigInteger p = BigInteger.ZERO;
        BigInteger j = BigInteger.ZERO;
        for (BigInteger i = BigInteger.ONE; i.compareTo(n) <= 0; i = i.add(BigInteger.ONE)) {
            BigInteger k = j;
            while (k.pow(e).compareTo(i) < 0) {
                BigInteger ke = k.pow(e);
                m.put(p.add(ke), k);
                p = p.add(ke);
                k = k.add(BigInteger.ONE);
                j = k;
            }
            if (d || i.equals(n)) {
                print(m, i, e);
            }
        }
    }

    private void print(Map<Integer, Integer> m, int n) {
        print(m, n, 2);
    }

    private void print(Map<Integer, Integer> m, int n, int e) {
        m.keySet().stream()
                .filter(i -> i >= n)
                .filter(i -> m.containsKey(i - n))
                .forEach(i -> {
                    System.out.print(n + " = ");
                    for (int k = m.get(i - n) + 1; k <= m.get(i); k++) {
                        System.out.print((int) Math.pow(k, e) + (k == m.get(i) ? "" : " + "));
                    }
                    System.out.println();
                });
    }

    private void print(Map<BigInteger, BigInteger> m, BigInteger n, int e) {
        m.keySet().stream()
                .filter(i -> i.compareTo(n) >= 0)
                .filter(i -> m.containsKey(i.subtract(n)))
                .forEach(i -> {
                    System.out.print(n + " = ");
                    for (BigInteger k = m.get(i.subtract(n)).add(BigInteger.ONE);
                         k.compareTo(m.get(i)) <= 0; k = k.add(BigInteger.ONE)) {
                        System.out.print(k.pow(e) + (k.equals(m.get(i)) ? "" : " + "));
                    }
                    System.out.println();
                });
    }
}
