package com.craig.happy.coding;

public class WordWrap {

    public String execute(String word, int column) {
        StringBuilder wordBuilder = new StringBuilder();
        int l = 0;
        int s = -1;
        for (int i = 0; i < word.length(); i++) {
            if (l + 1 == column) {
                if (!Character.isWhitespace(word.charAt(i))) {
                    wordBuilder.append(word.charAt(i));
                    if (s != -1 && i != word.length() - 1 && !Character.isWhitespace(word.charAt(i + 1))) {
                        wordBuilder.setCharAt(s, '\n');
                        l = i - s;
                        s = -1;
                    } else {
                        l = 0;
                        s = -1;
                        if (i != word.length() - 1) {
                            wordBuilder.append("\n");
                        }
                    }
                } else {
                    l = 0;
                    s = -1;
                    if (i != word.length() - 1) {
                        wordBuilder.append("\n");
                    }
                }

            } else {
                if (!Character.isWhitespace(word.charAt(i)) || l != 0) {
                    wordBuilder.append(word.charAt(i));
                    l++;
                    if (Character.isWhitespace(word.charAt(i))) {
                        s = i;
                    }
                }
            }
        }
        return wordBuilder.toString();
    }
}
