package com.craig.scholar.happy.model;

import java.util.Collection;
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

    @NonNull
    Collection<?> polys;

    @NonNull
    Integer numberOfBlocks;

    @NonNull
    Integer numberOfPolys;
}
