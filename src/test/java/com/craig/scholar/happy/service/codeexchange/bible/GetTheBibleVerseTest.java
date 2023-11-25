package com.craig.scholar.happy.service.codeexchange.bible;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GetTheBibleVerseTest {

  private final GetTheBibleVerse getTheBibleVerse = new GetTheBibleVerse();

  private static Stream<Arguments> bibleCases() {
    return Stream.of(
        Arguments.of(
            "Genesis 1:1",
            "In the beginning God created the heaven and the earth."
        ),
        Arguments.of(
            "1 Samuel 1:1",
            "Now there was a certain man of Ramathaimzophim, of mount Ephraim, and his name was Elkanah, the son of Jeroham, the son of Elihu, the son of Tohu, the son of Zuph, an Ephrathite:"
        ),
        Arguments.of(
            "1 Kings 1:1",
            "Now king David was old and stricken in years; and they covered him with clothes, but he gat no heat."
        ),
        Arguments.of(
            "Psalm 119:11",
            "Thy word have I hid in mine heart, that I might not sin against thee."
        ),
        Arguments.of(
            "John 3:16",
            "For God so loved the world, that he gave his only begotten Son, that whosoever believeth in him should not perish, but have everlasting life."
        ),
        Arguments.of(
            "1 John 1:9",
            "If we confess our sins, he is faithful and just to forgive us our sins, and to cleanse us from all unrighteousness."
        ),
        Arguments.of(
            "3 John 1",
            "The elder unto the wellbeloved Gaius, whom I love in the truth."
        ),
        Arguments.of(
            "Jude 21",
            "Keep yourselves in the love of God, looking for the mercy of our Lord Jesus Christ unto eternal life."
        ),
        Arguments.of(
            "Revelation 21:11",
            "Having the glory of God: and her light was like unto a stone most precious, even like a jasper stone, clear as crystal;"
        ),
        Arguments.of(
            "Revelation 21:16",
            "And the city lieth foursquare, and the length is as large as the breadth: and he measured the city with the reed, twelve thousand furlongs. The length and the breadth and the height of it are equal."
        ),
        Arguments.of(
            "Revelation 22:20",
            "He which testifieth these things saith, Surely I come quickly. Amen. Even so, come, Lord Jesus."
        ),
        Arguments.of(
            "Revelation 22:21",
            "The grace of our Lord Jesus Christ be with you all. Amen."
        )
    );
  }

  @ParameterizedTest
  @MethodSource("bibleCases")
  void getText(String referenceId, String expectedText) {
    Assertions.assertThat(getTheBibleVerse.getText(referenceId)).isEqualTo(expectedText);
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