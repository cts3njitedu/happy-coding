package com.craig.scholar.happy.util;

import com.craig.scholar.happy.model.either.ListEither2;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListUtil2 {

    public static ListEither2<Object> convert(Object object) {
        if (Objects.isNull(object)) return ListEither2.of();
        if (!(object instanceof List)) return ListEither2.ofItem(object);
        return ListEither2.ofList(((List<Object>) object).stream()
                .map(ListUtil2::convert)
                .collect(Collectors.toList()));
    }

    public static ListEither2<Integer> convert(Object object, Class<Integer> cls) {
        if (Objects.isNull(object)) return ListEither2.of();
        if (!(object instanceof List)) return ListEither2.ofItem(cls.cast(object));
        return ListEither2.ofList(((List<Object>) object).stream()
                .map(o -> convert(o, cls))
                .collect(Collectors.toList()));
    }

}
