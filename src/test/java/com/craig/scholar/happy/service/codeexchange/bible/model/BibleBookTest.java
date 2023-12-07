package com.craig.scholar.happy.service.codeexchange.bible.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BibleBookTest {

  static Stream<Arguments> getAllBooks() {
    return Stream.of(
        Arguments.of("Genesis", "Genesis", null, "Genesis"),
        Arguments.of("Exodus", "Exodus", null, "Exodus"),
        Arguments.of("Leviticus", "Leviticus", null, "Leviticus"),
        Arguments.of("Numbers", "Numbers", null, "Numbers"),
        Arguments.of("Deuteronomy", "Deuteronomy", null, "Deuteronomy"),
        Arguments.of("Joshua", "Joshua", null, "Joshua"),
        Arguments.of("Judges", "Judges", null, "Judges"),
        Arguments.of("Ruth", "Ruth", null, "Ruth"),
        Arguments.of("1 Samuel", "1 Samuel", "1", "Samuel"),
        Arguments.of("2 Samuel", "2 Samuel", "2", "Samuel"),
        Arguments.of("1 Kings", "1 Kings", "1", "Kings"),
        Arguments.of("2 Kings", "2 Kings", "2", "Kings"),
        Arguments.of("1 Chronicles", "1 Chronicles", "1", "Chronicles"),
        Arguments.of("2 Chronicles", "2 Chronicles", "2", "Chronicles"),
        Arguments.of("Ezra", "Ezra", null, "Ezra"),
        Arguments.of("Nehemiah", "Nehemiah", null, "Nehemiah"),
        Arguments.of("Esther", "Esther", null, "Esther"),
        Arguments.of("Job", "Job", null, "Job"),
        Arguments.of("Psalm", "Psalm", null, "Psalm"),
        Arguments.of("Proverbs", "Proverbs", null, "Proverbs"),
        Arguments.of("Ecclesiastes", "Ecclesiastes", null, "Ecclesiastes"),
        Arguments.of("Song of Solomon", "Song of Solomon", null, "Song of Solomon"),
        Arguments.of("Isaiah", "Isaiah", null, "Isaiah"),
        Arguments.of("Jeremiah", "Jeremiah", null, "Jeremiah"),
        Arguments.of("Lamentations", "Lamentations", null, "Lamentations"),
        Arguments.of("Ezekiel", "Ezekiel", null, "Ezekiel"),
        Arguments.of("Daniel", "Daniel", null, "Daniel"),
        Arguments.of("Hosea", "Hosea", null, "Hosea"),
        Arguments.of("Joel", "Joel", null, "Joel"),
        Arguments.of("Amos", "Amos", null, "Amos"),
        Arguments.of("Obadiah", "Obadiah", null, "Obadiah"),
        Arguments.of("Jonah", "Jonah", null, "Jonah"),
        Arguments.of("Micah", "Micah", null, "Micah"),
        Arguments.of("Nahum", "Nahum", null, "Nahum"),
        Arguments.of("Habakkuk", "Habakkuk", null, "Habakkuk"),
        Arguments.of("Zephaniah", "Zephaniah", null, "Zephaniah"),
        Arguments.of("Haggai", "Haggai", null, "Haggai"),
        Arguments.of("Zechariah", "Zechariah", null, "Zechariah"),
        Arguments.of("Malachi", "Malachi", null, "Malachi"),
        Arguments.of("Matthew", "Matthew", null, "Matthew"),
        Arguments.of("Mark", "Mark", null, "Mark"),
        Arguments.of("Luke", "Luke", null, "Luke"),
        Arguments.of("John", "John", null, "John"),
        Arguments.of("Acts", "Acts", null, "Acts"),
        Arguments.of("Romans", "Romans", null, "Romans"),
        Arguments.of("1 Corinthians", "1 Corinthians", "1", "Corinthians"),
        Arguments.of("2 Corinthians", "2 Corinthians", "2", "Corinthians"),
        Arguments.of("Galatians", "Galatians", null, "Galatians"),
        Arguments.of("Ephesians", "Ephesians", null, "Ephesians"),
        Arguments.of("Philippians", "Philippians", null, "Philippians"),
        Arguments.of("Colossians", "Colossians", null, "Colossians"),
        Arguments.of("1 Thessalonians", "1 Thessalonians", "1", "Thessalonians"),
        Arguments.of("2 Thessalonians", "2 Thessalonians", "2", "Thessalonians"),
        Arguments.of("1 Timothy", "1 Timothy", "1", "Timothy"),
        Arguments.of("2 Timothy", "2 Timothy", "2", "Timothy"),
        Arguments.of("Titus", "Titus", null, "Titus"),
        Arguments.of("Philemon", "Philemon", null, "Philemon"),
        Arguments.of("Hebrews", "Hebrews", null, "Hebrews"),
        Arguments.of("James", "James", null, "James"),
        Arguments.of("1 Peter", "1 Peter", "1", "Peter"),
        Arguments.of("2 Peter", "2 Peter", "2", "Peter"),
        Arguments.of("1 John", "1 John", "1", "John"),
        Arguments.of("2 John", "2 John", "2", "John"),
        Arguments.of("3 John", "3 John", "3", "John"),
        Arguments.of("Jude", "Jude", null, "Jude"),
        Arguments.of("Revelation", "Revelation", null, "Revelation")
    );
  }

  @ParameterizedTest
  @MethodSource("getAllBooks")
  void getBibleBook_AllBooks(String bookString, String fullName, String ordinal,
      String nameWithoutOrdinal) {
    BibleBook bibleBook = BibleBook.builder()
        .book(bookString)
        .build();
    assertThat(bibleBook.getFullName()).isEqualTo(fullName);
    assertThat(bibleBook.getOrdinal()).isEqualTo(ordinal);
    assertThat(bibleBook.getNameWithoutOrdinal()).isEqualTo(nameWithoutOrdinal);
  }


}