package com.craig.happy.coding;

public class NbonacciSequence implements HappyCoding {

    @Override
    public void execute() {
        execute(3, 8);
    }

    private void execute(int n, int x) {
        int[] f = new int[n];
        for (int i = 1; i <= x; i++) {
            if (i <= n) f[n-i] = 1;
            for (int j = 0; i > n && j < n-1; j++) {
                f[n-1]  += f[j];
                f[j] = f[j+1];
            }
            System.out.print(f[n-1] + ",");
        }
    }
}
