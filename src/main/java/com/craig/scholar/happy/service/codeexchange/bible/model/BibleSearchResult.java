package com.craig.scholar.happy.service.codeexchange.bible.model;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleSearchResult {

  private static final Pattern BIBLE_SEARCH_RESULT_PATTERN = Pattern.compile(
      "(?<=(\\s|\\n|^|\\z)(?<chapter>\\d{1,4})(\\s|\\n)?:(\\s|\\n)?(?<verse>\\d{1,4})(\\s|\\n|\\z|^))(?<passage>[\\s\\S]*?)(?=((\\s|\\n|\\z|^)\\d+(\\s|\\n)?:(\\s\\n)?\\d+(\\s|\\n|\\z|^))|\\z)");

  public static final String CHAPTER = "chapter";
  public static final String VERSE = "verse";
  public static final String PASSAGE = "passage";

  private final List<BiblePassage> biblePassages;

  @Builder
  public BibleSearchResult(String text) {
    Matcher matcher = BIBLE_SEARCH_RESULT_PATTERN.matcher(text);
    this.biblePassages = matcher.results()
        .map(matchResult -> new BiblePassage(matchResult.group(CHAPTER),
            matchResult.group(VERSE), getPassage(matchResult.group(PASSAGE))))
        .collect(Collectors.toList());
  }

  private String getPassage(String passage) {
    passage = passage.trim();
    passage = passage.replaceAll("\\R", " ");
    passage = passage.replaceAll("\\s+", " ");
    return passage;
  }


}
