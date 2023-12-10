package com.craig.scholar.happy.service.codeexchange.bible.model;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BibleSearchQuery {

  public static Optional<BibleSearchResult> getBibleSearchResult(BibleReference bibleReference,
      String text) {
    Pattern textMatchPattern = bibleReference.getPattern();
    Matcher textMatcher = textMatchPattern.matcher(text);
    if (textMatcher.find()) {
      return Optional.of(BibleSearchResult.builder()
          .text(textMatcher.group("passage"))
          .build());
    }
    return Optional.empty();
  }

}
