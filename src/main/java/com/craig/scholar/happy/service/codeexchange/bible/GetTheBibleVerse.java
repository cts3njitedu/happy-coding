package com.craig.scholar.happy.service.codeexchange.bible;

import com.craig.scholar.happy.service.codeexchange.HappyCoding;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTheBibleVerse implements HappyCoding {

  private static final String CHAPTER = "chapter";
  private static final String ORDINAL = "ordinal";
  private static final String BOOK = "book";
  private static final String VERSE = "verse";
  private static final String CHAPTER_VERSE = "chapterVerse";
  private static final String KING_JAMES_BIBLE = "pg10.txt";

  private final String OLD_TESTAMENT_HEADING = "The Old Testament of the King James Version of the Bible";
  private final String NEW_TESTAMENT_HEADING = "The New Testament of the King James Bible";
  private final Map<String, String> ORDINAL_MAP = Map.of(
      "1", "First",
      "2", "Second",
      "3", "Third"
  );
  private final String BOOK_PATTERN_FORMAT = "(\\n{5}%s.*?\\n{5})";
  private final String VERSE_PATTERN_FORMAT = "((?<=\\s%s\\s)|(?<=\\n1:21\\s) | (?<=^%s\\s))[\\s\\S]*?((?=\\s\\d+\\:\\d+|\\z)|(?=\\n\\d+\\:\\d+|\\z))";

  final Set<String> VALID_BOOKS = getBooks();
  final Set<String> VALID_SINGLE_CHAPTER_BOOKS = Set.of(
      "Obadiah",
      "Philemon",
      "John",
      "Jude"
  );
  final Pattern REFERENCE_PATTERN = Pattern.compile(
      "^((?<ordinal>\\d)(\\s))?(?<book>[A-Za-z]+)(\\s)(?<verse>\\d+|((?<chapter>\\d+):(?<chapterVerse>\\d+)))$");

  @Override
  public void execute() {

  }

  private record Reference(String ordinal, String name, String chapter, String verse) {

    public String chapterAndVerse() {
      return String.format("%s:%s", chapter == null ? "1" : chapter, verse);
    }

    public String fullBookName() {
      return ordinal == null ? name : String.join(" ", ordinal, name);
    }
  }

  public String getText(String referenceId) {
    Reference reference = getReference(referenceId);
    try {
      Path path = Paths.get(Objects.requireNonNull(GetTheBibleVerse.class.getClassLoader()
              .getResource(KING_JAMES_BIBLE))
          .toURI());
      List<String> lines = Files.readAllLines(path);
      return lines.stream()
          .filter(line -> !line.isEmpty())
          .dropWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
          .skip(1)
          .filter(line -> !line.equals(NEW_TESTAMENT_HEADING))
          .takeWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
          .filter(line -> reference.ordinal == null ? line.contains(reference.name) :
              line.contains(reference.name) && line.contains(ORDINAL_MAP.get(reference.ordinal)))
          .findFirst()
          .map(bookName -> {
            Pattern bookMatchPattern = Pattern.compile(String.format(BOOK_PATTERN_FORMAT, bookName),
                Pattern.DOTALL);
            Matcher bookMatcher = bookMatchPattern.matcher(String.join("\n", lines));
            if (bookMatcher.find()) {
              String book = bookMatcher.group();
              Pattern exactMatchPattern = Pattern.compile(
                  String.format(VERSE_PATTERN_FORMAT, reference.chapterAndVerse(),
                      reference.chapterAndVerse()),
                  Pattern.DOTALL);
              Matcher exactMatcher = exactMatchPattern.matcher(book);
              if (exactMatcher.find()) {
                return exactMatcher.group();
              }
            }
            return "";
          })
          .map(String::trim)
          .map(verse -> verse.replaceAll("\\R", " "))
          .map(verse -> verse.replaceAll("\\s+", " "))
          .orElseThrow(
              () -> new IllegalArgumentException(String.format("Couldn't find %s", referenceId)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private Reference getReference(String referenceId) {
    referenceId = referenceId.trim();
    Matcher referenceMatcher = REFERENCE_PATTERN.matcher(referenceId);
    if (referenceMatcher.find()) {
      String chapter = referenceMatcher.group(CHAPTER);
      Reference reference = new Reference(referenceMatcher.group(ORDINAL),
          referenceMatcher.group(BOOK), chapter,
          chapter == null ? referenceMatcher.group(VERSE)
              : referenceMatcher.group(CHAPTER_VERSE));
      if (!VALID_BOOKS.contains(reference.fullBookName())) {
        throw new IllegalArgumentException(
            String.format("Invalid book %s", reference.fullBookName()));
      }
      if (reference.chapter() == null) {
        if (!VALID_SINGLE_CHAPTER_BOOKS.contains(reference.name())) {
          throw new IllegalArgumentException(
              String.format("Invalid reference id %s. Book required to have chapter", referenceId));
        }
        if ("John".equals(reference.name())) {
          if (!"2".equals(reference.ordinal()) && !"3".equals(reference.ordinal())) {
            throw new IllegalArgumentException(String.format(
                "Invalid reference id %s. Only 2 John and 3 John allowed to have no chapter",
                referenceId));
          }
        }
      }
      return reference;
    }
    throw new IllegalArgumentException("Invalid reference id");
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
