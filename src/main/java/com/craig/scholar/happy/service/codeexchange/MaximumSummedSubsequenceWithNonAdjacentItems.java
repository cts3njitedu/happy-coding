package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.HappyArray;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MaximumSummedSubsequenceWithNonAdjacentItems implements
    HappyCodingV2<int[], List<List<List<Integer>>>> {

  @Override
  public List<List<List<Integer>>> execute(int[] arr) {
    Map<Integer, MaxSubsequence> mem = new HashMap<>();
    for (int i = 0; i<arr.length; i++) {
      execute(arr, i, mem);
    }
    int max = mem.values()
        .stream()
        .map(MaxSubsequence::sum)
        .max(Comparator.comparingInt(i -> i))
        .orElse(Integer.MIN_VALUE);
    return mem.values()
        .stream()
        .filter(m -> m.sum == max)
        .map(MaxSubsequence::sequences)
        .reduce(new ArrayList<>(), (total, l) -> {
          total.addAll(l);
          return total;
        });
  }

  private record MaxSubsequence(int sum, List<List<List<Integer>>> sequences) {

  }

  private MaxSubsequence execute(int[] arr, int i, Map<Integer, MaxSubsequence> mem) {
    if (mem.get(i) != null) {
      return mem.get(i);
    }
    List<MaxSubsequence> l = new ArrayList<>();
    l.add(new MaxSubsequence(arr[i], List.of(List.of(List.of(i, arr[i])))));
    for (int j = i + 2; j < arr.length; j++) {
      MaxSubsequence t = execute(arr, j, mem);
      l.add(new MaxSubsequence(t.sum + arr[i], t.sequences.stream()
          .map(a -> {
            List<List<Integer>> tt = new ArrayList<>();
            tt.add(List.of(i, arr[i]));
            tt.addAll(new ArrayList<>(a));
            return tt;
          })
          .collect(Collectors.toList())));
    }
    MaxSubsequence m = l.stream()
        .max(Comparator.comparing(MaxSubsequence::sum))
        .stream()
        .findFirst()
        .map(max -> l.stream()
            .filter(maxSubsequence -> maxSubsequence.sum == max.sum)
            .reduce(new MaxSubsequence(max.sum, new ArrayList<>()), (all, maxSubsequence) -> {
              all.sequences.addAll(maxSubsequence.sequences);
              return all;
            }))
        .orElse(null);
    mem.put(i, m);
    return m;
  }
}
