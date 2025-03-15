package com.craig.scholar.happy.model;

public record Fraction(int n, int d) {

  @Override
  public String toString() {
    return String.format("%s/%s", n, d);
  }

  public boolean isLarger(Fraction fraction) {
    return n * fraction.d > fraction.n * d;
  }

  public boolean isSmaller(Fraction fraction) {
    return n * fraction.d < fraction.n * d;
  }

  public boolean isSame(Fraction fraction) {
    return n * fraction.d == fraction.n * d;
  }
}
