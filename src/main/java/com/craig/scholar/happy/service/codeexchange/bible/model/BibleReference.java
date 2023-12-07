package com.craig.scholar.happy.service.codeexchange.bible.model;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleReference {

  public static final String CHAPTER_AND_VERSE_FORMAT = "%s:%s";
  public static final String MAXIMUM_CHAPTERS = "200";

  private final String PASSAGE_PATTERN_FORMAT = "(^|\\s|\\n)(?<passage>(%s\\s)([\\s\\S]*?))(?:(\\s|\\n)(%s)(\\s|\\n)|\\z)";

  private final String REFERENCE_PATTERN_FORMAT = "^(((?<book1>%s)\\s(?<startChapter1>\\d+)(\\:(?<startVerse1>\\d+)|\\-(?<endChapter1>\\d+)|\\:(?<startVerse2>\\d+)\\-(?<endVerse1>\\d+)|\\:(?<startVerse3>\\d+)\\-(?<endChapter2>\\d+)\\:(?<endVerse2>\\d+))?)|((?<book2>%s)\\s(?<startVerse4>\\d+)(\\-(?<endVerse3>\\d+))?))$";

  private final Pattern REFFERENCE_PATTERN = Pattern.compile(
      String.format(REFERENCE_PATTERN_FORMAT, getBooks().stream()
          .filter(book -> !SINGLE_CHAPTER_BOOKS.contains(book))
          .collect(Collectors.joining("|")), String.join("|", SINGLE_CHAPTER_BOOKS)));

  private final BibleBook bibleBook;
  private final ChapterAndVerse startChapterAndVerse;

  private final ChapterAndVerse endChapterAndVerse;

  private final Pattern pattern;

  private final static Set<String> SINGLE_CHAPTER_BOOKS = Set.of(
      "Obadiah",
      "Philemon",
      "2 John",
      "3 John",
      "Jude"
  );


  private final static String BOOK_CAPTURE_GROUP = "book";

  private final static String START_CHAPTER_CAPTURE_GROUP = "startChapter";

  private final static String START_VERSE_CAPTURE_GROUP = "startVerse";

  private final static String END_CHAPTER_CAPTURE_GROUP = "endChapter";

  private final static String END_VERSE_CAPTURE_GROUP = "endVerse";

  private final static Map<String, Integer> CAPTURE_GROUPS = Map.of(
      BOOK_CAPTURE_GROUP, 2,
      START_CHAPTER_CAPTURE_GROUP, 1,
      START_VERSE_CAPTURE_GROUP, 4,
      END_CHAPTER_CAPTURE_GROUP, 2,
      END_VERSE_CAPTURE_GROUP, 3
  );

  @Builder
  public BibleReference(String reference) {
    Matcher matcher = REFFERENCE_PATTERN.matcher(reference);
    if (matcher.find()) {
      this.bibleBook = BibleBook.builder()
          .book(getValue(matcher, BOOK_CAPTURE_GROUP))
          .build();
      this.startChapterAndVerse = new ChapterAndVerse(
          getValue(matcher, START_CHAPTER_CAPTURE_GROUP),
          getValue(matcher, START_VERSE_CAPTURE_GROUP));
      this.endChapterAndVerse = new ChapterAndVerse(
          getValue(matcher, END_CHAPTER_CAPTURE_GROUP),
          getValue(matcher, END_VERSE_CAPTURE_GROUP));
      this.pattern = getPattern(this.startChapterAndVerse, this.endChapterAndVerse);
    } else {
      throw new IllegalArgumentException("Reference in invalid format");
    }
  }

  private String getValue(Matcher matcher, String captureGroup) {
    return IntStream.rangeClosed(1, CAPTURE_GROUPS.get(captureGroup))
        .mapToObj(String::valueOf)
        .map(captureGroup::concat)
        .map(matcher::group)
        .filter(Objects::nonNull)
        .findFirst()
        .orElse(null);
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


  private static Set<String> getBooks() {
    return Set.of("Genesis",
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
