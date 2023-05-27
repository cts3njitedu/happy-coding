package com.craig.scholar.happy.service.codeexchange;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FlattenList implements HappyCoding {

    @Override
    public void execute() {
        List<?> list = List.of(List.of(List.of("[]"), "[]"));
        List<?> list2 = flatten(list);
        list2 = list2.stream()
                .map(e -> {
                    if (e instanceof String) return "\"" + e + "\"";
                    if (e instanceof Character) return "'" + e + "'";
                    return e;
                })
                .collect(Collectors.toList());
        System.out.println(list2);
    }

    public List<Object> flatten(List<?> list) {
        return list.stream()
                .map(e -> e instanceof List ? flatten((List<?>) e) : List.of(e))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
