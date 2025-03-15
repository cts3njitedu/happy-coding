package com.craig.scholar.happy.model;

import java.math.BigInteger;

public record BigFraction(BigInteger n, BigInteger d) {

  public BigFraction(String n, String d) {
    this(new BigInteger(n), new BigInteger(d));
  }

  @Override
  public String toString() {
    return String.format("%d/%d", n, d);
  }

  public boolean isLarger(BigFraction fraction) {
    return (n.multiply(fraction.d)).compareTo(fraction.n.multiply(d)) > 0;
  }

  public boolean isSmaller(BigFraction fraction) {
    return (n.multiply(fraction.d)).compareTo(fraction.n.multiply(d)) < 0;
  }

  public boolean isSame(BigFraction fraction) {
    return (n.multiply(fraction.d)).compareTo(fraction.n.multiply(d)) == 0;
  }
}
