package com.craig.happy.coding.util;

import com.craig.happy.coding.model.either.ListEither;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {

    public static ListEither<List<ListEither<? extends List<?>, ?>>, Object> convert(Object object) {
        if (object == null) return ListEither.of();
        if (!(object instanceof List)) return ListEither.ofItem(object);
        return ListEither.ofList(((List<?>) object).stream()
                .map(ListUtil::convert)
                .collect(Collectors.toList()));

    }
}
