package com.craig.scholar.happy.model;

import java.util.Collection;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class FreePolyominoesResponse {

    Collection<?> polys;

    Collection<int[][]> freePolys;

    @NonNull
    Integer numberOfBlocks;

    Integer numberOfPolys;

    @NonNull
    UUID polysId;

    FreePolyState freePolyState;

    String sessionId;
}
