package com.craig.scholar.happy.model;

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
public class FreePolyDto {

  private final String poly;

  private final Collection<?> polys;

  private final int numberOfBlocks;

  private final int numberOfPolys;

  @Id
  private final UUID polysId;
}
