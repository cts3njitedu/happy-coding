import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.stream.IntStream;

public class SumOfBinarySubstrings implements HappyCoding {

    @Override
    public void execute() {
//        IntStream.rangeClosed(23482834, 23482834)
//                .forEach(i -> System.out.printf("%s => %s\n", i, execute(i)));

//        IntStream.rangeClosed(2, 11)
//                .mapToObj(BigInteger::valueOf)
//                .forEach(b -> System.out.printf("%d => %d\n", b, execute(b)));
        System.out.println(execute(new BigInteger("99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999")));
    }

    public int execute(int n) {
        int m = 1;
        int b = 0;
        int s = 0;
        while (n != (m & n)) {
            int i = 0;
            while (m == 1 ? m << i <= n : i < b) {
                s += (m << i & n) >> i++;
                if (m == 1) b++;
            }
            m = m << 1 | 1; b--;
        }
        return s;
    }

    public BigInteger execute(BigInteger n) {
        BigInteger mask = BigInteger.ONE;
        int bits = 0;
        BigInteger sum = BigInteger.ZERO;
        while (!n.equals(mask.and(n))) {
            int i = 0;
            while(BigInteger.ONE.equals(mask) ?  mask.shiftLeft(i).compareTo(n) <= 0 : i < bits) {
                sum = sum.add((mask.shiftLeft(i).and(n)).shiftRight(i));
                i++;
                if (BigInteger.ONE.equals(mask)) bits++;
            }
            mask = mask.shiftLeft(1).or(BigInteger.ONE);
            bits--;
        }
        return sum;
    }
}
