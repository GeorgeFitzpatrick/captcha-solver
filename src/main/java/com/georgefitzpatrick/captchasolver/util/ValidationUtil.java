package com.georgefitzpatrick.captchasolver.util;

public class ValidationUtil {

    private ValidationUtil() {

    }

    public static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

}
