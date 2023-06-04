package com.craig.scholar.happy.trie;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BinaryTrieTest {

  BinaryTrie binaryTrie = new BinaryTrie();

  @Test
  void testAddMatrix() {
    List<boolean[][]> matrices = new ArrayList<>();
    matrices.add(new boolean[][]{
        {true, false, false, false},
        {true, true, true, true},
        {true, false, false, true}
    });
    matrices.add(new boolean[][]{
        {true, false, true, false},
        {true, true, true, true},
        {false, false, false, true}
    });
    matrices.forEach(binaryTrie::add);
    assertTrue(matrices.stream().allMatch(binaryTrie::find));
  }

}
