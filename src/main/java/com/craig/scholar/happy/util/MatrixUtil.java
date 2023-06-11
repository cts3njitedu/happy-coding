package com.craig.scholar.happy.util;

import static com.craig.scholar.happy.util.TransformationUtil.MAX_TRANSFORMATIONS;
import static com.craig.scholar.happy.util.TransformationUtil.NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT;
import static com.craig.scholar.happy.util.TransformationUtil.NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT;
import static com.craig.scholar.happy.util.TransformationUtil.ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT;
import static com.craig.scholar.happy.util.TransformationUtil.ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT;
import static com.craig.scholar.happy.util.TransformationUtil.TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT;
import static com.craig.scholar.happy.util.TransformationUtil.TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT;
import static com.craig.scholar.happy.util.TransformationUtil.ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT;
import static com.craig.scholar.happy.util.TransformationUtil.ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MatrixUtil {

  public static String collapseMatrix(boolean[][] matrix) {
    StringBuilder builder = new StringBuilder();
    for (boolean[] row : matrix) {
      for (int j = 0; j < matrix[0].length; j++) {
        builder.append(row[j] ? 1 : 0);
      }
      builder.append(2);
    }
    return new BigInteger(builder.toString(), 3).toString(36);
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

  public static boolean isCongruent(boolean[][] matrix1, boolean[][] matrix2) {
    boolean[] transformations = getCongruentTransformations(matrix1, matrix2);
    if (hasEqualTransformation(transformations)) {
      for (int i = 0; i < matrix1.length; i++) {
        for (int j = 0; j < matrix1[0].length; j++) {
          checkIfTransformationsAreEqual(matrix1, matrix2, transformations, i, j);
        }
      }
      return hasEqualTransformation(transformations);
    }
    return false;
  }

  private static boolean[] getCongruentTransformations(boolean[][] matrix1, boolean[][] matrix2) {
    boolean areMatricesSimilar = (matrix1.length == matrix2.length)
        && (matrix1[0].length == matrix2[0].length);
    boolean areMatricesRotated = (matrix1.length == matrix2[0].length)
        && (matrix1[0].length == matrix2.length);
    boolean[] transformations = new boolean[MAX_TRANSFORMATIONS];
    transformations[ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesSimilar;
    transformations[ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesSimilar;
    transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesSimilar;
    transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesSimilar;
    transformations[NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesRotated;
    transformations[NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesRotated;
    transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT] = areMatricesRotated;
    transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT] = areMatricesRotated;
    return transformations;
  }

  private static boolean hasEqualTransformation(boolean[] transformations) {
    for (boolean b : transformations) {
      if (b) {
        return true;
      }
    }
    return false;
  }

  private static void checkIfTransformationsAreEqual(boolean[][] matrix1, boolean[][] matrix2,
      boolean[] transformations, int i, int j) {
    if (transformations[ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[ZERO_DEGREE_ROTATION_LEFT_TO_RIGHT] = matrix1[i][j] == matrix2[i][j];
    }
    if (transformations[ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[ZERO_DEGREE_ROTATION_RIGHT_TO_LEFT] = matrix1[i][j] == matrix2[i][
          matrix2[0].length - 1 - j];
    }
    if (transformations[NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[NINETY_DEGREE_ROTATION_LEFT_TO_RIGHT] =
          matrix1[i][j] == matrix2[j][matrix2[0].length - 1 - i];
    }
    if (transformations[NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[NINETY_DEGREE_ROTATION_RIGHT_TO_LEFT] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - j][matrix2[0].length - 1 - i];
    }
    if (transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_LEFT_TO_RIGHT] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - i][j];
    }
    if (transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[ONE_HUNDRED_EIGHTY_DEGREE_ROTATION_RIGHT_TO_LEFT] =
          matrix1[i][j] == matrix2[matrix2.length - 1 - i][matrix2[0].length - 1 - j];
    }
    if (transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT]) {
      transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_LEFT_TO_RIGHT] =
          matrix1[i][j] == matrix2[j][i];
    }
    if (transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT]) {
      transformations[TWO_HUNDRED_SEVENTY_DEGREE_ROTATION_RIGHT_TO_LEFT] = matrix1[i][j] == matrix2[
          matrix2.length - 1 - j][i];
    }
  }

  public static boolean[][] centerMatrix(boolean[][] matrix) {
    int rows = matrix.length;
    int columns = matrix[0].length;
    boolean startRowFromZero = true;
    boolean startColumnFromZero = true;
    for (int i = 0; i < matrix[0].length; i++) {
      if (matrix[0][i] || matrix[matrix.length - 1][i]) {
        if (matrix[0][i]) {
          rows++;
          startRowFromZero = false;
        }
        if (matrix[matrix.length - 1][i]) {
          rows++;
        }
        break;
      }
    }
    for (boolean[] booleans : matrix) {
      if (booleans[0] || booleans[matrix[0].length - 1]) {
        if (booleans[0]) {
          columns++;
          startColumnFromZero = false;
        }
        if (booleans[matrix[0].length - 1]) {
          columns++;
        }
        break;
      }
    }
    boolean[][] largerMatrix = new boolean[rows][columns];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        largerMatrix[startRowFromZero ? i : i + 1][startColumnFromZero ? j : j + 1] = matrix[i][j];
      }
    }
    return largerMatrix;
  }

  public static void print(int n, List<boolean[][]> matrices, String trueFlag) {
    printImage(n, matrices, matrix -> {
      for (boolean[] booleans : matrix) {
        for (int j = 0; j < matrix[0].length; j++) {
          System.out.print(booleans[j] ? trueFlag : " ".repeat(trueFlag.length()));
        }
        System.out.println();
      }
      System.out.println("__________________");
    });
  }

  public static void print2(int n, List<int[]> matrices, String trueFlag) {
    printImage(n, matrices, matrix -> {
      int columns = 0;
      for (int row : matrix) {
        columns = Math.max(columns, Integer.SIZE - Integer.numberOfLeadingZeros(row));
      }
      for (int r : matrix) {
        for (int c = columns - 1; c >= 0; c--) {
          System.out.print((r & (1 << c)) != 0 ? trueFlag : " ".repeat(trueFlag.length()));
        }
        System.out.println();
      }
      System.out.println("_________________");
    });
  }

  private static <T> void printImage(int n, List<T> matrices, Consumer<T> func) {
    System.out.printf("Number: %d, Count: %d%n\n", n, matrices.size());
    System.out.println("START");
    matrices.forEach(func);
    System.out.println("FINISH");
    System.out.printf("Number: %d, Count: %d%n\n", n, matrices.size());

  }

//  public static void printImage(int n, List<boolean[][]> matrices) {
//    System.out.printf("Number: %d, Count: %d%n\n", n, matrices.size());
//    System.out.println("START");
//    BufferedImage image = new BufferedImage(n*100, n*100, BufferedImage.TYPE_INT_RGB);
//    matrices
//        .forEach(matrix -> {
//          Graphics2D g2d = image.createGraphics();
//          for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//              if (matrix[i][j]) {
//                g2d.setColor(Color.BLUE);
//              } else {
//                g2d.setColor(Color.BLACK);
//              }
//              g2d.fillRect(100*j, 100*i, 100, 100);
//            }
//            System.out.println();
//          }
//          g2d.dispose();
//          printImage(image);
//
//          System.out.println("__________________");
//        });
//    System.out.println("FINISH");
//    System.out.printf("Number: %d, Count: %d%n\n", n, matrices.size());
//  }

//  private static void printImage(BufferedImage image) {
//    ImageIcon icon=new ImageIcon(image);
//    JFrame frame=new JFrame();
//    frame.setBackground(Color.GREEN);
//    frame.setLayout(new FlowLayout());
//    frame.setSize(500,500);
//    JLabel lbl=new JLabel();
//    lbl.setIcon(icon);
//    frame.add(lbl);
//    frame.setVisible(true);
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//  }
}
