package com.craig.happy.coding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZipRaggedLists {


    public List<Object> zippedRaggedList(Object object1, Object object2) {
        List<Object> zippedRaggedList = new ArrayList<>();
        if (Objects.isNull(object1) || Objects.isNull(object2)) return zippedRaggedList;
        if (isObjectList(object1) || isObjectList(object2)) {
            int minimumMaxSize = getMinimumMaxSize(object1, object2);
            for (int i = 0; i < minimumMaxSize; i++) {
                Object subObject1 = isObjectList(object1) ? ((List<Object>) object1).get(i) : object1;
                Object subObject2 = isObjectList(object2) ? ((List<Object>) object2).get(i) : object2;
                zippedRaggedList.add(isObjectList(subObject1) || isObjectList(subObject2) ?
                        zippedRaggedList(subObject1, subObject2) : String.format("(%s,%s)", subObject1, subObject2));
            }
        } else {
            zippedRaggedList.add(String.format("(%s,%s)", object1, object2));
        }
        return zippedRaggedList;
    }

    @SuppressWarnings("unchecked")
    private int getMinimumMaxSize(Object object1, Object object2) {
        return ((List<Object>) ((isObjectList(object1) && isObjectList(object2)) ?
                ((((List<?>) object1).size() <= ((List<?>) object2).size()) ?
                        object1 : object2) : (isObjectList(object1) ? object1 : object2))).size();
    }

    private boolean isObjectList(Object object) {
        return object instanceof List;
    }

}
