package com.craig.scholar.happy.service.codeexchange.bible;

import com.craig.scholar.happy.service.codeexchange.HappyCoding;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

  private final Pattern pattern = Pattern.compile("(\\d+:\\d+)");
  Map<String, String> ORDINAL_MAP = Map.of(
      "1", "First",
      "2", "Second",
      "3", "Third"
  );

  public String getTheBibleVerse(String reference) {
    try {
      Reference ref = getReference(reference);
      String oldTestamentHeading = "The Old Testament of the King James Version of the Bible";
      String endOfBible = "*** END OF THE PROJECT GUTENBERG EBOOK THE KING JAMES VERSION OF THE BIBLE ***";
      AtomicInteger oldTestamentHeadingCount = new AtomicInteger(0);
      try {
        Map<String, Integer> books = new HashMap<>();
        Path path = Paths.get(Objects.requireNonNull(GetTheBibleVerse.class.getClassLoader()
                .getResource("pg10.txt"))
            .toURI());
        AtomicReference<String> bookName = new AtomicReference<>();
        Pattern exactMatchPattern = Pattern.compile(ref.verse);
        List<String> lines = Files.readAllLines(path);
        String verses = lines.stream()
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .dropWhile(line -> {
              if (line.contains(oldTestamentHeading)) {
                return oldTestamentHeadingCount.incrementAndGet() != 2;
              } else if (oldTestamentHeadingCount.get() == 1) {
                books.put(line, 1);
                if (ref.ordinal == null) {
                  return !line.contains(ref.name);
                }
                return !line.contains(ref.name) || !line.contains(ORDINAL_MAP.get(ref.ordinal));
              }
              return true;
            })
            .peek(line -> {
              if (oldTestamentHeadingCount.get() < 2 && bookName.get() == null) {
                bookName.set(line);
              }
            })
            .skip(1)
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .dropWhile(line -> {
              if (oldTestamentHeadingCount.get() == 2) {
                return true;
              }
              if (!books.containsKey(line)) {
                if (!line.equals(oldTestamentHeading)) {
                  books.put(line, 1);
                  return true;
                }
                oldTestamentHeadingCount.incrementAndGet();
                return false;
              }
              return true;
            })
            .skip(1)
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .dropWhile(line -> {
              if (!books.containsKey(line)) {
                return books.get(bookName.get()) != 2;
              }
              books.computeIfPresent(line, (k, v) -> v + 1);
              return books.get(bookName.get()) != 2 && books.get(line) == 2;
            })
            .skip(1)
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .takeWhile(line -> {
              if (endOfBible.equals(line)) {
                return true;
              }
              if (!books.containsKey(line)) {
                return true;
              }
              return line.equals(bookName.get());
            })
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .dropWhile(line -> {
              Matcher matcher = exactMatchPattern.matcher(line);
              return !matcher.find();
            })
            .map(String::trim)
            .filter(line -> !line.isEmpty())
            .collect(Collectors.joining());
        Matcher matcher = pattern.matcher(verses);
        int prev = 0;
        while (matcher.find()) {

        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } finally {

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
