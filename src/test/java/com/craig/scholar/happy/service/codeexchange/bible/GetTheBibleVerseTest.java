package com.craig.scholar.happy.service.codeexchange.bible;

import org.junit.jupiter.api.Test;

class GetTheBibleVerseTest {

    private final GetTheBibleVerse getTheBibleVerse = new GetTheBibleVerse();
    @Test
    void getTheBibleVerse() {
//        List<Integer> numbers = List.of(1,2,3,4,5,1,1,1,1,6,7,8,9);
//        List<Integer> collect = numbers.stream()
//            .dropWhile(i -> i <= 5)
//            .takeWhile(i -> i != 1)
//            .collect(Collectors.toList());
//        System.out.println(collect);
        System.out.println(getTheBibleVerse.getTheBibleVerse("Exodus 1:6"));
    }
}