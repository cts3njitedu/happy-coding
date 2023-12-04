package com.craig.scholar.happy.service.codeexchange.bible.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BibleBook {

  public static final String FULL_NAME = "fullName";
  public static final String ORDINAL = "ordinal";
  public static final String NAME_WITHOUT_ORDINAL = "nameWithoutOrdinal";
  private final String fullName;
  private final String ordinal;
  private final String nameWithoutOrdinal;

  private final Pattern BOOK_PATTERN = Pattern.compile(
      "^(?<fullName>((?<ordinal>[1-3]?)\\s)?(?<nameWithoutOrdinal>[A-Za-z]+))$");

  @Builder
  public BibleBook(String book) {
    Matcher matcher = BOOK_PATTERN.matcher(book);
    if (matcher.find()) {
      this.fullName = matcher.group(FULL_NAME);
      this.ordinal = matcher.group(ORDINAL);
      this.nameWithoutOrdinal = matcher.group(NAME_WITHOUT_ORDINAL);
    } else {
      this.fullName = null;
      this.ordinal = null;
      this.nameWithoutOrdinal = null;
    }
  }
}
