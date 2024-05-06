package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.either.HappyPair;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IsACartesianProduct {

  public boolean execute(List<HappyPair<Integer, Integer>> products) {
    Set<Integer> aSet = products.stream()
        .map(HappyPair::a)
        .collect(Collectors.toSet());
    return products.stream()
        .collect(Collectors.groupingBy(HappyPair::b,
            Collectors.groupingBy(HappyPair::a, Collectors.counting())))
        .values()
        .stream()
        .map(freqMap -> {
          long min = freqMap
              .values()
              .stream()
              .mapToLong(i -> i)
              .min()
              .orElse(0L);
          return aSet.stream()
              .map(ai -> {
                if (min == 0) {
                  return 0;
                }
                long av = freqMap.getOrDefault(ai, 0L);
                return av % min == 0 ? av / min : 0;
              })
              .map(String::valueOf)
              .collect(Collectors.joining(","));
        })
        .distinct()
        .count()
        == 1L;
  }

}
