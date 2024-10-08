package com.craig.scholar.happy.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class FreePolyDto implements Serializable {

  private final Collection<int[][]> freePolys;

  private final int numberOfBlocks;

  private final int numberOfPolys;

  private final String sessionId;

  private final UUID polysId;
}
