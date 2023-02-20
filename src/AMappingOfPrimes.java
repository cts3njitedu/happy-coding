import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AMappingOfPrimes implements HappyCoding {
    @Override
    public void execute() {
        int n = 214748391;
//        printPrimes(100);
        Instant start = Instant.now();
        String m = mappingOfPrimes(n);
        System.out.println(m);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.printf("Time taken: %s ms.\n", timeElapsed);
        System.out.println(printPrimeFactors(n));
//        String e = "";
//        System.out.println(m.equals(e));
//        System.out.println("{{{}},{},{},{{}},{{{}}},{},{}}");
//        System.out.println(primeFactors(22308));
//        System.out.println(mappingOfPrimes(22308));
    }

    public String mappingOfPrimes(int n) {
        return mappingOfPrimes(n, primes(n), new HashMap<>());
    }

    public String mappingOfPrimes(int n, boolean[] p, Map<Integer, String> m) {
        if (m.containsKey(n)) return m.get(n);
        TreeMap<Integer, Integer> t = primeFactors(n, p);
        StringBuilder b = new StringBuilder("{");
        if (!t.isEmpty()) {
            int l = t.lastKey();
            List<String> c = IntStream.range(2, p.length)
                    .filter(i -> i+1 <= l && !p[i])
                    .mapToObj(i -> mappingOfPrimes(t.getOrDefault(i + 1, 0) + 1, p, m))
                    .collect(Collectors.toList());
            int a = t.getOrDefault(2, 0);
            for (int i = 1; i <= a; i++) {
                c.add("{}");
            }
            b.append(String.join(",", c));
        }
        b.append("}");
        m.put(n, b.toString());
        return m.get(n);
    }


    public TreeMap<Integer, Integer> primeFactors(int n, boolean[] p) {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        if (n > 1) {
            for (int i = 1; (i + 1) * (i + 1) <= n; i++) {
                if (!p[i] && n % (i + 1) == 0) {
                    m.put(i + 1, 1);
                    TreeMap<Integer, Integer> t = primeFactors(n / (i + 1), p);
                    t.forEach((k, v) -> m.merge(k, v, Integer::sum));
                    return m;
                }
            }
            m.put(n, 1);
        }
        return m;
    }

    public TreeMap<Integer, Integer> primeFactors(int n) {
        TreeMap<Integer, Integer> m = new TreeMap<>();
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                m.put(i, 1);
                Map<Integer, Integer> t = primeFactors(n / i);
                t.forEach((k, v) -> m.merge(k, v, Integer::sum));
                return m;
            }
        }
        if (n > 1) {
            m.put(n, 1);
        }
        return m;
    }

    public void printPrimes(int n) {
        boolean[] p = primes(n);
        for (int i = 0; i < p.length; i++) {
            if (!p[i]) {
                System.out.println(i + 1);
            }
        }
    }

    public boolean[] primes(int n) {
        Instant s = Instant.now();
        boolean[] sieves = new boolean[n];
        int i = 1;
        while (i < n) {
            while (i < n) {
                if (!sieves[i++]) break;
            }
            for (int j = 2; j * i <= n; j++) {
                sieves[j * i - 1] = true;
            }
        }
        Instant f = Instant.now();
        System.out.printf("Time taken to get primes: %s ms.\n", Duration.between(s, f).toMillis());
        return sieves;
    }

    private String printPrimeFactors(BigInteger n) {
        TreeMap<BigInteger, BigInteger> m = primeFactors(n);
        return m.entrySet().stream()
                .map(e -> e.getKey() + "^" + e.getValue())
                .collect(Collectors.joining("*"));
    }

    private String printPrimeFactors(int n) {
        TreeMap<Integer, Integer> m = new TreeMap<>(primeFactors(n));
        return m.entrySet().stream()
                .map(e -> e.getKey() + "^" + e.getValue())
                .collect(Collectors.joining("*"));
    }

    private TreeMap<BigInteger, BigInteger> primeFactors(BigInteger n) {
        TreeMap<BigInteger, BigInteger> m = new TreeMap<>();
        if (n.compareTo(BigInteger.ONE) > 0) {
            for (BigInteger i = BigInteger.TWO; i.multiply(i).compareTo(n) <= 0; i = i.add(BigInteger.ONE)) {
                if (n.mod(i).equals(BigInteger.ZERO)) {
                    m.put(i, BigInteger.ONE);
                    primeFactors(n.divide(i))
                            .forEach((k, v) -> m.merge(k, v, BigInteger::add));
                    return m;
                }
            }
            m.put(n, BigInteger.ONE);
        }
        return m;
    }

}
