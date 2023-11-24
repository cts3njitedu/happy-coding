package com.craig.scholar.happy.service.codeexchange.bible;

import com.craig.scholar.happy.service.codeexchange.HappyCoding;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTheBibleVerse implements HappyCoding {

  @Override
  public void execute() {

  }

  private record Reference(String ordinal, String name, String verse) {

  }

  public String getTheBibleVerse(String reference) {
    Reference ref = getReference(reference);
    final String OLD_TESTAMENT_HEADING = "The Old Testament of the King James Version of the Bible";
    final String NEW_TESTAMENT_HEADING = "The New Testament of the King James Bible";
    final Map<String, String> ORDINAL_MAP = Map.of(
        "1", "First",
        "2", "Second",
        "3", "Third"
    );
    final String BOOK_PATTERN_FORMAT = "(\\n{5}%s.*?\\n{5})";
    final String VERSE_PATTERN_FORMAT = "((?<=\\s%s\\s)|(?<=\\n1:21\\s) | (?<=^%s\\s))[\\s\\S]*?((?=\\s\\d+\\:\\d+|\\z)|(?=\\n\\d+\\:\\d+|\\z))";
    try {
      Path path = Paths.get(Objects.requireNonNull(GetTheBibleVerse.class.getClassLoader()
              .getResource("pg10.txt"))
          .toURI());
      List<String> lines = Files.readAllLines(path);
      return lines.stream()
          .filter(line -> !line.isEmpty())
          .dropWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
          .skip(1)
          .filter(line -> !line.equals(NEW_TESTAMENT_HEADING))
          .takeWhile(line -> !line.equals(OLD_TESTAMENT_HEADING))
          .filter(line -> ref.ordinal == null ? line.contains(ref.name) :
              line.contains(ref.name) && line.contains(ORDINAL_MAP.get(ref.ordinal)))
          .findFirst()
          .map(bookName -> {
            Pattern bookMatchPattern = Pattern.compile(String.format(BOOK_PATTERN_FORMAT, bookName),
                Pattern.DOTALL);
            Matcher bookMatcher = bookMatchPattern.matcher(String.join("\n", lines));
            if (bookMatcher.find()) {
              String book = bookMatcher.group();
              Pattern exactMatchPattern = Pattern.compile(
                  String.format(VERSE_PATTERN_FORMAT, ref.verse, ref.verse), Pattern.DOTALL);
              Matcher exactMatcher = exactMatchPattern.matcher(book);
              if (exactMatcher.find()) {
                return exactMatcher.group();
              }
            }
            return "";
          })
          .map(String::trim)
          .orElse("");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Reference getReference(String reference) {
    String[] references = reference.split(" ");
    if (references.length == 3) {
      return new Reference(references[0], references[1], references[2]);
    } else {
      return new Reference(null, references[0],
          (!references[1].contains(":") ? "1" + ":" : "") + references[1]);
    }
  }
}
