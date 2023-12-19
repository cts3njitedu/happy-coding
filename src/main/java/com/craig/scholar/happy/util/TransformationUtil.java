package com.craig.scholar.happy.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class TransformationUtil {

  public static final Integer ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT = 0;
  public static final Integer ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT = 1;
  public static final Integer NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT = 2;
  public static final Integer NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT = 3;

  public static final Integer ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT = 4;
  public static final Integer ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT = 5;
  public static final Integer TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT = 6;
  public static final Integer TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT = 7;

  public static final Integer MAX_TRANSFORMATIONS = 8;

  public static int[] flip(int[] rows) {
    int[] flip = new int[rows.length];
    for (int i = 0; i < rows.length; i++) {
      flip[rows.length - 1 - i] = rows[i];
    }
    return flip;
  }

  public static int[] reflect(int[] rows) {
    int columns = getColumns(rows);
    int[] reflect = new int[rows.length];
    for (int i = 0; i < rows.length; i++) {
      for (int j = columns - 1; j >= 0; j--) {
        int b = (rows[i] & (1 << j)) != 0 ? 1 : 0;
        reflect[i] |= (b << (columns - 1 - j));
      }
    }
    return reflect;
  }

  public static int[] rotate(int[] rows) {
    int columns = getColumns(rows);
    int[] rotate = new int[columns];
    for (int i = 0; i < rows.length; i++) {
      for (int j = columns - 1; j >= 0; j--) {
        int b = (rows[i] & (1 << j)) != 0 ? 1 : 0;
        rotate[columns - 1 - j] |= (b << i);
      }
    }
    return rotate;
  }

  public static List<String> getTransformations(List<Integer> rows) {
    return getTransformations(rows.stream().mapToInt(Integer::intValue).toArray());
  }

  public static List<String> getTransformations(int[] rows) {
    return getTransformationsList(rows).stream()
        .map(Arrays::toString)
        .collect(Collectors.toList());
  }

  public static Collection<int[]> getTransformationsList(int[] rows) {
    int columns = getColumns(rows);
    Map<Integer, int[]> matrices = new HashMap<>(MAX_TRANSFORMATIONS);
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
    return matrices.values();
  }

  public static List<BitSet[]> getTransformations(BitSet[] matrix) {
    List<BitSet[]> matrices = new ArrayList<>(MAX_TRANSFORMATIONS);
    int rows = matrix.length;
    int columns = Arrays.stream(matrix)
        .mapToInt(BitSet::length)
        .max()
        .orElse(0);
    matrices.add(ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT, getBitSet(rows));
    matrices.add(ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT, getBitSet(rows));
    matrices.add(NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT, getBitSet(columns));
    matrices.add(NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT, getBitSet(columns));
    matrices.add(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT, getBitSet(rows));
    matrices.add(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT, getBitSet(rows));
    matrices.add(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT, getBitSet(columns));
    matrices.add(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT, getBitSet(columns));

    for (int r = 0; r < rows; r++) {
      int i = r;
      matrix[i].stream()
          .forEach(j -> {
            for (int t = 0; t < MAX_TRANSFORMATIONS; t++) {
              if (t == ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT) {
                matrices.get(t)[i].set(j);
              } else if (t == ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT) {
                matrices.get(t)[i].set(columns - 1 - j);
              } else if (t == NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT) {
                matrices.get(t)[columns - 1 - j].set(i);
              } else if (t == NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT) {
                matrices.get(t)[j].set(i);
              } else if (t == ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT) {
                matrices.get(t)[rows - 1 - i].set(columns - 1 - j);
              } else if (t == ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT) {
                matrices.get(t)[rows - 1 - i].set(j);
              } else if (t == TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT) {
                matrices.get(t)[j].set(rows - 1 - i);
              } else if (t == TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT) {
                matrices.get(t)[columns - 1 - j].set(rows - 1 - i);
              }
            }
          });
    }
    return matrices;
  }

  private static BitSet[] getBitSet(int rows) {
    BitSet[] bitSets = new BitSet[rows];
    Arrays.setAll(bitSets, k -> new BitSet());
    return bitSets;
  }

  public static List<boolean[][]> getTransformations(boolean[][] matrix) {
    List<boolean[][]> matrices = new ArrayList<>(MAX_TRANSFORMATIONS);
    int rows = matrix.length;
    int columns = matrix[0].length;
    matrices.add(ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT, new boolean[rows][columns]);
    matrices.add(ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT, new boolean[rows][columns]);
    matrices.add(NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT, new boolean[columns][rows]);
    matrices.add(NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT, new boolean[columns][rows]);
    matrices.add(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT, new boolean[rows][columns]);
    matrices.add(ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT, new boolean[rows][columns]);
    matrices.add(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT, new boolean[columns][rows]);
    matrices.add(TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT, new boolean[columns][rows]);
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        for (int t = 0; t < MAX_TRANSFORMATIONS; t++) {
          boolean[][] rotatedMatrix = matrices.get(t);
          if (ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT == t) {
            rotatedMatrix[i][j] = matrix[i][j];
          } else if (ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT == t) {
            rotatedMatrix[i][rotatedMatrix[0].length - 1 - j] = matrix[i][j];
          } else if (NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT == t) {
            rotatedMatrix[j][rotatedMatrix[0].length - 1 - i] = matrix[i][j];
          } else if (NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT == t) {
            rotatedMatrix[rotatedMatrix.length - 1 - j][rotatedMatrix[0].length - 1
                - i] = matrix[i][j];
          } else if (ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT == t) {
            rotatedMatrix[rotatedMatrix.length - 1 - i][j] = matrix[i][j];
          } else if (ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT == t) {
            rotatedMatrix[rotatedMatrix.length - 1 - i][rotatedMatrix[0].length - 1
                - j] = matrix[i][j];
          } else if (TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT == t) {
            rotatedMatrix[j][i] = matrix[i][j];
          } else if (TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT == t) {
            rotatedMatrix[rotatedMatrix.length - 1 - j][i] = matrix[i][j];
          }
        }
      }
    }
    return matrices;
  }

  private static int getColumns(int[] rows) {
    int columns = 0;
    for (int row : rows) {
      columns = Math.max(columns, Integer.SIZE - Integer.numberOfLeadingZeros(row));
    }
    return columns;
  }

}
