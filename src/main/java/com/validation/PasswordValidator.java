package com.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidator {
    // At least 8 chars, one upper, one lower, one digit, one special
    private static final Pattern VALID_PASSWORD =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\p{Punct}).{8,}$");

    public boolean isValid(String rawPassword) {
        if (rawPassword == null || !VALID_PASSWORD.matcher(rawPassword).matches())
            return false;
        return true;
    }
}
