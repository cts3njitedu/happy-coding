package com.craig.scholar.happy.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@Jacksonized
public class FreePolyominoesRequest {

    @NonNull
    Integer n;
}
