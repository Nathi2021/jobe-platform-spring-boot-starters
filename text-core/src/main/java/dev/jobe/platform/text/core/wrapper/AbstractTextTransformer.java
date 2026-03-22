package dev.jobe.platform.text.core.wrapper;

import org.springframework.util.Assert;

import java.util.Locale;
import java.util.Set;

public abstract class AbstractTextTransformer {

    protected String asTitleCase(final String input) {
        this.validate(input);
        StringBuilder sb = new StringBuilder(input.length());
        boolean capitalize = true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Delimiter.isDelimiter(c)) {
                sb.append(c);
                capitalize = true;
            } else if (capitalize) {
                sb.append(Character.toTitleCase(c));
                capitalize = false;
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString().trim();
    }

    protected String asSentenceCase(final String input) {
        this.validate(input);
        if (input.isBlank()) return input;

        StringBuilder sb = new StringBuilder(input.length());
        boolean newSentence = true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (newSentence && Character.isLetter(c)) {
                sb.append(Character.toUpperCase(c));
                newSentence = false;
            } else if (!newSentence && Character.isLetter(c)) {
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }

            if (this.isSentenceEnding(c)) {
                newSentence = true;
                while ((i + 1) < input.length() && Character.isWhitespace(input.charAt(i + 1))) {
                    i++;
                }
            }
        }
        return sb.toString().trim();
    }

    protected String asLowerCase(final String input) {
        this.validate(input);
        return input.isBlank()
            ? input
            : input.trim().toLowerCase(Locale.ROOT);
    }

    protected String asUpperCase(final String input) {
        this.validate(input);
        return input.isBlank()
            ? input
            : input.trim().toUpperCase(Locale.ROOT);
    }

    private void validate(final String input) {
        Assert.notNull(input, "Input must not be null");
    }

    private static final Set<Character> SENTENCE_ENDING = Set.of('.', ':', '!', '?');
    private boolean isSentenceEnding(final char c) {
        return AbstractTextTransformer.SENTENCE_ENDING.contains(c);
    }
}
