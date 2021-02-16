package net.testlab.io;

import java.util.List;

/**
 * Instances of this class represent the result of files analyzing
 * for presence of sentences containing the specific word
 */
public class Result {
    private List<String> sentences;
    private int wordCount;

    /**
     * Constructs an instance represents result of a text analyzing.
     * Calculates the number of occurring of a specific word in the text
     * and a list of sentences with this word.
     *
     * @param sentences - list of sentences
     * @param wordCount - number of word in text
     */
    public Result(List<String> sentences, int wordCount) {
        if (sentences == null) {
            throw new NullPointerException("Parameter sentences is null");
        }
        this.sentences = sentences;
        this.wordCount = wordCount;
    }


    public List<String> getSentences() {
        return sentences;
    }

    public int getWordCount() {
        return wordCount;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Number of word in text: " + wordCount + "\r\n");
        if (wordCount > 0) {
            builder.append("List of sentences:\r\n");
            for (String s : sentences) {
                builder.append(" - " + s + "\r\n");
            }
        }
        return builder.toString();
    }
}
