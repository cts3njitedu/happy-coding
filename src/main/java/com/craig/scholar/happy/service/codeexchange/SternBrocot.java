package com.craig.scholar.happy.service.codeexchange;


import com.craig.scholar.happy.model.Fraction;
import java.util.Arrays;

public class SternBrocot implements HappyCodingV2<Void, Void> {

  @Override
  public Void execute(Void unused) {
    sternBrocot(new Fraction[]{new Fraction(0, 1), new Fraction(1, 0)}, 10);
    return null;
  }

  public Fraction r_n(int n) {
    int[] s = new int[n + 2];
    s[1] = 1;
    s[2] = 1;
    for (int i = 3; i <= n + 1; i++) {
      if (i % 2 == 0) {
        s[i] = s[i / 2];
      } else {
        s[i] = s[(i - 1) / 2] + s[(i + 1) / 2];
      }
    }
    return new Fraction(s[n], s[n + 1]);

  }

  private void sternBrocot(Fraction[] fs, int order) {
    if (order == 0) {
      return;
    }
    Arrays.stream(fs)
        .forEach(f -> System.out.printf("%d/%d ", f.n(), f.d()));
    System.out.println();
    Fraction[] nfs = new Fraction[2 * fs.length - 1];
    for (int i = 0; i < nfs.length; i++) {
      if (i % 2 == 0) {
        nfs[i] = fs[i / 2];
      } else {
        nfs[i] = new Fraction(fs[i / 2], fs[(i + 1) / 2]);
      }
    }
    sternBrocot(nfs, order - 1);
  }
}
