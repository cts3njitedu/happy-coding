package com.craig.scholar.happy.service.codeexchange.bible;

import java.util.List;
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
//    getBooks()
//        .forEach(book -> System.out.println(
//            book + ":" + getTheBibleVerse.getTheBibleVerse(book + " 119:2")));
    System.out.println(getTheBibleVerse.getTheBibleVerse("Jude 21"));
  }


  private List<String> getBooks() {
    return List.of("Genesis",
        "Exodus",
        "Leviticus",
        "Numbers",
        "Deuteronomy",
        "Joshua",
        "Judges",
        "Ruth",
        "1 Samuel",
        "2 Samuel",
        "1 Kings",
        "2 Kings",
        "1 Chronicles",
        "2 Chronicles",
        "Ezra",
        "Nehemiah",
        "Esther",
        "Job",
        "Psalm",
        "Proverbs",
        "Ecclesiastes",
        "Song of Solomon",
        "Isaiah",
        "Jeremiah",
        "Lamentations",
        "Ezekiel",
        "Daniel",
        "Hosea",
        "Joel",
        "Amos",
        "Obadiah",
        "Jonah",
        "Micah",
        "Nahum",
        "Habakkuk",
        "Zephaniah",
        "Haggai",
        "Zechariah",
        "Malachi",
        "Matthew",
        "Mark",
        "Luke",
        "John",
        "Acts",
        "Romans",
        "1 Corinthians",
        "2 Corinthians",
        "Galatians",
        "Ephesians",
        "Philippians",
        "Colossians",
        "1 Thessalonians",
        "2 Thessalonians",
        "1 Timothy",
        "2 Timothy",
        "Titus",
        "Philemon",
        "Hebrews",
        "James",
        "1 Peter",
        "2 Peter",
        "1 John",
        "2 John",
        "3 John",
        "Jude",
        "Revelation");
  }
}