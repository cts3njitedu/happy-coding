package com.craig.scholar.happy.model.either;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Deprecated
public class ListEither<L extends List<ListEither<? extends List<?>, ?>>, R>
        extends Either<L, R> {

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

    @SafeVarargs
    public static <C extends List<ListEither<? extends List<?>, ?>>, G> ListEither<C, G>
    ofList(ListEither<? extends List<?>, ?> g, ListEither<? extends List<?>, ?>... v) {
        List<ListEither<? extends List<?>, ?>> c = new ArrayList<>();
        c.add(g);
        c.addAll(List.of(v));
        return new ListEither<>((C) c, null);
    }

    public boolean isList() {
        return super.isLeft();
    }

    public boolean isItem() {
        return super.isRight();
    }

    public boolean isEmpty() {
        return (Objects.isNull(super.getLeft()) || super.getLeft().isEmpty())
                && Objects.isNull(super.getRight());
    }

    public ListEither<L, R> getListEither() {
        return this;
    }

    public L getList() {
        return super.getLeft();
    }

    public R getItem() {
        return super.getRight();
    }
}
