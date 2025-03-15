package com.craig.scholar.happy.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SternBrocotTree<F, N> {

  private F leftFraction;
  private final F fraction;
  private F rightFraction;
  private SternBrocotTree<F, N> left;
  private SternBrocotTree<F, N> right;
  private final N level;
  private final N position;

  public SternBrocotTree(F leftFraction, F fraction, F rightFraction, N level, N position) {
    this.leftFraction = leftFraction;
    this.fraction = fraction;
    this.rightFraction = rightFraction;
    this.level = level;
    this.position = position;
  }

}
