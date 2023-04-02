package com.craig.happy.coding.util;

import com.craig.happy.coding.model.either.ListEither2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.craig.happy.coding.model.either.ListEither2.ofItem;
import static com.craig.happy.coding.model.either.ListEither2.ofList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ListUtil2Test {

    private static Stream<Arguments> listEitherCases() {
        return Stream.of(
                Arguments.of(
                        List.of(1, 2, 3),
                        ofList(ofItem(1), ofItem(2), ofItem(3))
                ),
                Arguments.of(
                        List.of(1, List.of(2, 3, 9), 3),
                        ofList(ofItem(1), ofList(ofItem(2), ofItem(3), ofItem(9)), ofItem(3))
                ),
                Arguments.of(
                        List.of(List.of(2, 3, List.of(5, 4))),
                        ofList(ofList(ofItem(2), ofItem(3), ofList(ofItem(5), ofItem(4))))
                ),
                Arguments.of(
                        List.of(List.of(1, 2, List.of(3, 4, List.of(5), List.of(6, 7, List.of(8, 9, 10), 11))), 12, 13,
                                List.of(14)),
                        ofList(ofList(ofItem(1), ofItem(2), ofList(ofItem(3), ofItem(4),
                                ofList(ofItem(5)), ofList(ofItem(6), ofItem(7), ofList(ofItem(8), ofItem(9), ofItem(10)),
                                        ofItem(11)))), ofItem(12), ofItem(13), ofList(ofItem(14)))
                ),
                Arguments.of(
                        List.of(1, List.of(2, List.of(3, List.of(4, List.of(5, List.of(4,
                                List.of(3, List.of(2, List.of(1))))))))),
                        ofList(ofItem(1), ofList(ofItem(2), ofList(ofItem(3), ofList(ofItem(4), ofList(ofItem(5),
                                ofList(ofItem(4), ofList(ofItem(3), ofList(ofItem(2), ofList(ofItem(1))))))))))
                ),
                Arguments.of(
                        List.of(),
                        ofList(List.of())
                )

        );
    }

    @ParameterizedTest
    @MethodSource("listEitherCases")
    void testConvert(List<Object> list,
                     ListEither2<Object> expectedList) {
        ListEither2<Object> actualList = ListUtil2.convert(list);
        isEqual(expectedList, actualList);
    }

    @Test
    void testConvertException() {
        assertThrows(IllegalArgumentException.class, () -> ListUtil.convert(List.of(Map.of())));
    }


    private void isEqual(ListEither2<Object> expectedListEither,
                         ListEither2<Object> actualListEither) {
        assertEquals(expectedListEither.isList(), actualListEither.isList());
        assertEquals(expectedListEither.isEmpty(), actualListEither.isEmpty());
        assertEquals(expectedListEither.isItem(), actualListEither.isItem());
        if (expectedListEither.isList()) {
            assertEquals(expectedListEither.getList().size(), actualListEither.getList().size());
            int size = expectedListEither.getList().size();
            for (int i = 0; i < size; i++) {
                isEqual(expectedListEither.getList().get(i).getListEither(),
                        actualListEither.getList().get(i).getListEither());
            }
        } else if (expectedListEither.isItem()) {
            assertEquals(expectedListEither.getItem(), actualListEither.getItem());
        }

    }
}
