package com.craig.scholar.happy.trie;

import java.util.*;
import java.util.regex.Pattern;

public class Trie {

    private final TrieNode root = new TrieNode();
    private int numberOfWords;

    private static final Pattern WORD_PATTERN = Pattern.compile("[a-z]+");

    public boolean addWord(String w) {
        if (w == null) return false;
        w = w.toLowerCase().trim();
        if (!WORD_PATTERN.matcher(w).matches()) return false;
        TrieNode trieNode = root;
        for (int i = 0; i < w.length(); i++) {
            char c = w.charAt(i);
            if (!trieNode.nextLetterExist(c)) {
                trieNode.setNextLetter(c);
            }
            trieNode = trieNode.getNextLetter(c);
        }
        trieNode.endWord = true;
        numberOfWords++;
        return true;
    }

    public boolean foundWord(String w) {
        return findNode(w)
                .map(trieNode -> trieNode.endWord)
                .orElse(false);
    }

    public boolean deleteWord(String w) {
        return deleteWord(w, -1, root).isPresent();
    }

    private Optional<Boolean> deleteWord(String w, int i, TrieNode trieNode) {
        if (trieNode == null || i == w.length() - 1
                && !trieNode.endWord) return Optional.empty();
        if (i == w.length() - 1) {
            numberOfWords--;
            trieNode.endWord = false;
            return Optional.of(trieNode.isEmptyNode());
        }
        int j = i + 1;
        return deleteWord(w, j, trieNode.getNextLetter(w.charAt(j)))
                .map(isDeleted -> {
                    if (!isDeleted) return false;
                    trieNode.removeNextLetter(w.charAt(j));
                    return trieNode.isEmptyNode();
                });
    }

    public List<String> startsWith(String w) {
        return findNode(w)
                .map(trieNode -> startsWith(trieNode, w))
                .orElse(new ArrayList<>());
    }

    private List<String> startsWith(TrieNode trieNode, String w) {
        List<String> words = new ArrayList<>();
        if (trieNode == null) return words;
        if (trieNode.endWord) {
            words.add(w);
        }
        for (TrieNode n : trieNode.getLetters()) {
            if (n != null) {
                words.addAll(startsWith(n, w + n.value));
            }
        }
        return words;
    }

    public List<String> getAllWords() {
        return startsWith(root, "");
    }

    private Optional<TrieNode> findNode(String w) {
        TrieNode trieNode = root;
        for (int i = 0; i < w.length(); i++) {
            trieNode = trieNode.getNextLetter(w.charAt(i));
            if (trieNode == null) return Optional.empty();
        }
        return Optional.of(trieNode);
    }

    public int getNumberOfWords() {
        return calculateNumberOfWords(root);
    }

    public int calculateNumberOfWords(TrieNode root) {
        if (root == null) return 0;
        int s = root.endWord ? 1 : 0;
        for (TrieNode n : root.getLetters()) {
            s += calculateNumberOfWords(n);
        }
        return s;
    }

    public static class TrieNode {

        private String value;
        private final Map<String, TrieNode> letters = new HashMap<>();
        private boolean endWord;

        public TrieNode(char value) {
            this.value = value + "";
        }

        public TrieNode() {
        }

        public boolean nextLetterExist(char c) {
            return this.letters.containsKey(c+"");
        }

        public TrieNode getNextLetter(char c) {
           return this.letters.get(c+"");
        }

        public void setNextLetter(char c) {
            this.letters.put(c+"", new TrieNode(c));
        }

        public void removeNextLetter(char c) {
            this.letters.remove(c+"");
        }

        private boolean hasNoNextLetters() {
            return this.letters.isEmpty();
        }

        public boolean isEmptyNode() {
            return !this.endWord && hasNoNextLetters();
        }

        public Collection<TrieNode> getLetters() {
            return this.letters.values();
        }

    }
}
