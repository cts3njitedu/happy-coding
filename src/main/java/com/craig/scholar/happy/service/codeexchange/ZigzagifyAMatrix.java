package com.craig.scholar.happy.service.codeexchange;

import java.util.Arrays;

public class ZigzagifyAMatrix implements HappyCoding {
    @Override
    public void execute() {
//        int[][] m = {{1,2,3,4,5}, {6,7,8,9,10},{11,12,13,14,15}, {16,17,18,19,20},{21,22,23,24,25}};
        int[][] m = {{1}};
        int[] n = execute(m);
        Arrays.stream(n).forEach(i -> System.out.print(i + " "));
    }

    public int[] execute(int[][] m) {
        int h = m.length;
        int w = m[0].length;
        int[] z = new int[w * h];
        int k = 0;
        boolean u = true;
        int i = 0;
        int j = 0;
        z[k++] = m[i][j];
        while (k < z.length) {
            int p = (u && j+1>=w) || (!u && i+1<h) ? i+1: i;
            int l = (u && j+1<w) || (!u && i+1>=h) ? j+1 : j;
            i = p;
            j = l;
            u = !u;
            while(i>=0 && i<h && j>=0 && j<w) {
                z[k++] = m[i][j];
                i = u ? i-1 : i+1;
                j = u ? j+1 : j-1;
            }
            i = u ? i+1 : i-1;
            j = u ? j-1 : j+1;
        }
        return z;
    }
}
