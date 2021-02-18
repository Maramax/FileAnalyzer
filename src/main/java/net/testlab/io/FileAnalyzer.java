package net.testlab.io;

import java.io.*;
import java.nio.file.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class FileAnalyzer {
    /**
     * Open the file by its path name and analyzes its content
     * for presence of a specific word. Returns Result object
     * with number of occurrence of this word in the text and a list
     * of sentences containing the word.
     *
     * @param path - path to the file to be analyzed
     * @param word - word to find the sentences with it
     * @return Result object with a list of sentences containing
     * the word provided and the number of those sentences
     * @throws IOException if IOException occurs during processing the file
     */
    public Result analyze(String path, String word) throws IOException {
        validateParameters(path, word);
        String text = getTextFromFile(path);
        List<String> allSentences = getSentencesFromText(text);
        return getResultFromSentences(allSentences, word);

    }

    /**
     * Open the file by its path name and analyzes its content
     * for presence of a specific word. Prints number of occurrence
     * of this word in the text and a list of sentences containing the word.
     *
     * @param path - path to the file to be analyzed
     * @param word - word to find the sentences with it
     * @throws IOException if IOException occurs during processing the file
     */
    public void analyzeAndPrint(String path, String word) throws IOException {
        Result result = this.analyze(path, word);
        System.out.println(result.toString());
    }

    /**
     * Open the file by its path name and analyzes its content
     * for presence of a specific word. Save to the log file number of occurrence
     * of this word in the text and a list of sentences containing the word.
     *
     * @param filePath - path to the file to be analyzed
     * @param logPath  - path to the log file to save the result
     * @param word     - word to find the sentences with it
     * @throws IOException if IOException occurs during processing the file
     */
    public void analyzeAndSaveLog(String filePath, String logPath, String word) {
    }

    /**
     * Validates the parameters path and word that should be null or empty.
     * File located by path should exist.
     *
     * @param path - path to the file to be analyzed
     * @param word - word to find the sentences with it
     */
    private void validateParameters(String path, String word) {
        if (path == null || path.isEmpty() || word == null || word.isEmpty() || !new File(path).exists()) {
            throw new IllegalArgumentException("Parameters 'path' and 'word' cannot to be null or empty");
        }
    }

    /**
     * Splits text by sentence BreakIterator and return list of sentences
     *
     * @param text - initial text to be split into sentences
     * @return sentences as list of Strings
     */
    private List<String> getSentencesFromText(String text) {
        List<String> sentenceList = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance();
        iterator.setText(text);
        int start = iterator.first();
        int end = iterator.next();
        while (end != BreakIterator.DONE) {
            sentenceList.add(text.substring(start, end).trim());
            start = end;
            end = iterator.next();
        }
        return sentenceList;
    }

    /**
     * Iterates a list of sentences, collects sentences containing
     * the word specified and counts total number of the word
     * in the sentences.
     *
     * @param list - list of sentences
     * @param word - word to be searched
     * @return Result instance with list of sentences containing the word
     */
    private Result getResultFromSentences(List<String> list, String word) {
        int totalWordCount = 0;
        List<String> sentencesWithWord = new ArrayList<>();
        for (String sentence : list) {
            int wordCount = countWordsInSentence(sentence, word);
            if (wordCount > 0) {
                sentencesWithWord.add(sentence);
                totalWordCount += wordCount;
            }
        }

        Result result = new Result.ResultBuilder()
                .setSentences(sentencesWithWord)
                .setWordCount(totalWordCount)
                .build();

        return result;
    }

    /**
     * Counts number of a word in a sentence
     *
     * @param sentence     - sentence to be checked for containing the word
     * @param searchedWord - the word to be searched in the sentence
     * @return true if the sentence contains the word
     */
    private int countWordsInSentence(String sentence, String searchedWord) {
        int count = 0;
        BreakIterator iterator = BreakIterator.getWordInstance();
        iterator.setText(sentence);
        int start = iterator.first();
        int end = iterator.next();
        while (end != BreakIterator.DONE) {
            String word = sentence.substring(start, end);
            if (word.equalsIgnoreCase(searchedWord)) {
                count++;
            }
            start = end;
            end = iterator.next();
        }
        return count;
    }

    /**
     * Reads the file located at the path indicated and returns its content as string
     *
     * @param path - path to the file to be read
     * @return content of file
     * @throws IOException
     */
    private String getTextFromFile(String path) throws IOException {

        File file = new File(path);
        long fileSize = file.length();
        byte[] textBuffer = new byte[(int) fileSize];
        try (BufferedInputStream input = new BufferedInputStream(new FileInputStream(file))) {
            input.read(textBuffer);
        } catch (IOException e) {
            throw new IOException("Cannot read text from file");
        }
        return new String(textBuffer);
    }


}


