package com.craig.scholar.happy.service.codeexchange.freepoly;

import static com.craig.scholar.happy.util.TransformationUtil.getTransformations;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class EnumerateFreePolyServiceBitSetArray implements EnumerateFreePolyService<BitSet[]> {

  @Override
  public Collection<BitSet[]> enumerate(int n) {
    BitSet[] rootPoly = new BitSet[1];
    BitSet bitSet = new BitSet();
    bitSet.set(0);
    rootPoly[0] = bitSet;
    LinkedList<BitSet[]> freePolys = new LinkedList<>();
    freePolys.add(rootPoly);
    Set<String> mem = new HashSet<>();
    mem.add(Arrays.toString(rootPoly));
    for (int i = 2; i <= n; i++) {
      int size = freePolys.size();
      while (size > 0) {
        BitSet[] freePoly = freePolys.poll();
        for (int _r = 0; _r < freePoly.length; _r++) {
          final int r = _r;
          BitSet row = freePoly[r];
          row.stream()
              .forEach(c -> Stream.of(
                      extendNorth(freePoly, r, c),
                      extendEast(freePoly, r, c),
                      extendSouth(freePoly, r, c),
                      extendWest(freePoly, r, c))
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .forEach(newPoly -> {
                    if (isUniquePoly(newPoly, mem)) {
                      freePolys.add(newPoly);
                    }
                  }));
        }
        size--;
      }
      mem.clear();
    }
    return freePolys;
  }

  private Optional<BitSet[]> extendNorth(BitSet[] freePoly, int r, int c) {
    boolean northBit = r != 0 && freePoly[r - 1].get(c);
    if (!northBit) {
      BitSet[] newPoly = new BitSet[r == 0 ? freePoly.length + 1 : freePoly.length];
      int northRowIndex = r == 0 ? 0 : r - 1;
      newPoly[northRowIndex] = new BitSet();
      for (int i = 0; i < freePoly.length; i++) {
        BitSet bitSet = (BitSet) freePoly[i].clone();
        newPoly[r == 0 ? i + 1 : i] = bitSet;
      }
      newPoly[northRowIndex].set(c);
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private Optional<BitSet[]> extendEast(BitSet[] freePoly, int r, int c) {
    boolean eastBit = c != 0 && freePoly[r].get(c - 1);
    if (!eastBit) {
      BitSet[] newPoly = new BitSet[freePoly.length];
      for (int i = 0; i < newPoly.length; i++) {
        BitSet newRow = new BitSet();
        freePoly[i].stream()
            .map(j -> c == 0 ? j + 1 : j)
            .forEach(newRow::set);
        if (i == r) {
          newRow.set(c == 0 ? 0 : c - 1);
        }
        newPoly[i] = newRow;
      }
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private Optional<BitSet[]> extendSouth(BitSet[] freePoly, int r, int c) {
    boolean southBit = r != freePoly.length - 1 && freePoly[r + 1].get(c);
    if (!southBit) {
      BitSet[] newPoly = new BitSet[r == freePoly.length - 1 ? freePoly.length + 1
          : freePoly.length];
      int southRowIndex = r == freePoly.length - 1 ? freePoly.length : r + 1;
      newPoly[southRowIndex] = new BitSet();
      for (int i = 0; i < freePoly.length; i++) {
        BitSet bitSet = (BitSet) freePoly[i].clone();
        newPoly[i] = bitSet;
      }
      newPoly[southRowIndex].set(c);
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private Optional<BitSet[]> extendWest(BitSet[] freePoly, int r, int c) {
    boolean westBit = freePoly[r].get(c + 1);
    if (!westBit) {
      BitSet[] newPoly = new BitSet[freePoly.length];
      for (int i = 0; i < newPoly.length; i++) {
        BitSet newRow = (BitSet) freePoly[i].clone();
        if (i == r) {
          newRow.set(c + 1);
        }
        newPoly[i] = newRow;
      }
      return Optional.of(newPoly);
    }
    return Optional.empty();
  }

  private boolean isUniquePoly(BitSet[] freePoly, Set<String> mem) {
    List<BitSet[]> transformations = getTransformations(freePoly);
    String polyString = Arrays.toString(freePoly);
    boolean isExist = transformations.stream()
        .map(Arrays::toString)
        .anyMatch(mem::contains);
    if (!isExist) {
      mem.add(polyString);
    }
    return !isExist;
  }
}
