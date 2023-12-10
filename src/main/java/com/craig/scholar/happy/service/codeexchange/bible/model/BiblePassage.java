package com.craig.scholar.happy.service.codeexchange.bible.model;

public record BiblePassage(String chapter, String verse, String text) {

  public String getChapterAndVerse() {
    return String.join(":", chapter, verse);
  }
}
