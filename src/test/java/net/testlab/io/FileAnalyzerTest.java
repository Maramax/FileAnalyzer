package net.testlab.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerTest {

    private static FileAnalyzer analyzer;

    @BeforeAll
    static void setUp(){
        analyzer = new FileAnalyzer();
    }

    @Test
    @DisplayName("Analyze if sentences in the file contain 'word'")
    void analyzeIfContainWord() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "word");
        List<String> expectedList = Arrays.asList(
                "Word.",
                "Test with word.",
                "Test with two word word.",
                "Test with WORD in upper case.",
                "Test with word, and commas.",
                "Test with word question mark?");
        assertAll(
                () -> assertEquals(7, result.getWordCount()),
                () -> assertEquals(expectedList, result.getSentences())
        );
    }


    @Test
    @DisplayName("Analyze if sentences in the file contain 'two'")
    void analyzeIfContainTwo() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "two");
        List<String> expectedList = Arrays.asList(
                "Test with two word word.");
        assertAll(
                () -> assertEquals(1, result.getWordCount()),
                () -> assertEquals(expectedList, result.getSentences())
        );
    }

    @Test
    @DisplayName("Analyze if sentences in the file contain 'somethingelse'")
    void analyzeIfContainSomethingelse() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "somethingesle");
        List<String> expectedList = Arrays.asList();
        assertAll(
                () -> assertEquals(0, result.getWordCount()),
                () -> assertEquals(expectedList, result.getSentences())
        );
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'word'")
    void analyzeAndPrintIfContainWord() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "word");
        String expectedText = "Number of word in text: 7\r\n" +
                "List of sentences:\r\n" +
                " - Word.\r\n" +
                " - Test with word.\r\n" +
                " - Test with two word word.\r\n" +
                " - Test with WORD in upper case.\r\n" +
                " - Test with word, and commas.\r\n" +
                " - Test with word question mark?\r\n";
        assertEquals(expectedText, result.toString());
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'two'")
    void analyzeAndPrintIfContainTwo() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "two");
        String expectedText = "Number of word in text: 1\r\n" +
                "List of sentences:\r\n" +
                " - Test with two word word.\r\n";
        assertEquals(expectedText, result.toString());
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'somethingelse'")
    void analyzeAndPrintIfContainSomethingelse() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "somethingesle");
        String expectedText = "Number of word in text: 0\r\n";
        assertEquals(expectedText, result.toString());
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'слово'")
    void analyzeIfContainCyrillic() throws IOException {
        Result result = analyzer.analyze("src/test/resources/test.txt", "слово");
        String expectedText = "Number of word in text: 3\r\n" +
                "List of sentences:\r\n" +
                " - Слово теж шукає.\r\n" +
                " - Та знаходить слово, тут теж.\r\n" +
                " - Слово.\r\n";
        assertEquals(expectedText, result.toString());
    }


    @Test
    @DisplayName("Try to analyze with null or empty parameters")
    void exceptionIllegalArguments() {
        assertAll( //
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    analyzer.analyze(null, "word");
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    analyzer.analyze("", "word");
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    analyzer.analyze("src/test/resources/test.txt", null);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    analyzer.analyze("src/test/resources/test.txt", "");
                })
        );
    }

    @Test
    @DisplayName("Try to analyze with wrong path")
    void exceptionWrongPath() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.analyze("nosuchfile.txt", "word");
        });
    }

    @Test
    @DisplayName("Try to analyze with no access to file")
    void exceptionNotAccessedPath() {
        assertThrows(IOException.class, () -> {
            analyzer.analyze(".", "word");
        });
    }

}