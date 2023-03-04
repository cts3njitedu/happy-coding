package com.craig.happy.coding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

public class ZipRaggedListsTest {

    private final ZipRaggedLists zipRaggedLists = new ZipRaggedLists();

    private static Stream<Arguments> zipRaggedListsCases() {
        return Stream.of(
                Arguments.of(
                        List.of(1, 2, 3),
                        List.of(6, 5, 4),
                        "[(1,6), (2,5), (3,4)]"
                ),
                Arguments.of(
                        List.of(1, 2, 3),
                        List.of(6, 5, 4, 3, 2, 1),
                        "[(1,6), (2,5), (3,4)]"
                ),
                Arguments.of(
                        List.of(1, List.of(2, 3, 9), 3),
                        List.of(2, List.of(3, 4, 5, 6)),
                        "[(1,2),[(2,3),(3,4),(9,5)]]"
                ),
                Arguments.of(
                        List.of(1),
                        List.of(List.of(2, 3, List.of(5, 4))),
                        "[[(1,2),(1,3),[(1,5),(1,4)]]]"
                ),
                Arguments.of(
                        List.of(1, 2),
                        List.of(3, List.of(4, 5)),
                        "[(1,3),[(2,4),(2,5)]]"
                ),
                Arguments.of(
                        List.of(List.of(2, 3), 4),
                        List.of(1, List.of(6, 7)),
                        "[[(2,1),(3,1)],[(4,6),(4,7)]]"
                ),
                Arguments.of(
                        null,
                        List.of(1, List.of(6, 7)),
                        "[]"
                ),
                Arguments.of(
                        List.of(1, List.of(6, 7)),
                        null,
                        "[]"
                ),
                Arguments.of(
                        List.of(),
                        List.of(),
                        "[]"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("zipRaggedListsCases")
    void testZipRaggedLists(Object object1, Object object2, String expectedZipRaggedLists) {
        List<Object> actualZipRaggedLists = zipRaggedLists.zippedRaggedList(object1, object2);
        Assertions.assertEquals(expectedZipRaggedLists.replaceAll("\\s", ""),
                actualZipRaggedLists.toString().replaceAll("\\s", ""));
    }

    @ParameterizedTest
    @MethodSource("zipRaggedListsCases")
    void testZipRaggedListsEither(Object object1, Object object2, String expectedZipRaggedLists) {
        List<Object> actualZipRaggedLists = zipRaggedLists.zippedRaggedListEither(object1, object2);
        Assertions.assertEquals(expectedZipRaggedLists.replaceAll("\\s", ""),
                actualZipRaggedLists.toString().replaceAll("\\s", ""));
    }
}
