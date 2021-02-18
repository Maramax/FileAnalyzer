package net.testlab.io;

import java.util.List;

/**
 * Instances of this class represent the result of files analyzing
 * for presence of sentences containing the specific word
 */
public class Result {

    private int wordCount;
    private List<String> sentences;

    /**
     * Constructs an instance represents result of a text analyzing.
     * Calculates the number of occurring of a specific word in the text
     * and a list of sentences with this word.
     *
     * @param builder - builder for construction of Result object
     */
    private Result(ResultBuilder builder) {
        this.sentences = builder.sentences;
        this.wordCount = builder.wordCount;
    }


    public int getWordCount() {
        return wordCount;
    }

    public List<String> getSentences() {
        return sentences;
    }

    /**
     * Overrides methods to provide correct string representation of object
     * @return string representation of the object
     */
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

    /**
     * Builder class for construction of Result objects
     */

    public static class ResultBuilder {
        private int wordCount;
        private List<String> sentences;

        /**
         * Sets value for result list of sentences
         *
         * @param sentences list of sentences
         */
        public ResultBuilder setSentences(List<String> sentences) {
            this.sentences = sentences;
            return this;
        }

        /**
         * Sets value for result counted number of word
         *
         * @param wordCount
         */
        public ResultBuilder setWordCount(int wordCount) {
            this.wordCount = wordCount;
            return this;
        }

        /**
         * Returns object built with current fields set
         *
         * @return Result object
         */
        public Result build() {
            Result result = new Result(this);
            validateResultObject(result);
            return result;
        }

        private void validateResultObject(Result result) {
            if (result.sentences == null || result.wordCount < 0) {
                throw new NullPointerException("Wrong parameters for construction Result object");
            }
        }


    }

}
