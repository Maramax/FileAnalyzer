package net.testlab.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class FileAnalyzerTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Test
    @DisplayName("Analyze if sentences in the file contain 'word'")
    void analyzeIfContainWord() throws IOException {
        Result result = new FileAnalyzer().analyze("test.txt", "word");
        String[] expectedArray = {
                "Word.",
                "Test with word.",
                "Test with two word word.",
                "Test with WORD in upper case.",
                "Test with word, and commas.",
                "Test with word question mark?"};
        assertAll(
                () -> assertEquals(6, result.getSentenceCount()),
                () -> assertArrayEquals(expectedArray, result.getSentences().toArray())
        );
    }

    @Test
    @DisplayName("Analyze if sentences in the file contain 'two'")
    void analyzeIfContainTwo() throws IOException {
        Result result = new FileAnalyzer().analyze("test.txt", "two");
        String[] expectedArray = {
                "Test with two word word."};
        assertAll(
                () -> assertEquals(1, result.getSentenceCount()),
                () -> assertArrayEquals(expectedArray, result.getSentences().toArray())
        );
    }

    @Test
    @DisplayName("Analyze if sentences in the file contain 'somethingelse'")
    void analyzeIfContainSomethingelse() throws IOException {
        Result result = new FileAnalyzer().analyze("test.txt", "somethingesle");
        String[] expectedArray = {};
        assertAll(
                () -> assertEquals(0, result.getSentenceCount()),
                () -> assertArrayEquals(expectedArray, result.getSentences().toArray())
        );
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'word'")
    void analyzeAndPrintIfContainWord() throws IOException {
        System.setOut(new PrintStream(outputStreamCaptor));
        new FileAnalyzer().analyzeAndPrint("test.txt", "word");
        String expectedText = "Number of sentences containing word 'word': 6\r\n" +
                "List of sentences:\r\n" +
                " - Word.\r\n" +
                " - Test with word.\r\n" +
                " - Test with two word word.\r\n" +
                " - Test with WORD in upper case.\r\n" +
                " - Test with word, and commas.\r\n" +
                " - Test with word question mark?";
        System.setOut(originalOut);
        assertEquals(expectedText, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'two'")
    void analyzeAndPrintIfContainTwo() throws IOException {
        System.setOut(new PrintStream(outputStreamCaptor));
        new FileAnalyzer().analyzeAndPrint("test.txt", "two");
        String expectedText = "Number of sentences containing word 'two': 1\r\n" +
                "List of sentences:\r\n" +
                " - Test with two word word.";
        System.setOut(originalOut);
        assertEquals(expectedText, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Analyze and print if sentences in the file contain 'somethingelse'")
    void analyzeAndPrintIfContainSomethingelse() throws IOException {
        System.setOut(new PrintStream(outputStreamCaptor));
        new FileAnalyzer().analyzeAndPrint("test.txt", "somethingesle");
        String expectedText = "Number of sentences containing word 'somethingesle': 0";
        System.setOut(originalOut);
        assertEquals(expectedText, outputStreamCaptor.toString().trim());
    }

    @Test
    @DisplayName("Try to analyze with null or empty parameters")
    void exceptionIllegalArguments() {
        assertAll( //
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new FileAnalyzer().analyze(null, "word");
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new FileAnalyzer().analyze("", "word");
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new FileAnalyzer().analyze("test.txt", null);
                }),
                () -> assertThrows(IllegalArgumentException.class, () -> {
                    new FileAnalyzer().analyze("test.txt", "");
                })
        );
    }

    @Test
    @DisplayName("Try to analyze with wrong path")
    void exceptionWrongPath() {
        assertThrows(IOException.class, () -> {
            new FileAnalyzer().analyze("nosuchfile.txt", "word");
        });
    }

    @Test
    @DisplayName("Try to analyze with no access to file")
    void exceptionNotAccessedPath() {
        assertThrows(IOException.class, () -> {
            new FileAnalyzer().analyze(".", "word");
        });
    }

}