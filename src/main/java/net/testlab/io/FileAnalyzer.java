package net.testlab.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FileAnalyzer {
    /**
     * Open the file by its path name and analyzes its content
     * for presence of sentences with a specific word. Returns Result object
     * with a list of such sentences and their number.
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
        List<String> sentencesWithWord = filterSentencesByContainingWord(allSentences, word);
        return new Result(sentencesWithWord);
    }

    /**
     * Open the file by its path name and analyzes its content
     * for presence of sentences with a specific word. Prints to console
     * a list of such sentences and their number.
     *
     * @param path - path to the file to be analyzed
     * @param word - word to find the sentences with it
     * @throws IOException if IOException occurs during processing the file
     */
    public void analyzeAndPrint(String path, String word) throws IOException {
        Result result = this.analyze(path, word);
        int count = result.getSentenceCount();
        printCount(word, count);
        if (count > 0) {
            printList(result.getSentences());
        }
    }

    /**
     * Open the file by its path name and analyzes its content
     * for presence of sentences with a specific word. Save to the
     * log file a list of such sentences and their number.
     *
     * @param filePath - path to the file to be analyzed
     * @param logPath  - path to the log file to save the result
     * @param word     - word to find the sentences with it
     * @throws IOException if IOException occurs during processing the file
     */
    public void analyzeAndSaveLog(String filePath, String logPath, String word) {
    }

    /**
     * Validates the parameters path and word that should be null or empty
     *
     * @param path - path to the file to be analyzed
     * @param word - word to find the sentences with it
     */
    private void validateParameters(String path, String word) {
        if (path == null || path.equals("") || word == null || word.equals(""))
            throw new IllegalArgumentException("Parameters 'path' and 'word' cannot to be null or empty");
    }

    /**
     * Splits text by sentence BreakIterator and return list of sentences
     *
     * @param text - initial text to be split into sentences
     * @return sentences as list of Strings
     */
    private List<String> getSentencesFromText(String text) {
        List<String> sentenceList = new ArrayList<>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        for (int start = iterator.first(), end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            sentenceList.add(text.substring(start, end).trim());
        }
        return sentenceList;
    }

    /**
     * Filters list of sentences maintaining only the sentences containing
     * the word provided
     *
     * @param list - list of sentences
     * @param word - word that a sentence must contain
     * @return list of sentences containing the word
     */
    private List<String> filterSentencesByContainingWord(List<String> list, String word) {
        return list.stream()
                .filter(str -> checkIfContainsWord(str, word))
                .collect(Collectors.toList());
    }

    /**
     * Checks whether the sentence contains the word provided or not
     *
     * @param sentence - sentence to be checked for containing the word
     * @param word     - the word to be searched in the sentence
     * @return true if the sentence contains the word
     */
    private boolean checkIfContainsWord(String sentence, String word) {
        BreakIterator iterator = BreakIterator.getWordInstance(Locale.US);
        iterator.setText(sentence);
        for (int start = iterator.first(), end = iterator.next();
             end != BreakIterator.DONE;
             start = end, end = iterator.next()) {
            String s = (sentence.substring(start, end));
            if (s.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads the file located at the path indicated and returns its content as string
     *
     * @param path - path to the file to be read
     * @return content of file
     * @throws IOException
     */
    private String getTextFromFile(String path) throws IOException {

        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            int c;
            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }
        } catch (NullPointerException e) {
            throw new IOException("Path to file is not indicated");
        } catch (AccessDeniedException e) {
            throw new IOException("Access to file denied");
        } catch (NoSuchFileException e) {
            throw new IOException("Cannot find pathname: " + path);
        } catch (IOException e) {
            throw new IOException("Cannot read text from file");
        }
        return builder.toString();
    }

    /**
     * Prints to console line with word and number of sentences with this word
     *
     * @param word  - word that was used for search
     * @param count - number of sentences found
     */
    private void printCount(String word, int count) {
        System.out.println("Number of sentences containing word '" + word + "': " + count);
    }

    /**
     * Prints to console lines of sentences from list
     *
     * @param list - list of sentences
     */
    private void printList(List<String> list) {
        System.out.println("List of sentences:");
        for (String s : list) {
            System.out.println(" - " + s);
        }
    }
}


