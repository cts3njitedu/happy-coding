package com.craig.scholar.happy.service.codeexchange.bible;

import com.craig.scholar.happy.service.codeexchange.HappyCoding;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BibleService implements HappyCoding {

  private static final String CHAPTER = "chapter";
  private static final String ORDINAL = "ordinal";
  private static final String BOOK = "book";
  private static final String VERSE = "verse";
  private static final String KING_JAMES_BIBLE = "pg10.txt";
  public static final String EMPTY = "";
  public static final String REFERENCE_ID = "referenceId";
  public static final String TEXT = "text";

  private final String OLD_TESTAMENT_HEADING = "The Old Testament of the King James Version of the Bible";
  private final String NEW_TESTAMENT_HEADING = "The New Testament of the King James Bible";
  private final Map<String, String> ORDINAL_MAP = Map.of(
      "1", "First",
      "2", "Second",
      "3", "Third"
  );

  private final Set<String> VALID_BOOKS = getBooks();
  private final Set<String> VALID_SINGLE_CHAPTER_BOOKS = Set.of(
      "Obadiah",
      "Philemon",
      "2 John",
      "3 John",
      "Jude"
  );

  private final String REFERENCE_PATTERN_FORMAT = "^(?<%s>((?<%s>[1-3])\\s)?[A-Za-z]+)\\s((?<%s>\\d+)\\:)?(?<%s>\\d+)$";
  private final Pattern REFERENCE_PATTERN = Pattern.compile(
      String.format(REFERENCE_PATTERN_FORMAT, BOOK, ORDINAL, CHAPTER, VERSE));

  private static final List<String> BIBLE = getBible();

  private static final String BIBLE_TEXT = String.join("\n", BIBLE);

  @Override
  public void execute() {

  }

  private record Reference(String ordinal, String book, String chapter, String verse) {

    public String chapterAndVerse() {
      return String.format("%s:%s", chapter == null ? "1" : chapter, verse);
    }

    public String bookNameWithoutOrdinal() {
      return ordinal == null ? book : book.split(" ")[1];
    }
  }

  private record Text(String referenceId, String text) {

  }

  public String getText(String referenceId) {
    Reference reference = getReference(referenceId);
    try {
      return getBookName(reference)
          .flatMap(bookName -> getText(reference, bookName))
          .map(Text::text)
          .filter(verse -> !verse.isEmpty())
          .map(String::trim)
          .map(verse -> verse.replaceAll("\\R", " "))
          .map(verse -> verse.replaceAll("\\s+", " "))
          .orElseGet(() -> {
            log.info("Unable to find text for reference {}", referenceId);
            return EMPTY;
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static List<String> getBible() {
    try {
      Path path = Paths.get(Objects.requireNonNull(BibleService.class.getClassLoader()
              .getResource(KING_JAMES_BIBLE))
          .toURI());
      return Files.readAllLines(path);
    } catch (Exception e) {
      log.error("Unable to retrieve bible.", e);
    }
    return List.of();
  }

  private Optional<String> getBookName(Reference reference) {
    return BIBLE.stream()
        .filter(line -> !line.isEmpty())
        .dropWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
        .skip(1)
        .filter(line -> !line.equals(NEW_TESTAMENT_HEADING))
        .takeWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
        .filter(line -> isBookTitle(reference, line))
        .findFirst();
  }

  private boolean isBookTitle(Reference reference, String line) {
    return reference.ordinal == null ? line.contains(reference.book) :
        line.contains(reference.bookNameWithoutOrdinal()) && line.contains(
            ORDINAL_MAP.get(reference.ordinal));
  }

  private Optional<Text> getText(Reference reference, String bookName) {
    String bookText = getBookText(bookName);
    if (!bookText.isEmpty()) {
      return getChapterAndVerseText(reference.chapterAndVerse(), bookText);
    }
    return Optional.empty();
  }

  private Reference getReference(String referenceId) {
    referenceId = referenceId.trim();
    Matcher referenceMatcher = REFERENCE_PATTERN.matcher(referenceId);
    if (referenceMatcher.find()) {
      Reference reference = new Reference(referenceMatcher.group(ORDINAL),
          referenceMatcher.group(BOOK), referenceMatcher.group(CHAPTER),
          referenceMatcher.group(VERSE));
      if (!VALID_BOOKS.contains(reference.book())) {
        throw new IllegalArgumentException(
            String.format("Invalid book %s", reference.book()));
      }
      if (reference.chapter() == null) {
        if (!VALID_SINGLE_CHAPTER_BOOKS.contains(reference.book())) {
          throw new IllegalArgumentException(
              String.format("Invalid reference id %s. Book required to have chapter", referenceId));
        }
        if (reference.book().contains("John")) {
          if (!"2".equals(reference.ordinal()) && !"3".equals(reference.ordinal())) {
            throw new IllegalArgumentException(String.format(
                "Invalid reference id %s. Only 2 John and 3 John allowed to have no chapter",
                referenceId));
          }
        }
      }
      return reference;
    }
    throw new IllegalArgumentException(String.format("Invalid reference id %s", referenceId));
  }

  private String getBookText(String bookName) {
    String BOOK_PATTERN_FORMAT = "(\\n{5}%s.*?\\n{5})";
    Pattern bookMatchPattern = Pattern.compile(String.format(BOOK_PATTERN_FORMAT, bookName),
        Pattern.DOTALL);
    Matcher bookMatcher = bookMatchPattern.matcher(BIBLE_TEXT);
    if (bookMatcher.find()) {
      return bookMatcher.group();
    }
    return EMPTY;
  }

  private Optional<Text> getChapterAndVerseText(String chapterAndVerse, String bookText) {
    String VERSE_PATTERN_FORMAT = "((^|\\s|\\n)(?<referenceId>%s)\\s)(?<text>[\\s\\S]*?)(?:(\\s|\\n)\\d+\\:\\d+(\\s|\\n)|\\z)";
    Pattern textMatchPattern = Pattern.compile(
        String.format(VERSE_PATTERN_FORMAT, chapterAndVerse),
        Pattern.DOTALL);
    Matcher textMatcher = textMatchPattern.matcher(bookText);
    if (textMatcher.find()) {
      return Optional.of(new Text(textMatcher.group(REFERENCE_ID), textMatcher.group(TEXT)));
    }
    return Optional.empty();
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
