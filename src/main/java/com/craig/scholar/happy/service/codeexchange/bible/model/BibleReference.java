package com.craig.scholar.happy.service.codeexchange.bible.model;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleReference {

  public static final String CHAPTER_AND_VERSE_FORMAT = "%s:%s";
  public static final String MAXIMUM_CHAPTERS = "200";

  private final String PASSAGE_PATTERN_FORMAT = "(^|\\s|\\n)(?<passage>(%s\\s)([\\s\\S]*?))(?:(\\s|\\n)(%s)(\\s|\\n)|\\z)";

  private final BibleBook bibleBook;
  private final ChapterAndVerse startChapterAndVerse;

  private final ChapterAndVerse endChapterAndVerse;

  private final Pattern pattern;

  @Builder
  public BibleReference(BibleBook bibleBook, ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    this.bibleBook = bibleBook;
    this.startChapterAndVerse = startChapterAndVerse;
    this.endChapterAndVerse = endChapterAndVerse;
    this.pattern = getPattern(startChapterAndVerse, endChapterAndVerse);
  }

  private Pattern getPattern(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    String startChapterAndVerseString = String.format(CHAPTER_AND_VERSE_FORMAT,
        getStartChapter(startChapterAndVerse, endChapterAndVerse),
        getStartVerse(startChapterAndVerse, endChapterAndVerse));
    String endChapterAndVerseString = String.join("|",
        String.format(CHAPTER_AND_VERSE_FORMAT,
            getFirstEndChapter(startChapterAndVerse, endChapterAndVerse),
            getFirstEndVerse(startChapterAndVerse, endChapterAndVerse)),
        String.format(CHAPTER_AND_VERSE_FORMAT,
            getSecondEndChapter(startChapterAndVerse, endChapterAndVerse),
            getSecondEndVerse(startChapterAndVerse, endChapterAndVerse)));
    return Pattern.compile(
        String.format(PASSAGE_PATTERN_FORMAT, startChapterAndVerseString, endChapterAndVerseString),
        Pattern.DOTALL);
  }

  private String getStartChapter(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    if (nonNull(startChapterAndVerse.chapter())
        && ((isNull(startChapterAndVerse.verse()) && isNull(endChapterAndVerse.verse()))
        || (nonNull(startChapterAndVerse.verse()) && (isNull(endChapterAndVerse.chapter())
        || nonNull(endChapterAndVerse.verse()))))) {
      return startChapterAndVerse.chapter();
    } else if (isNull(startChapterAndVerse.chapter()) && nonNull(startChapterAndVerse.verse())
        && isNull(endChapterAndVerse.chapter())) {
      return "1";
    }
    throw new IllegalArgumentException(
        "Invalid combination of start chapter/verse and end chapter/verse");
  }

  private String getStartVerse(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    if (nonNull(startChapterAndVerse.chapter()) && isNull(startChapterAndVerse.verse()) && isNull(
        endChapterAndVerse.verse())) {
      return "1";
    } else if (nonNull(startChapterAndVerse.verse()) && (isNull(endChapterAndVerse.chapter()) ||
        (nonNull(startChapterAndVerse.chapter()) && nonNull(endChapterAndVerse.verse())))) {
      return startChapterAndVerse.verse();
    }
    throw new IllegalArgumentException(
        "Invalid combination of start chapter/verse and end chapter/verse");
  }

  private String getFirstEndChapter(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    if (nonNull(startChapterAndVerse.chapter()) && isNull(startChapterAndVerse.verse()) && isNull(
        endChapterAndVerse.chapter()) && isNull(endChapterAndVerse.verse())) {
      return increment(startChapterAndVerse.chapter());
    } else if (nonNull(startChapterAndVerse.chapter()) && nonNull(startChapterAndVerse.verse())
        && isNull(endChapterAndVerse.chapter())) {
      return startChapterAndVerse.chapter();
    } else if (nonNull(startChapterAndVerse.chapter()) && isNull(startChapterAndVerse.verse())
        && nonNull(endChapterAndVerse.chapter()) && isNull(endChapterAndVerse.verse())) {
      return increment(endChapterAndVerse.chapter());
    } else if (nonNull(startChapterAndVerse.chapter()) && nonNull(startChapterAndVerse.verse())
        && nonNull(endChapterAndVerse.verse())) {
      return endChapterAndVerse.chapter();
    } else if (isNull(startChapterAndVerse.chapter()) && nonNull(startChapterAndVerse.verse())
        && isNull(endChapterAndVerse.chapter())) {
      return "1";
    }
    throw new IllegalArgumentException(
        "Invalid combination of start chapter/verse and end chapter/verse");
  }

  private String getFirstEndVerse(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    if ((nonNull(startChapterAndVerse.verse()) && nonNull(endChapterAndVerse.verse())) &&
        (nonNull(startChapterAndVerse.chapter()) || isNull(endChapterAndVerse.chapter()))) {
      return increment(endChapterAndVerse.verse());
    } else if (nonNull(startChapterAndVerse.verse()) && isNull(endChapterAndVerse.chapter())) {
      return increment(startChapterAndVerse.verse());
    } else if (nonNull(startChapterAndVerse.chapter()) && isNull(startChapterAndVerse.verse())
        && isNull(endChapterAndVerse.verse())) {
      return "1";
    }
    throw new IllegalArgumentException(
        "Invalid combination of start chapter/verse and end chapter/verse");
  }

  private String getSecondEndChapter(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    if ((nonNull(startChapterAndVerse.chapter()) && isNull(endChapterAndVerse.chapter())) && (
        nonNull(startChapterAndVerse.verse()) || isNull(endChapterAndVerse.verse()))) {
      return increment(startChapterAndVerse.chapter());
    } else if ((nonNull(startChapterAndVerse.chapter()) && nonNull(endChapterAndVerse.chapter()))
        && ((isNull(startChapterAndVerse.verse()) && isNull(endChapterAndVerse.verse())) ||
        (nonNull(startChapterAndVerse.verse()) && nonNull(endChapterAndVerse.verse())))) {
      return increment(endChapterAndVerse.chapter());
    } else if (isNull(startChapterAndVerse.chapter()) && nonNull(startChapterAndVerse.verse())
        && isNull(endChapterAndVerse.chapter())) {
      return "2";
    }
    throw new IllegalArgumentException(
        "Invalid combination of start chapter/verse and end chapter/verse");
  }

  private String getSecondEndVerse(ChapterAndVerse startChapterAndVerse,
      ChapterAndVerse endChapterAndVerse) {
    if ((nonNull(startChapterAndVerse.chapter())
        && ((isNull(endChapterAndVerse.chapter()) && isNull(endChapterAndVerse.verse())) ||
        (isNull(startChapterAndVerse.verse()) && isNull(endChapterAndVerse.verse())) ||
        (nonNull(startChapterAndVerse.verse()) && nonNull(endChapterAndVerse.verse()))))
        || (isNull(startChapterAndVerse.chapter()) && nonNull(startChapterAndVerse.verse())
        && isNull(endChapterAndVerse.chapter()))) {
      return "1";
    }
    throw new IllegalArgumentException(
        "Invalid combination of start chapter/verse and end chapter/verse");
  }

  private String increment(String value) {
    return String.valueOf(Integer.parseInt(value) + 1);
  }
}
