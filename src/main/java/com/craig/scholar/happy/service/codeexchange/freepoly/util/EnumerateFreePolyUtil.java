package com.craig.scholar.happy.service.codeexchange.freepoly.util;

import static com.craig.scholar.happy.util.MatrixUtil.collapseMatrix;
import static com.craig.scholar.happy.util.MatrixUtil.isCongruent;
import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import com.craig.scholar.happy.trie.MatrixTrie;
import com.craig.scholar.happy.util.MatrixUtil;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EnumerateFreePolyUtil {

  public static boolean isValidCell(boolean[][] polyomino, int row, int column) {
    return row == -1 || column == -1 || row == polyomino.length || column == polyomino[0].length
        || !polyomino[row][column];
  }

  public static boolean[][] getNewPolyomino(boolean[][] polyomino, int row, int column) {
    boolean[][] newPolyomino = new boolean[(row == -1 || row == polyomino.length) ?
        polyomino.length + 1 : polyomino.length][(column == -1 || column == polyomino[0].length) ?
        polyomino[0].length + 1 : polyomino[0].length];
    int firstRow = row == -1 ? 1 : 0;
    int firstColumn = column == -1 ? 1 : 0;
    for (int i = 0; i < polyomino.length; i++) {
      for (int j = 0; j < polyomino[0].length; j++) {
        newPolyomino[firstRow == 1 ? i + 1 : i][firstColumn == 1 ? j + 1 : j] = polyomino[i][j];
      }
    }
    newPolyomino[row == -1 ? 0 : row][column == -1 ? 0 : column] = true;
    return newPolyomino;
  }

  public static boolean isExist(List<boolean[][]> polyominoes, boolean[][] newPolyomino) {
    return Optional.ofNullable(polyominoes)
        .orElse(List.of())
        .parallelStream()
        .anyMatch(existingPolyomino -> isCongruent(existingPolyomino, newPolyomino));
  }

  public static boolean isExist(Set<String> polyMemory, boolean[][] newPoly) {
    if (polyMemory.contains(collapseMatrix(newPoly))) {
      return true;
    }
    List<String> transformations = getTransformations(newPoly).stream()
        .map(MatrixUtil::collapseMatrix)
        .toList();
    boolean isExist = transformations.stream()
        .anyMatch(polyMemory::contains);
    if (!isExist) {
      polyMemory.addAll(transformations);
    }
    return isExist;
  }

  public static boolean isExist(MatrixTrie matrixTrie, boolean[][] newPoly) {
    if (matrixTrie.find(newPoly)) {
      return true;
    }
    List<boolean[][]> transformations = getTransformations(newPoly);
    boolean isExist = transformations.stream()
        .anyMatch(matrixTrie::find);
    if (!isExist) {
      transformations
          .forEach(matrixTrie::add);
    }
    return isExist;
  }


}
