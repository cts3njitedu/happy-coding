package com.craig.scholar.happy.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RequiredArgsConstructor
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class FreePolyominoesResponse {
    @NonNull
    List<boolean[][]> enumeratedPolyominoes;
}
