package dev.jobe.platform.text.core.wrapper;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum Delimiter {
    AMPERSAND("&"),
    BACK_SLASH("\\"),
    SPACE(" "),
    COMMA(","),
    COLON(":"),
    EQUAL_SIGN("="),
    FORWARD_SLASH("/"),
    HYPHEN("-"),
    PERIOD("."),
    PIPE("|"),
    QUESTION_MARK("?"),
    SEMI_COLON(";"),
    UNDERSCORE("_"),

    OPEN_CURLY("{"),
    CLOSE_CURLY("}"),
    OPEN_PARENTHESES("("),
    CLOSE_PARENTHESES(")"),
    OPEN_SQUARE("["),
    CLOSE_SQUARE("]");

    private final String value;

    Delimiter(final String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public static final List<String> DELIMITERS = Arrays
        .stream(Delimiter.values())
        .map(Delimiter::value)
        .toList();

    public static boolean isDelimiter(final String s) {
        return DELIMITERS.contains(s);
    }

    public static boolean isDelimiter(final char c) {
        return isDelimiter(String.valueOf(c));
    }

    public static Pattern delimiterPattern(final String s) {
        return delimiterPattern(valueOf(s));
    }

    public static Pattern delimiterPattern(final Delimiter delimiter) {
        var s = delimiter.value();
        return Pattern.compile(SENSITIVE_DELIMITERS.contains(s) ? BACK_SLASH.value.concat(s) : s);
    }

    private static final List<String> SENSITIVE_DELIMITERS = Stream.of(BACK_SLASH,
            PERIOD,
            PIPE,
            QUESTION_MARK,
            OPEN_CURLY,
            OPEN_SQUARE,
            OPEN_PARENTHESES,
            CLOSE_PARENTHESES)
        .map(Delimiter::value)
        .toList();
}

