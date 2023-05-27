package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.either.ListEither2;
import com.craig.scholar.happy.util.ListUtil2;

import java.util.stream.IntStream;

public class RaggedListPatternMatching {

  public boolean isMatch(Object pattern, Object raggedList) {
    return execute(ListUtil2.convert(pattern, Integer.class),
        ListUtil2.convert(raggedList, Integer.class));
  }

  private boolean execute(ListEither2<Integer> pattern, ListEither2<Integer> raggedList) {
    if (pattern.isEmpty() || raggedList.isEmpty()) {
      return pattern.isEmpty() && raggedList.isEmpty();
    }
    if (pattern.isItem() && raggedList.isItem()) {
      return pattern.getItem().equals(0)
          || pattern.getItem().equals(raggedList.getItem());
    }
    if (pattern.isItem() || raggedList.isItem()) {
      return raggedList.isList() && pattern.getItem().equals(0);
    }
    if (pattern.getList().size() != raggedList.getList().size()) {
      return false;
    }
    return IntStream.range(0, pattern.getList().size())
        .allMatch(i -> execute(pattern.getList().get(i), raggedList.getList().get(i)));
  }
}
