package com.craig.happy.coding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZipRaggedLists {

    private final String TUPLE_STRING_FORMAT = "(%s,%s)";

    public List<Object> zippedRaggedList(Object object1, Object object2) {
        List<Object> zippedRaggedList = new ArrayList<>();
        if (Objects.isNull(object1) || Objects.isNull(object2)) return zippedRaggedList;
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

    @SuppressWarnings("unchecked")
    private int getMinimumMaxSize(Object object1, Object object2) {
        return ((List<Object>) ((isObjectList(object1) && isObjectList(object2)) ?
                ((((List<?>) object1).size() <= ((List<?>) object2).size()) ?
                        object1 : object2) : (isObjectList(object1) ? object1 : object2))).size();
    }

    @SuppressWarnings("unchecked")
    private Object getSubObject(Object object, int i) {
        return isObjectList(object) ? ((List<Object>) object).get(i) : object;
    }

    private boolean isObjectList(Object object) {
        return object instanceof List;
    }

}
