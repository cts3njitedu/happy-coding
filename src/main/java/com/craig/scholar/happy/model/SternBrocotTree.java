package com.craig.scholar.happy.model;


import java.util.LinkedList;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SternBrocotTree<F, N> {

  public enum Direction {
    START,
    LEFT,
    RIGHT
  }

  public record PreviousFraction<F>(F fraction, Direction direction) {

  }

  private F leftFraction;
  private final F fraction;
  private F rightFraction;
  private SternBrocotTree<F, N> left;
  private SternBrocotTree<F, N> right;
  private final N level;
  private final N position;
  private LinkedList<PreviousFraction<F>> previousFractions;

  public SternBrocotTree(F leftFraction, F fraction, F rightFraction, N level, N position) {
    this.leftFraction = leftFraction;
    this.fraction = fraction;
    this.rightFraction = rightFraction;
    this.level = level;
    this.position = position;
  }

}
