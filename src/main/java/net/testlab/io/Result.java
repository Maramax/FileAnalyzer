package net.testlab.io;

import java.util.List;

/**
 * Instances of this class represent the result of files analyzing
 * for presence of sentences containing the specific word
 */
public class Result {
    List<String> sentences;
    private int sentenceCount;

    /**
     * Constructs an instance with list of sentences.
     * Calculates the number of the sentences.
     *
     * @param sentences - list of sentences
     */
    public Result(List<String> sentences) {
        if (sentences == null) {
            throw new NullPointerException("Parameter sentences is null");
        }
        this.sentences = sentences;
        this.sentenceCount = sentences.size();
    }


    public List<String> getSentences() {
        return sentences;
    }

    public void setSentences(List<String> sentences) {
        this.sentences = sentences;
    }

    public int getSentenceCount() {
        return sentenceCount;
    }

    public void setSentenceCount(int sentenceCount) {
        this.sentenceCount = sentenceCount;
    }
}
