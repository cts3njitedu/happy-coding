package com.craig.scholar.happy.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RequiredArgsConstructor
@Builder
@RedisHash
@Getter
public class FreePolyDto implements Serializable {

  private final String poly;

  private final Collection<?> polys;

  private final Collection<int[]> freePolys;

  private final int numberOfBlocks;

  private final int numberOfPolys;

  @Id
  private final UUID polysId;

  private final FreePolyState freePolyState;
}
