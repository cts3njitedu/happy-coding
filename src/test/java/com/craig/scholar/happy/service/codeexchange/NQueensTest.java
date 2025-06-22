package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NQueensTest {

  NQueens nQueens = new NQueens();

  @ParameterizedTest
  @CsvSource({
      "1, 1",
      "2, 0",
      "3, 0",
      "4, 2",
      "5, 10",
      "6, 4",
      "7, 40",
      "8, 92",
      "9, 352",
      "10, 724"
  })
  void execute_params(int n, int size) {
    assertThat(nQueens.execute(n))
        .hasSize(size);
    assertThat(nQueens.executeBin(n))
        .hasSize(size);
  }

  @Test
  void execute() {
    nQueens.execute(8)
        .forEach(board -> {
          board
              .forEach(System.out::println);
          System.out.println("*".repeat(25));
        });
  }

  @Test
  void execute_size() {
    System.out.println(nQueens.execute(12).size());
  }

  @Test
  void execute_bin_size() {
    System.out.println(nQueens.executeBin(15).size());
  }

  @Test
  void execute_bin() {
    nQueens.executeBin(9)
        .forEach(board -> {
          board
              .forEach(System.out::println);
          System.out.println("*".repeat(25));
        });
  }
}