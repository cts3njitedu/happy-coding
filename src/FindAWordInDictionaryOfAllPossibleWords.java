import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class FindAWordInDictionaryOfAllPossibleWords implements HappyCoding {
    @Override
    public void execute() {
//        String s = "41, 57, 60, 61, 71, 80, 113, 125, 131, 139, 141, 184, 197, 200, 201, 214, 215, 216, 223, 236, 238, 240, 244, 252, 264, 279, 300, 335, 340, 393, 410, 414, 421, 436, 441, 447, 461, 466, 483, 490, 525, 537, 540, 543, 547, 551, 552, 557, 569, 583, 584, 591, 593, 595, 596, 607, 610, 613, 614, 620, 621, 634, 637, 643, 652, 683, 691, 713, 726, 733, 738, 750, 757, 767, 777, 789, 803, 812, 813, 817, 844, 850, 856, 878, 901, 910, 926, 947, 949, 951, 953, 958, 962, 969, 982, 995}, [252, 300, 969, 844, 856, 713, 60, 621, 393, 637, 634, 441, 817, 264, 551, 757, 926, 240, 461, 421, 767, 726, 223, 610, 547, 141, 593, 184, 200, 643, 583, 614, 958, 540, 201, 214, 584, 591, 525, 652, 466, 414, 995, 125, 813, 951, 901, 215, 947, 410, 113, 279, 238, 57, 750, 607, 61, 131, 216, 340, 569, 803, 557, 878, 691, 80, 850, 483, 71, 613, 41, 244, 789, 595, 447, 596, 812, 543, 953, 620, 962, 436, 537, 733, 738, 197, 949, 982, 139, 683, 910, 236, 552, 490, 777, 335";
//        LinkedList<Integer> l = Arrays.stream(s.split(","))
//                .map(String::trim)
//                .map(Integer::parseInt)
//                .sorted()
//                .collect(Collectors.toCollection(LinkedList::new));
//        LinkedList<Integer> l = new LinkedList<>(List.of(41, 57, 60, 61, 71, 80, 113, 125, 131, 139, 141, 184, 197, 200, 201, 214, 215, 216, 223, 236, 238, 240, 244, 252, 264, 279, 300, 335, 340, 393, 410, 414, 421, 436, 441, 447, 461, 466, 483, 490, 525, 537, 540, 543, 547, 551, 552, 557, 569, 583, 584, 591, 593, 595, 596, 607, 610, 613, 614, 620, 621, 634, 637, 643, 652, 683, 691, 713, 726, 733, 738, 750, 757, 767, 777, 789, 803, 812, 813, 817, 844, 850, 856, 878, 901, 910, 926, 947, 949, 951, 953, 958, 962, 969, 982, 995));
        //int[] w = new int[]{252, 300, 969, 844, 856, 713, 60, 621, 393, 637, 634, 441, 817, 264, 551, 757, 926, 240, 461, 421, 767, 726, 223, 610, 547, 141, 593, 184, 200, 643, 583, 614, 958, 540, 201, 214, 584, 591, 525, 652, 466, 414, 995, 125, 813, 951, 901, 215, 947, 410, 113, 279, 238, 57, 750, 607, 61, 131, 216, 340, 569, 803, 557, 878, 691, 80, 850, 483, 71, 613, 41, 244, 789, 595, 447, 596, 812, 543, 953, 620, 962, 436, 537, 733, 738, 197, 949, 982, 139, 683, 910, 236, 552, 490, 777, 335};
        LinkedList<Integer> l = new LinkedList<>(List.of(1,2,3));
        System.out.println(findWord(l, new int[]{1,4,3}));
//        totalNumberOfPermutations(200);
    }

    private BigInteger findWord(LinkedList<Integer> l, int[] w) {
        BigInteger o = BigInteger.ONE;
        BigInteger z = BigInteger.ZERO;
        BigInteger[] s = new BigInteger[l.size()];
        s[0] = o;
        for (int i = 1; i<s.length; i++) {
            s[i] = BigInteger.valueOf(i).multiply(s[i-1]).add(o);
        }
        BigInteger p = z;
        for (int k : w) {
            p = p.add(o);
            BigInteger j = z;
            int n = l.size();
            Iterator<Integer> itr = l.iterator();
            while (itr.hasNext()) {
                j = j.add(o);
                if (itr.next() == k) {
                    itr.remove();
                    break;
                }
            }
            if (l.size() == n) {
                throw new IllegalArgumentException(String.format("Invalid character %s", k));
            }
            p = p.add(j.subtract(o).multiply(s[l.size()]));
        }
        return p;
    }

    private void totalNumberOfPermutations(int n) {
        BigInteger o = BigInteger.ONE;
        BigInteger s = BigInteger.ZERO;
        for (int i = 0; i<=n; i++) {
            s = BigInteger.valueOf(i).multiply(s).add(o);
            System.out.printf("n: %d, total permutations: %d\n", i, s);
        }
    }
}
