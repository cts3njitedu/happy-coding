package com.craig.scholar.happy.model;

public record Fraction(int n, int d) {

  @Override
  public String toString() {
    return String.format("%s/%s", n, d);
  }
}
