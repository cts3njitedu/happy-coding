package com.craig.scholar.happy.service.codeexchange;

import static java.lang.Character.isWhitespace;

public class WordWrap {

    public String execute(String word, int column) {
        StringBuilder wordWrapBuilder = new StringBuilder();
        int currentLineColumnCount = 0;
        int numberOfWhiteSpaces = -1;
        for (int i = 0; i < word.length(); i++) {
            if (currentLineColumnCount + 1 == column) {
                if (isMoveCurrentWordToNewLine(word, i, numberOfWhiteSpaces)) {
                    wordWrapBuilder.append(word.charAt(i));
                    wordWrapBuilder.setCharAt(numberOfWhiteSpaces, '\n');
                    currentLineColumnCount = i - numberOfWhiteSpaces;
                    numberOfWhiteSpaces = -1;
                } else {
                    if (!isWhitespace(word.charAt(i))) {
                        wordWrapBuilder.append(word.charAt(i));
                    }
                    currentLineColumnCount = 0;
                    numberOfWhiteSpaces = -1;
                    if (i != word.length() - 1) {
                        wordWrapBuilder.append("\n");
                    }
                }

            } else {
                if (doAddCharacter(word, i, currentLineColumnCount)) {
                    wordWrapBuilder.append(word.charAt(i));
                    currentLineColumnCount++;
                    if (isWhitespace(word.charAt(i))) {
                        numberOfWhiteSpaces = i;
                    }
                }
            }
        }
        return wordWrapBuilder.toString();
    }

    private boolean isMoveCurrentWordToNewLine(String word, int i, int numberOfWhiteSpaces) {
        return !isWhitespace(word.charAt(i))
                && numberOfWhiteSpaces != -1
                && i != word.length() - 1
                && !isWhitespace(word.charAt(i + 1));
    }

    private boolean doAddCharacter(String word, int i, int lineColumnCount) {
        return !isWhitespace(word.charAt(i)) || lineColumnCount != 0;
    }
}
