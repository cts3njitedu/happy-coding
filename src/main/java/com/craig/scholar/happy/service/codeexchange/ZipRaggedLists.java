package com.craig.scholar.happy.service.codeexchange;

import com.craig.scholar.happy.model.either.ListEither2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.craig.scholar.happy.util.ListUtil2.convert;

public class ZipRaggedLists {

  private final String TUPLE_STRING_FORMAT = "(%s,%s)";


  public List<Object> zippedRaggedList(Object object1, Object object2) {
    List<Object> zippedRaggedList = new ArrayList<>();
    if (Objects.isNull(object1) || Objects.isNull(object2)) {
      return zippedRaggedList;
    }
    if (isObjectList(object1) || isObjectList(object2)) {
      int minimumMaxSize = getMinimumMaxSize(object1, object2);
      for (int i = 0; i < minimumMaxSize; i++) {
        Object subObject1 = getSubObject(object1, i);
        Object subObject2 = getSubObject(object2, i);
        zippedRaggedList.add(isObjectList(subObject1) || isObjectList(subObject2) ?
            zippedRaggedList(subObject1, subObject2)
            : String.format(TUPLE_STRING_FORMAT, subObject1, subObject2));
      }
    } else {
      zippedRaggedList.add(String.format(TUPLE_STRING_FORMAT, object1, object2));
    }
    return zippedRaggedList;
  }

  public List<Object> zippedRaggedListEither(Object object1, Object object2) {
    return zippedRaggedListEither(convert(object1), convert(object2));
  }

  public List<Object> zippedRaggedListEither(
      ListEither2<Object> either1, ListEither2<Object> either2) {
    List<Object> zippedRaggedList = new ArrayList<>();
    if (either1.isEmpty() || either2.isEmpty()) {
      return zippedRaggedList;
    }
    if (either1.isList() || either2.isList()) {
      int minimumMaxSize = getMinimumMaxSize(either1, either2);
      for (int i = 0; i < minimumMaxSize; i++) {
        ListEither2<Object> subEither1 = getSubObject(either1, i);
        ListEither2<Object> subEither2 = getSubObject(either2, i);
        zippedRaggedList.add(subEither1.isList() || subEither2.isList() ?
            zippedRaggedListEither(subEither1, subEither2)
            : String.format(TUPLE_STRING_FORMAT, subEither1.getItem(), subEither2.getItem()));
      }
    } else {
      zippedRaggedList.add(
          String.format(TUPLE_STRING_FORMAT, either1.getItem(), either2.getItem()));
    }
    return zippedRaggedList;
  }


  @SuppressWarnings("unchecked")
  private int getMinimumMaxSize(Object object1, Object object2) {
    return ((List<Object>) ((isObjectList(object1) && isObjectList(object2)) ?
        ((((List<?>) object1).size() <= ((List<?>) object2).size()) ?
            object1 : object2) : (isObjectList(object1) ? object1 : object2))).size();
  }

  private int getMinimumMaxSize(ListEither2<Object> either1,
      ListEither2<Object> either2) {
    return either1.isList() && either2.isList() ? Math.min(either1.getList().size(),
        either2.getList().size()) : either1.isList() ?
        either1.getList().size() : either2.isList() ?
        either2.getList().size() : 0;
  }

  @SuppressWarnings("unchecked")
  private Object getSubObject(Object object, int i) {
    return isObjectList(object) ? ((List<Object>) object).get(i) : object;
  }


  private boolean isObjectList(Object object) {
    return object instanceof List;
  }

  private ListEither2<Object> getSubObject(ListEither2<Object> either, int i) {
    return either.isList() ?
        either.getList().get(i).getListEither() : either;
  }

}
