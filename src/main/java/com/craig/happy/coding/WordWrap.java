package com.craig.happy.coding;

public class WordWrap {

    public String execute(String word, int column) {
        StringBuilder wordWrapBuilder = new StringBuilder();
        int lineColumnCount = 0;
        int numberOfWhiteSpaces = -1;
        for (int i = 0; i < word.length(); i++) {
            if (lineColumnCount + 1 == column) {
                if (isMoveCurrentWordToNewLine(word, i, numberOfWhiteSpaces)) {
                    wordWrapBuilder.append(word.charAt(i));
                    wordWrapBuilder.setCharAt(numberOfWhiteSpaces, '\n');
                    lineColumnCount = i - numberOfWhiteSpaces;
                    numberOfWhiteSpaces = -1;
                } else {
                    if (!Character.isWhitespace(word.charAt(i))) {
                        wordWrapBuilder.append(word.charAt(i));
                    }
                    lineColumnCount = 0;
                    numberOfWhiteSpaces = -1;
                    if (i != word.length() - 1) {
                        wordWrapBuilder.append("\n");
                    }
                }

            } else {
                if (doAddCharacter(word, i, lineColumnCount)) {
                    wordWrapBuilder.append(word.charAt(i));
                    lineColumnCount++;
                    if (Character.isWhitespace(word.charAt(i))) {
                        numberOfWhiteSpaces = i;
                    }
                }
            }
        }
        return wordWrapBuilder.toString();
    }

    private boolean isMoveCurrentWordToNewLine(String word, int i, int numberOfWhiteSpaces) {
        return !Character.isWhitespace(word.charAt(i))
                && numberOfWhiteSpaces != -1
                && i != word.length() - 1
                && !Character.isWhitespace(word.charAt(i + 1));
    }

    private boolean doAddCharacter(String word, int i, int lineColumnCount) {
        return !Character.isWhitespace(word.charAt(i)) || lineColumnCount != 0;
    }
}
