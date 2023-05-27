package com.craig.scholar.happy.model.either;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ListEither2<R>
    extends Either<List<ListEither2<R>>, R> {

  private static final Set<Class<?>> validItems = Set.of(Number.class, Character.class,
      String.class, Boolean.class);

  public ListEither2(List<ListEither2<R>> l) {
    super(l, null);
  }

  public ListEither2(R r) {
    super(null, r);
  }

  public ListEither2() {
    super(null, null);
  }

  public static <G> ListEither2<G> of() {
    return new ListEither2<>();
  }

  public static <G> ListEither2<G> ofItem(G g) {
    boolean isValid = validItems.stream()
        .anyMatch(cls -> cls.isInstance(g));
    if (isValid) {
      return new ListEither2<>(g);
    } else {
      throw new IllegalArgumentException(
          String.format("Element must be one of these class types %s, " +
              "but element has a class type of %s", validItems.stream()
              .map(Class::getName)
              .collect(Collectors.joining(",")), g.getClass()));
    }

  }

  public static <G> ListEither2<G> ofList(List<ListEither2<G>> l) {
    return new ListEither2<>(l);
  }

  @SafeVarargs
  public static <G> ListEither2<G> ofList(ListEither2<G> g, ListEither2<G>... v) {
    List<ListEither2<G>> c = new ArrayList<>();
    c.add(g);
    c.addAll(List.of(v));
    return new ListEither2<>(c);
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

  public ListEither2<R> getListEither() {
    return this;
  }

  public List<ListEither2<R>> getList() {
    return super.getLeft();
  }

  public Object getItem() {
    return super.getRight();
  }
}
