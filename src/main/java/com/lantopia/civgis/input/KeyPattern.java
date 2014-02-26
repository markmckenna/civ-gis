package com.lantopia.civgis.input;

/**
 * @author Mark McKenna %lt;mark.denis.mckenna@gmail.com>
 * @version 0.1
 * @since 24/02/14
 *
 * Defines a singly pressable key pattern.
 */
public class KeyPattern {
    private final String pattern;

    KeyPattern(final String pattern) {
        this.pattern = pattern;
    }

    public static KeyPattern make(final String pattern) {
        return new KeyPattern(pattern);
    }
}
