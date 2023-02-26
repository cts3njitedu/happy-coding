package com.craig.happy.coding;

public class WordWrap {

    public String execute(String word, int column) {
        StringBuilder wordBuilder = new StringBuilder();
        int lineColumnCount = 0;
        int numberOfWhiteSpaces = -1;
        for (int i = 0; i < word.length(); i++) {
            if (lineColumnCount + 1 == column) {
                if (!Character.isWhitespace(word.charAt(i))) {
                    wordBuilder.append(word.charAt(i));
                    if (isNoEmptySpacesInLine(numberOfWhiteSpaces) && isNextCharacterNotAWhiteSpace(word, i)) {
                        wordBuilder.setCharAt(numberOfWhiteSpaces, '\n');
                        lineColumnCount = i - numberOfWhiteSpaces;
                        numberOfWhiteSpaces = -1;
                    } else {
                        lineColumnCount = 0;
                        numberOfWhiteSpaces = -1;
                        if (i != word.length() - 1) {
                            wordBuilder.append("\n");
                        }
                    }
                } else {
                    lineColumnCount = 0;
                    numberOfWhiteSpaces = -1;
                    if (i != word.length() - 1) {
                        wordBuilder.append("\n");
                    }
                }

            } else {
                if (isValidCharacter(word, i, lineColumnCount)) {
                    wordBuilder.append(word.charAt(i));
                    lineColumnCount++;
                    if (Character.isWhitespace(word.charAt(i))) {
                        numberOfWhiteSpaces = i;
                    }
                }
            }
        }
        return wordBuilder.toString();
    }

    private boolean isNoEmptySpacesInLine(int s) {
        return s != -1;
    }

    private boolean isNextCharacterNotAWhiteSpace(String word, int i) {
        return i != word.length() - 1 && !Character.isWhitespace(word.charAt(i + 1));
    }

    private boolean isValidCharacter(String word, int i, int lineColumnCount) {
        return !Character.isWhitespace(word.charAt(i)) || lineColumnCount != 0;
    }
}
