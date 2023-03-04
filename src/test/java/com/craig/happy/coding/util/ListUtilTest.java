package com.craig.happy.coding.util;

import com.craig.happy.coding.model.either.ListEither;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.craig.happy.coding.model.either.ListEither.ofItem;
import static com.craig.happy.coding.model.either.ListEither.ofList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListUtilTest {

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
                )

        );
    }

    @ParameterizedTest
    @MethodSource("listEitherCases")
    void testConvert(List<Object> list,
                     ListEither<? extends List<?>, ?> expectedList) {
        ListEither<List<ListEither<? extends List<?>, ?>>, Object> actualList = ListUtil.convert(list);
        isEqual(expectedList, actualList);
    }


    private void isEqual(ListEither<? extends List<?>, ?> expectedListEither,
                         ListEither<? extends List<?>, ?> actualListEither) {
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
