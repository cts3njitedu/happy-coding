package com.craig.scholar.happy.util;

import com.craig.scholar.happy.model.either.ListEither;

import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public class ListUtil {

    public static ListEither<List<ListEither<? extends List<?>, ?>>, Object> convert(Object object) {
        if (object == null) return ListEither.of();
        if (!(object instanceof List)) return ListEither.ofItem(object);
        return ListEither.ofList(((List<?>) object).stream()
                .map(ListUtil::convert)
                .collect(Collectors.toList()));

    }
}
