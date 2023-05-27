package com.craig.scholar.happy.service.codeexchange;

import java.math.BigInteger;
import java.util.Objects;

public class FibonacciPolynomials implements HappyCoding {

    @Override
    public void execute() {
//        execute(15);
        execute2(1000);
    }

    private void execute2(int n) {
        BigInteger[][] p = new BigInteger[n + 1][n + 1];
        p[0][0] = BigInteger.ZERO;
        p[1][0] = BigInteger.ONE;
        for (int i = 0; i <= n; i++) {
            for (int j = i - 2; i > 1 && j < i; j++) {
                for (int k = 0; k < n; k++) {
                    p[i][2 - i + j + k] = (Objects.requireNonNullElse(p[i][2 - i + j + k],
                            BigInteger.ZERO)).add(Objects.requireNonNullElse(p[j][k], BigInteger.ZERO));
                }
            }
            if (i == n)
            printPolynomialCoefficient(p, i);
        }

    }

    private void execute(int n) {
        int[][] p = new int[n + 1][n + 1];
        p[1][0] = 1;
        for (int i = 0; i <= n; i++) {
            for (int j = i - 2; i > 1 && j < i; j++) {
                for (int k = 0; k < n; k++) {
                    p[i][2 - i + j + k] += p[j][k];
                }
            }
            printPolynomialCoefficient(p, i);
        }
    }

    private void printPolynomialCoefficient(int[][] p, int i) {
        System.out.print(i + " -> [");
        for (int j = i < 2 ? 0 : i - 1; j >= 0; j--) {
            System.out.print(p[i][j] + (j != 0 ? "," : "]\n"));
        }
    }

    private void printPolynomialCoefficient(BigInteger[][] p, int i) {
        System.out.print(i + " -> [");
        for (int j = i < 2 ? 0 : i - 1; j >= 0; j--) {
            System.out.print(p[i][j] + (j != 0 ? "," : "]\n"));
        }
    }

//    private void printPolynomial(int[][] p, int i) {
//        String f = String.format("F_%d(x) = ", i);
//        for (int j = i < 2 ? 0 : i-1; j>=0; j--) {
//            String c = i<2 || p[i][j] > 0 && j==0 || p[i][j] >
//        }
//    }
}
