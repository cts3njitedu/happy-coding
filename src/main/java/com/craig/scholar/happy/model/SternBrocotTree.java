package com.craig.scholar.happy.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SternBrocotTree<F> {

  private F leftFraction;
  private final F fraction;
  private F rightFraction;
  private SternBrocotTree<F> left;
  private SternBrocotTree<F> right;
  private final int level;
  private final int position;

  public SternBrocotTree(F leftFraction, F fraction, F rightFraction, int level, int position) {
    this.leftFraction = leftFraction;
    this.fraction = fraction;
    this.rightFraction = rightFraction;
    this.level = level;
    this.position = position;
  }

}
