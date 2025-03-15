package com.craig.scholar.happy.service.codeexchange;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class WordBreak {

  public List<List<String>> getCombinations(String word, List<String> dictionary) {
    return getCombinations(word, 0, dictionary, new HashSet<>(), new Stack<>());
  }

  private List<List<String>> getCombinations(String str, int i, List<String> dictionary,
      Set<Integer> visitedIndices, Stack<String> combination) {
    if (i > str.length()) {
      return List.of();
    }
    if (i == str.length()) {
      return List.of(new ArrayList<>(combination));
    }
    var combinations = new ArrayList<List<String>>();
    var matchedWords = new HashSet<>();
    for (int d = 0; d < dictionary.size(); d++) {
      var word = dictionary.get(d);
      if (!matchedWords.contains(word)
          && !visitedIndices.contains(d)
          && str.startsWith(word, i)) {
        matchedWords.add(word);
        visitedIndices.add(d);
        combination.push(word);
        combinations.addAll(
            getCombinations(str, i + word.length(), dictionary, visitedIndices, combination));
        visitedIndices.remove(d);
        combination.pop();
      }
    }
    return combinations;
  }

}
