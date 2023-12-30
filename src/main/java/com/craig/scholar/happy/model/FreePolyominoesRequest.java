package com.craig.scholar.happy.model;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@Jacksonized
public class FreePolyominoesRequest {

    @NonNull
    Integer numberOfBlocks;

    UUID polysId;

    String sessionId;
}
