package com.craig.scholar.happy.service.codeexchange.bible.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class BibleReferenceTest {

  @Test
  void getPattern_StartChapterAndVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("5", "2"))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(5:2\\s)([\\s\\S]*?))(?:(\\s|\\n)(5:3|6:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getPattern_StartChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .startChapterAndVerse(new ChapterAndVerse("5", null))
        .endChapterAndVerse(new ChapterAndVerse(null, null))
        .bibleBook(new BibleBook("John", "5", null))
        .build();
    assertThat(bibleReference.getPattern().pattern())
        .isEqualTo(
            "(^|\\s|\\n)(?<passage>(5:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(6:1|6:1)(\\s|\\n)|\\z)");
  }

  @Test
  void getPattern_StartVerse() {
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
  void getPattern_StartChapterStartVerseAndEndVerse() {
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
  void getPattern_StartChapterAndEndChapter() {
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
  void getPattern_StartChapterAndStartVerseAndEndChapterAndEndVerse() {
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
  void getPattern_StartVerseAndEndVerse() {
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

}