package com.craig.happy.coding.model.either;

import java.util.Objects;

public class Either<L, R> {

    private final L l;

    private final R r;

    public Either(L l, R r) {
        this.l = l;
        this.r = r;
    }

    public static <C, G> Either<C, G> left(C c) {
        return new Either<>(c, null);
    }

    public static <C, G> Either<C, G> right(G c) {
        return new Either<>(null, c);
    }


    public boolean isLeft() {
        return Objects.nonNull(l);
    }

    public boolean isRight() {
        return Objects.nonNull(r);
    }

    public L getLeft() {
        return l;
    }

    public R getRight() {
        return r;
    }


}
