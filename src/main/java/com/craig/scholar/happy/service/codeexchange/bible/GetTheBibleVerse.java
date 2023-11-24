package com.craig.scholar.happy.service.codeexchange.bible;

import com.craig.scholar.happy.service.codeexchange.HappyCoding;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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

  private static Map<String, String> BOOKS = new HashMap<>();

  static {
    BOOKS.put("1 Samuel", "The First Book of Samuel");
    BOOKS.put("2 Samuel", "The Second Book of Samuel");
    BOOKS.put("1 Kings", "The First Book of the Kings");
    BOOKS.put("2 Kings", "The Second Book of the Kings");
    BOOKS.put("1 Chronicles", "The First Book of the Chronicles");
    BOOKS.put("2 Chronicles", "The Second Book of the Chronicles");
    BOOKS.put("John", "The Gospel According to Saint John");
    BOOKS.put("1 Corinthians", "The First Epistle of Paul the Apostle to the Corinthians");
    BOOKS.put("2 Corinthians", "The Second Epistle of Paul the Apostle to the Corinthians");
    BOOKS.put("1 Thessalonians",
        "The First Epistle of Paul the Apostle to the Thessalonians");
    BOOKS.put("2 Thessalonians",
        "The Second Epistle of Paul the Apostle to the Thessalonians");
    BOOKS.put("1 Timothy", "The First Epistle of Paul the Apostle to Timothy");
    BOOKS.put("2 Timothy", "The Second Epistle of Paul the Apostle to Timothy");
    BOOKS.put("1 Peter", "The First Epistle General of Peter");
    BOOKS.put("2 Peter", "The Second Epistle General of Peter");
    BOOKS.put("1 John", "The First Epistle General of John");
    BOOKS.put("2 John", "The Second Epistle General of John");
    BOOKS.put("3 John", "The Third Epistle General of John");
    BOOKS.put("Revelation", "The Revelation of Saint John the Divine");
  }

  //(?<=2:20)(?<=[])?[\s\S]*?(?=\s\d+\:\d+|\z)
  private final Pattern pattern = Pattern.compile("(\\d+:\\d+)");

  private final String testPattern = "((?<=\\s%s\\s)[\\s\\S]*?(?=\\s\\d+\\:\\d+|\\z)) | (?<=^%s\\s)[\\s\\S]*?(?=\\s\\d+\\:\\d+|\\z) | (?<=\\n%s\\s)[\\s\\S]*?(?=\\s\\d+\\:\\d+|\\z)";

  private final String bookPattern = "(\\n{5}%s.*?\\n{5})";
  Map<String, String> ORDINAL_MAP = Map.of(
      "1", "First",
      "2", "Second",
      "3", "Third"
  );

  public String getTheBibleVerse(String reference) {
    Reference ref = getReference(reference);
    String oldTestamentHeading = "The Old Testament of the King James Version of the Bible";
    String newTestamentHeading = "The New Testament of the King James Bible";
    try {
      Path path = Paths.get(Objects.requireNonNull(GetTheBibleVerse.class.getClassLoader()
              .getResource("pg10.txt"))
          .toURI());
      Pattern exactMatchPattern = Pattern.compile(
          String.format(testPattern, ref.verse, ref.verse, ref.verse), Pattern.DOTALL);
      List<String> lines = Files.readAllLines(path);
      return lines.stream()
          .filter(line -> !line.isEmpty())
          .dropWhile(line -> !line.equals(oldTestamentHeading))
          .skip(1)
          .filter(line -> !line.equals(newTestamentHeading))
          .takeWhile(line -> !line.equals(oldTestamentHeading))
          .filter(line -> ref.ordinal == null ? line.contains(ref.name) :
              line.contains(ref.name) && line.contains(ORDINAL_MAP.get(ref.ordinal)))
          .findFirst()
          .map(bookName -> {
            Pattern bookMatchPattern = Pattern.compile(String.format(bookPattern, bookName),
                Pattern.DOTALL);
            Matcher bookMatcher = bookMatchPattern.matcher(String.join("\n", lines));
            if (bookMatcher.find()) {
              String book = bookMatcher.group();
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
