package com.craig.trie;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class DemoTrie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        try {
            Path path = Paths.get(Objects.requireNonNull(DemoTrie.class.getClassLoader()
                    .getResource("com/craig/trie/dictionary.txt")).toURI());
            Instant start = Instant.now();
            System.out.println(Files.lines(path)
                    .allMatch(trie::addWord));
            Instant end = Instant.now();
            System.out.printf("Time taken to add all words: %s ms.\n", Duration.between(start, end).toMillis());
            start = Instant.now();
            System.out.println(Files.lines(path)
                    .allMatch(trie::foundWord));
            end = Instant.now();
            System.out.printf("Time taken to find all words: %s ms.\n", Duration.between(start, end).toMillis());
            System.out.println(trie.getNumberOfWords());
            System.out.println(trie.startsWith("ja"));
            start = Instant.now();
            System.out.println(Files.lines(path)
                    .allMatch(trie::deleteWord));
            end = Instant.now();
            System.out.printf("Time taken to delete all words: %s ms.\n", Duration.between(start, end).toMillis());
            System.out.println(trie.getNumberOfWords());
            trie.addWord("cat");
            System.out.println(trie.deleteWord("but"));
//            System.out.println(trie.getAllWords());
//            System.out.println(trie.startsWith("ab"));
//            Files.lines(path)
//                    .forEach(trie::deleteWordV2);
//            System.out.println(Files.lines(path)
//                    .noneMatch(trie::foundWord));
//            System.out.println(trie.getNumberOfWords());
//            List<String> l = List.of("hit");
//            l.forEach(trie::addWord);
//            System.out.println(l.stream().allMatch(trie::foundWord));
//            System.out.println(trie.deleteWordV2("hit"));
//            System.out.println(trie.foundWord("hit"));
//            System.out.println(l.stream().filter(t -> !t.equals("hit")).allMatch(trie::foundWord));
//            System.out.println(trie.getNumberOfWords());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
