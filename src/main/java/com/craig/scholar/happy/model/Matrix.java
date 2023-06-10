package com.craig.scholar.happy.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Matrix(int[] rows, int columns) {

  public static final Integer ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT = 0;
  public static final Integer ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT = 1;
  public static final Integer NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT = 2;
  public static final Integer NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT = 3;

  public static final Integer ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT = 4;
  public static final Integer ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT = 5;
  public static final Integer TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT = 6;
  public static final Integer TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT = 7;

  public List<Matrix> getTransformations() {
    Map<Integer, int[]> matrices = new HashMap<>(8);
    matrices.put(ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT, rows);
    matrices.put(ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT, new int[rows.length]);
    matrices.put(NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT, new int[columns]);
    matrices.put(NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT, new int[columns]);
    matrices.put(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT, new int[rows.length]);
    matrices.put(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT, new int[rows.length]);
    matrices.put(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT, new int[columns]);
    matrices.put(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT, new int[columns]);
    for (int i = 0; i < rows.length; i++) {
      for (int j = columns - 1; j >= 0; j--) {
        int b = (rows[i] & (1 << j)) != 0 ? 1 : 0;
        matrices.get(ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT)[i] |= (b << (columns - 1
            - j));
        matrices.get(NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT)[columns - 1 - j] |= (b << i);
        matrices.get(NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT)[j] |= (b << i);
        matrices.get(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT)[rows.length - 1 - i]
            |= (b << (columns - 1 - j));
        matrices.get(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT)[rows.length - 1 - i]
            |= (b << j);
        matrices.get(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT)[j] |= (b << (rows.length - 1
            - i));
        matrices.get(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT)[columns - 1 - j] |= (b << (
            rows.length - 1 - i));
      }
    }
    return matrices.values().stream()
        .map(m -> new Matrix(m, m.length == rows.length ? columns : rows.length))
        .collect(Collectors.toList());
  }
}
