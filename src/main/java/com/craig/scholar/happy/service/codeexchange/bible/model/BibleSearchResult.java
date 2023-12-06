package com.craig.scholar.happy.service.codeexchange.bible.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleSearchResult {

  private static final Pattern BIBLE_SEARCH_RESULT_PATTERN = Pattern.compile(
      "(?<=(\\s|\\n|^|\\z)?(?<chapter>\\d{1,4})(\\s|\\n)?:(\\s|\\n)?(?<verse>\\d{1,4})(\\s|\\n|\\z|^))(?<passage>[\\s\\S]*?)(?=((\\s|\\n|\\z|^)\\d+(\\s|\\n)?:(\\s\\n)?\\d+(\\s|\\n|\\z|^))|\\z)");

  private final List<BiblePassage> biblePassages;

  @Builder
  public BibleSearchResult(String text) {
    Matcher matcher = BIBLE_SEARCH_RESULT_PATTERN.matcher(text);
    List<BiblePassage> passages = new ArrayList<>();
    while (matcher.find()) {
      BiblePassage biblePassage = new BiblePassage(matcher.group("chapter"), matcher.group("verse"),
          matcher.group("passage"));
      passages.add(biblePassage);
    }
    this.biblePassages = passages;
  }


}
