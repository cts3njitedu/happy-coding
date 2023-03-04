package com.craig.happy.coding.model.either;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ListEither<L extends List<ListEither<? extends List<?>, ?>>, R> extends Either<L, R> {

    private static final Set<Class<?>> validItems = Set.of(Number.class, Character.class, String.class);

    public ListEither(L l, R r) {
        super(l, r);
    }

    public static <C extends List<ListEither<? extends List<?>, ?>>, G> ListEither<C, G> of() {
        return new ListEither<>(null, null);
    }

    public static <C extends List<ListEither<? extends List<?>, ?>>, G> ListEither<C, G> ofItem(G g) {
        boolean isValid = validItems.stream()
                .anyMatch(cls -> cls.isInstance(g));
        if (isValid) {
            return new ListEither<>(null, g);
        } else {
            throw new IllegalArgumentException(String.format("Element must be one of these class types %s, " +
                    "but element has a class type of %s", validItems.stream()
                    .map(Class::getName)
                    .collect(Collectors.joining(",")), g.getClass()));
        }

    }

    public static <C extends List<ListEither<? extends List<?>, ?>>, G> ListEither<C, G> ofList(C c) {
        return new ListEither<>(c, null);
    }


    public boolean isList() {
        return super.isLeft();
    }

    public boolean isEmpty() {
        return Objects.isNull(super.getLeft())
                && Objects.isNull(super.getRight());
    }

    public L getList() {
        return super.getLeft();
    }
}
