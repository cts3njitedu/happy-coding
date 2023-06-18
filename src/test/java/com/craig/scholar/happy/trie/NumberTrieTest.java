package com.craig.scholar.happy.trie;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class NumberTrieTest {

  @Test
  void testAddWord() {
    NumberTrie numberTrie = new NumberTrie();
    List<int[]> numbers = List.of(
        new int[]{4},
        new int[]{9},
        new int[]{4, 2},
        new int[]{4, 2, 6},
        new int[]{4, 2, 6, 7},
        new int[]{1, 8, 5, 7},
        new int[]{4, 3, 6, 6},
        new int[]{7, 8, 3, 7},
        new int[]{10, 2, 16, 9}
    );
    numbers.forEach(numberTrie::addWord);
    boolean isMatched = numbers.stream()
        .allMatch(numberTrie::findWord);
    assertThat(isMatched).isTrue();
    System.out.println(numberTrie.getAllWords());
    assertThat(numberTrie.findWord(new int[]{9, 2})).isFalse();
  }
}
