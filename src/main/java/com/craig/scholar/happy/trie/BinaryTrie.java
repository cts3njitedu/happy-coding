package com.craig.scholar.happy.trie;

import java.util.Objects;

public class BinaryTrie {

    private boolean isEndOfMatrix;
    private final BinaryTrie[] binaryTries = new BinaryTrie[3];

    public boolean find(boolean[][] matrix) {
        return find(matrix, 0, 0);
    }

    public boolean find(boolean[][] matrix, int r, int c) {
        if (r == matrix.length) {
            return isEndOfMatrix;
        }
        if (c == matrix[0].length) {
            BinaryTrie child = binaryTries[2];
            if (Objects.isNull(child)) {
                return false;
            }
            return child.find(matrix, r + 1, 0);
        } else {
            int childIndex = matrix[r][c] ? 1 : 0;
            BinaryTrie child = binaryTries[childIndex];
            if (Objects.isNull(child)) {
                return false;
            }
            return child.find(matrix, r, c + 1);
        }
    }

    public void add(boolean[][] matrix) {
        add(matrix, 0, 0);
    }

    public void add(boolean[][] matrix, int r, int c) {
        if (r == matrix.length) {
            isEndOfMatrix = true;
        } else {
            if (c == matrix[0].length) {
                BinaryTrie child = binaryTries[2];
                if (Objects.isNull(child)) {
                    child = new BinaryTrie();
                    binaryTries[2] = child;
                }
                child.add(matrix, r + 1, 0);
            } else {
                int childIndex = matrix[r][c] ? 1 : 0;
                BinaryTrie child = binaryTries[childIndex];
                if (Objects.isNull(child)) {
                    child = new BinaryTrie();
                    binaryTries[childIndex] = child;
                }
                child.add(matrix, r, c + 1);
            }
        }

    }

}
