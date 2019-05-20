package com.xenosnowfox.mediatype;

/**
 * Checked exception thrown to indicate that a string could not be parsed as a Media Type.
 *
 * @since 0.0.1
 */
public class MediaTypeSyntaxException extends Exception {

    /**
     * Holds a copy of the input string which caused the exception to be thrown.
     */
    private final String input;

    /**
     * Holds the index into the input string at which the parse error occurred.
     */
    private final int index;

    /**
     * Constructs an instance from the given input string and reason.
     * The resulting object will have an error index of -1.
     *
     * @param input
     *      The input string being parsed.
     * @param reason
     *      A string explaining why the input could not be parsed.
     * @throws NullPointerException
     *      If either the input or reason strings are {@code null}
     */
    public MediaTypeSyntaxException(final String input, final String reason) {
        this(input, reason, -1);
    }

    /**
     * Constructs an instance from the given input string, reason and error index.
     *
     * @param input
     *      The input string being parsed.
     * @param reason
     *      A string explaining why the input could not be parsed.
     * @param index
     *      The index at which the parse error occurred, or -1 if the index is not known
     * @throws NullPointerException
     *      If either the input or reason strings are {@code null}
     * @throws IllegalArgumentException
     *      If the error index is less than -1 or greater then the length of the input string.
     */
    public MediaTypeSyntaxException(final String input, final String reason, final int index) {
        super(reason);

        if (input == null) {
            throw new NullPointerException("Input string cannot be null");
        }

        if (index < -1) {
            throw new IllegalArgumentException("Index value cannot be less than -1");
        }

        if (index > input.length()) {
            throw new IllegalArgumentException("Index value cannot exceed the length of the input string");
        }

        this.input = input;
        this.index = index;
    }

    /**
     * Returns the input string which caused the exception to be thrown.
     *
     * @return
     *      The input string.
     */
    public String getInput() {
        return this.input;
    }

    /**
     * Returns an index into the input string of the position at which the parse error occurred, or {@code -1} if this position is not known.
     *
     * @return
     *      The error index.
     */
    public int getIndex() {
        return this.index;
    }
}

