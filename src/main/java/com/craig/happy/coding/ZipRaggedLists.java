package com.craig.happy.coding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZipRaggedLists {

    public List<Object> zippedRaggedList(Object object1, Object object2) {
        List<Object> zippedRaggedList = new ArrayList<>();
        boolean isObject1List = object1 instanceof List;
        boolean isObject2List = object2 instanceof List;
        if (Objects.isNull(object1) || Objects.isNull(object2)) return zippedRaggedList;
        if (isObject1List || isObject2List) {
            List<Object> list = (List<Object>) (isObject1List && isObject2List ?
                    ((List<?>) object1).size() <= ((List<?>) object2).size() ?
                            object1 : object2 : isObject1List ? object1 : object2);
            for (int i = 0; i < list.size(); i++) {
                Object subObject1 = isObject1List ? ((List<Object>) object1).get(i) : object1;
                Object subObject2 = isObject2List ? ((List<Object>) object2).get(i) : object2;
                boolean isSubObject1List = subObject1 instanceof List;
                boolean isSubObject2List = subObject2 instanceof List;
                zippedRaggedList.add(isSubObject1List || isSubObject2List ?
                        zippedRaggedList(subObject1, subObject2) : String.format("(%s,%s)", subObject1, subObject2));
            }
        } else {
            zippedRaggedList.add(String.format("(%s,%s)", object1, object2));
        }
        return zippedRaggedList;
    }

}
