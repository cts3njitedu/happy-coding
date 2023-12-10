package com.craig.scholar.happy.service.codeexchange.bible;

import static com.craig.scholar.happy.service.codeexchange.bible.model.BibleSearchQuery.getBibleSearchResult;

import com.craig.scholar.happy.service.codeexchange.HappyCoding;
import com.craig.scholar.happy.service.codeexchange.bible.model.BibleBook;
import com.craig.scholar.happy.service.codeexchange.bible.model.BibleReference;
import com.craig.scholar.happy.service.codeexchange.bible.model.BibleSearchResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BibleService implements HappyCoding {

  private static final String KING_JAMES_BIBLE = "pg10.txt";
  public static final String EMPTY = "";
  public static final String SINGLE_SPACE = " ";

  private final String OLD_TESTAMENT_HEADING = "The Old Testament of the King James Version of the Bible";
  private final String NEW_TESTAMENT_HEADING = "The New Testament of the King James Bible";
  private final Map<String, String> ORDINAL_MAP = Map.of(
      "1", "First",
      "2", "Second",
      "3", "Third"
  );

  private static final List<String> BIBLE = getBible();

  private static final String BIBLE_TEXT = String.join("\n", BIBLE);

  @Override
  public void execute() {

  }

  public String getPassage(String referenceId) {
    try {
      BibleReference bibleReference = BibleReference.builder()
          .reference(referenceId)
          .build();
      return getBookName(bibleReference)
          .flatMap(bookName -> getResult(bibleReference, bookName))
          .map(BibleSearchResult::getBiblePassages)
          .stream()
          .flatMap(Collection::stream)
          .map(biblePassage -> isSingleVerse(bibleReference) ? biblePassage.text() :
              String.join(SINGLE_SPACE, biblePassage.getChapterAndVerse(),
                  biblePassage.text()))
          .filter(verse -> !verse.isEmpty())
          .collect(Collectors.joining(SINGLE_SPACE));
    } catch (Exception e) {
      log.error("Unable to get passage for reference {}", referenceId, e);
      throw new IllegalArgumentException(
          String.format("Error: %s, Reference: %s", e.getMessage(), referenceId));
    }
  }

  private boolean isSingleVerse(BibleReference bibleReference) {
    return Objects.isNull(bibleReference.getEndChapterAndVerse().chapter())
        && Objects.isNull(bibleReference.getEndChapterAndVerse().verse())
        && Objects.nonNull(bibleReference.getStartChapterAndVerse().verse());
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

  private Optional<String> getBookName(BibleReference bibleReference) {
    return BIBLE.stream()
        .filter(line -> !line.isEmpty())
        .dropWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
        .skip(1)
        .filter(line -> !line.equals(NEW_TESTAMENT_HEADING))
        .takeWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
        .filter(line -> isBookTitle(bibleReference.getBibleBook(), line))
        .findFirst();
  }

  private boolean isBookTitle(BibleBook bibleBook, String line) {
    return bibleBook.getOrdinal() == null ? line.contains(bibleBook.getFullName()) :
        line.contains(bibleBook.getNameWithoutOrdinal()) && line.contains(
            ORDINAL_MAP.get(bibleBook.getOrdinal()));
  }

  private Optional<BibleSearchResult> getResult(BibleReference bibleReference, String bookName) {
    String bookText = getBookText(bookName);
    if (!bookText.isEmpty()) {
      return getBibleSearchResult(bibleReference, bookText);
    }
    return Optional.empty();
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

}
