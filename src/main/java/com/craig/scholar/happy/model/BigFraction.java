package com.craig.scholar.happy.model;

import java.math.BigInteger;

public record BigFraction(BigInteger n, BigInteger d) {

  @Override
  public String toString() {
    return String.format("%d/%d", n, d);
  }
}
