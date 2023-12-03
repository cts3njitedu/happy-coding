package com.craig.scholar.happy.service.codeexchange.bible.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class BibleReferenceTest {

  @Test
  void getPattern_StartChapterAndVerseNextVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("4", "24"))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passage>(4:24\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:25|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        5:3 And Adam lived an hundred and thirty years, and begat a son in his
        own likeness, and after his image; and called his name Seth: 5:4 And
        the days of Adam after he had begotten Seth were eight hundred years:
        and he begat sons and daughters: 5:5 And all the days that Adam lived
        were nine hundred and thirty years: and he died.

        5:6 And Seth lived an hundred and five years, and begat Enos: 5:7 And
        Seth lived after he begat Enos eight hundred and seven years, and
        begat sons and daughters: 5:8 And all the days of Seth were nine
        hundred and twelve years: and he died.""";
    String expectedPassage = "4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and sevenfold.";
    assertBibleReference(bibleReference.getPattern(), expectedPatternString, text, expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndVerseNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("4", "26"))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passage>(4:26\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:27|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        5:3 And Adam lived an hundred and thirty years, and begat a son in his
        own likeness, and after his image; and called his name Seth: 5:4 And
        the days of Adam after he had begotten Seth were eight hundred years:
        and he begat sons and daughters: 5:5 And all the days that Adam lived
        were nine hundred and thirty years: and he died.

        5:6 And Seth lived an hundred and five years, and begat Enos: 5:7 And
        Seth lived after he begat Enos eight hundred and seven years, and
        begat sons and daughters: 5:8 And all the days of Seth were nine
        hundred and twelve years: and he died.""";
    String expectedPassage = "4:26 And to Seth, to him also there was born a son; and he called his name Enos: then began men to call upon the name of the LORD.";
    assertBibleReference(bibleReference.getPattern(), expectedPatternString, text, expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndVerseEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("4", "26"))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passage>(4:26\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:27|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        """;
    String expectedPassage = "4:26 And to Seth, to him also there was born a son; and he called his name Enos: then began men to call upon the name of the LORD.";
    assertBibleReference(bibleReference.getPattern(), expectedPatternString, text, expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("4", null))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passage>(4:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(5:1|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and
        said, I have gotten a man from the LORD.
                                                                    
        4:2 And she again bare his brother Abel. And Abel was a keeper of
        sheep, but Cain was a tiller of the ground.
                                                                    
        4:3 And in process of time it came to pass, that Cain brought of the
        fruit of the ground an offering unto the LORD.
                
        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        """;
    String expectedPassage =
        "4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and "
            + "said, I have gotten a man from the LORD. "
            + "4:2 And she again bare his brother Abel. And Abel was a keeper of "
            + "sheep, but Cain was a tiller of the ground. "
            + "4:3 And in process of time it came to pass, that Cain brought of the "
            + "fruit of the ground an offering unto the LORD.";
    assertBibleReference(bibleReference.getPattern(), expectedPatternString, text, expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("4", null))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passage>(4:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(5:1|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and
        said, I have gotten a man from the LORD.
                                                                    
        4:2 And she again bare his brother Abel. And Abel was a keeper of
        sheep, but Cain was a tiller of the ground.
                                                                    
        4:3 And in process of time it came to pass, that Cain brought of the
        fruit of the ground an offering unto the LORD.

        """;
    String expectedPassage =
        "4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and "
            + "said, I have gotten a man from the LORD. "
            + "4:2 And she again bare his brother Abel. And Abel was a keeper of "
            + "sheep, but Cain was a tiller of the ground. "
            + "4:3 And in process of time it came to pass, that Cain brought of the "
            + "fruit of the ground an offering unto the LORD.";
    assertBibleReference(bibleReference.getPattern(), expectedPatternString, text, expectedPassage);
  }

  @Test
  void getBibleReference_StartVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse(null, "5"))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(1:5\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:6|2:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getBibleReference_StartChapterStartVerseAndEndVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("5", "3"))
        .endChapterAndVerse(new ChapterAndVerse(null, "6"))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(5:3\\s)([\\s\\S]*?))(?:(\\s|\\n)(5:7|6:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getBibleReference_StartChapterAndEndChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("5", null))
        .endChapterAndVerse(new ChapterAndVerse("6", null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(5:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(7:1|7:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getBibleReference_StartChapterAndStartVerseAndEndChapterAndEndVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("5", "3"))
        .endChapterAndVerse(new ChapterAndVerse("6", "2"))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(5:3\\s)([\\s\\S]*?))(?:(\\s|\\n)(6:3|7:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getBibleReference_StartVerseAndEndVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse(null, "5"))
        .endChapterAndVerse(new ChapterAndVerse(null, "9"))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(1:5\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:10|2:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getBibleReference_EndVerse() {
    assertThatThrownBy(() -> BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse(null, null))
        .endChapterAndVerse(new ChapterAndVerse(null, "9"))
        .bibleBook(new BibleBook("John", "5", null))
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getBibleReference_EndChapter() {
    assertThatThrownBy(() -> BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse(null, null))
        .endChapterAndVerse(new ChapterAndVerse("9", null))
        .bibleBook(new BibleBook("John", "5", null))
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getBibleReference_StartChapterAndEndChapterAndEndVerse() {
    assertThatThrownBy(() -> BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("5", null))
        .endChapterAndVerse(new ChapterAndVerse("9", "2"))
        .bibleBook(new BibleBook("John", "5", null))
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  private void assertBibleReference(Pattern pattern, String expectedPatternString, String text,
      String expectedPassage) {
    assertThat(pattern.pattern())
        .isEqualTo(expectedPatternString);
    Matcher nextVerseMatcher = pattern.matcher(text);
    assertThat(nextVerseMatcher.find()).isTrue();
    String passage = nextVerseMatcher.group("passage");
    passage = passage.trim();
    passage = passage.replaceAll("\\R", " ");
    passage = passage.replaceAll("\\s+", " ");
    assertThat(passage).isEqualTo(expectedPassage);
  }

}