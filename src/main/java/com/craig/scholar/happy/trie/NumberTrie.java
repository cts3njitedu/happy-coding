package com.craig.scholar.happy.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NumberTrie {

  private boolean isWord;
  private final Map<Integer, NumberTrie> children = new HashMap<>();

  public void addWord(int[] numbers) {
    NumberTrie numberTrie = this;
    for (int number : numbers) {
      numberTrie = numberTrie.children.computeIfAbsent(number,
          k -> new NumberTrie());
    }
    numberTrie.isWord = true;
  }

  public boolean findWord(int[] numbers) {
    NumberTrie numberTrie = this;
    for (int number : numbers) {
      numberTrie = numberTrie.children.get(number);
      if (Objects.isNull(numberTrie)) {
        return false;
      }
    }
    return numberTrie.isWord;
  }

  public List<List<Integer>> getAllWords() {
    return getAllWords(this, new LinkedList<>());
  }

  private List<List<Integer>> getAllWords(NumberTrie numberTrie, LinkedList<Integer> l) {
    if (numberTrie == null) {
      return new ArrayList<>();
    }
    List<List<Integer>> wl = new ArrayList<>();
    if (numberTrie.isWord) {
      wl.add(new ArrayList<>(l));
    }
    for (Map.Entry<Integer, NumberTrie> entry : numberTrie.children.entrySet()) {
      l.add(entry.getKey());
      wl.addAll(getAllWords(entry.getValue(), l));
      l.pop();
    }
    return wl;
  }
}
