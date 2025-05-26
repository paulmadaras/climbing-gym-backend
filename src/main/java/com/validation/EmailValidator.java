package com.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator {
    /**
     * RFC 5322 simplified email regex:
     * - local part: uppercase/lowercase letters, digits, and ._%+-
     * - “@”
     * - domain part: letters, digits, and .- , ending with a 2+ letter TLD
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Returns true if the given string is a syntactically valid email address.
     */
    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    // Prevent instantiation
    public EmailValidator() { }
}
