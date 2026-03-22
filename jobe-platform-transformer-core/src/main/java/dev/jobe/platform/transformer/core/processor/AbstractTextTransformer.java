package dev.jobe.platform.transformer.core.processor;

import org.springframework.util.Assert;

import java.util.Locale;
import java.util.Set;

public abstract class AbstractTextTransformer {

    private static final Set<Character> SENTENCE_ENDING = Set.of('.', ':', '!', '?');

    protected String asTitle(final String input) {
        validate(input);
        var sb = new StringBuilder(input.length());
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

    protected String asSentence(final String input) {
        validate(input);
        if (input.isBlank()) return input;
        var sb = new StringBuilder(input.length());
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

            if (SENTENCE_ENDING.contains(c)) {
                newSentence = true;
                while ((i + 1) < input.length() && Character.isWhitespace(input.charAt(i + 1))) {
                    i++;
                }
            }
        }
        return sb.toString().trim();
    }

    protected String asLower(final String input) {
        validate(input);
        return input.isBlank()
            ? input
            : input.trim().toLowerCase(Locale.ROOT);
    }

    protected String asUpper(final String input) {
        validate(input);
        return input.isBlank()
            ? input
            : input.trim().toUpperCase(Locale.ROOT);
    }

    private void validate(final String input) {
        Assert.notNull(input, "Input must not be null");
    }
}
