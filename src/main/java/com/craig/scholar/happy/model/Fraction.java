package com.craig.scholar.happy.model;

public record Fraction(int n, int d) {

  public Fraction(Fraction a, Fraction b) {
    this(a.n + b.n, a.d + b.d);
  }

  @Override
  public String toString() {
    return String.format("%d/%d", n, d);
  }
}
