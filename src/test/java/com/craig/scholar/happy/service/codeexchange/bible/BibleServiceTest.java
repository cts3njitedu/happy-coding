package com.craig.scholar.happy.service.codeexchange.bible;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BibleServiceTest {

  private final BibleService bibleService = new BibleService();

  private final Pattern CHAPTER_VERSE_PATTERN = Pattern.compile("\\d+:\\d+");

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
        ),
        Arguments.of(
            "Mark 6:3",
            "Is not this the carpenter, the son of Mary, the brother of James, and Joses, and of Juda, and Simon? and are not his sisters here with us? And they were offended at him."
        ),
        Arguments.of(
            "Genesis 50:27",
            ""
        )
    );
  }


  @ParameterizedTest
  @MethodSource("bibleCases")
  void getPassage(String referenceId, String expectedText) {
    assertThat(bibleService.getPassage(referenceId)).isEqualTo(expectedText);
  }

  @Test
  void getPassage_InvalidBook() {
    assertThatThrownBy(() -> bibleService.getPassage("Samuelite 1"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Samuelite");
  }

  @Test
  void getPassage_SingleChapter_Reference() {
    String text = bibleService.getPassage("Genesis 1");
    Matcher matcher = CHAPTER_VERSE_PATTERN.matcher(text);
    assertThat(matcher.results().count()).isEqualTo(31);
  }

  @Test
  void getPassage_Invalid_Trailing_Colon() {
    assertThatThrownBy(() -> bibleService.getPassage("Genesis 1:"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Genesis 1:");
  }

  @Test
  void getPassage_Invalid_NoSpaceBetweenBookAndChapter() {
    assertThatThrownBy(() -> bibleService.getPassage("Genesis1:4"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Genesis1:4");
  }

  @Test
  void getPassage_Invalid_NoSpaceBetweenOrdinalAndBook() {
    assertThatThrownBy(() -> bibleService.getPassage("1John3:4"))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("1John3:4");
  }
}