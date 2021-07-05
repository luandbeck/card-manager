package com.example.cardmanager.exceptions.enums;

import com.example.cardmanager.config.log.StructuredLog;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public enum Errors {
    CAR001,
    CAR002,
    CAR003;


    public String getMessage(final Locale messageLocale) {
        final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages/exceptions", messageLocale);

        try {
            return new String(resourceBundle.getString(this.name() + ".message").getBytes(StandardCharsets.ISO_8859_1.name()), StandardCharsets.UTF_8.name());
        } catch (final UnsupportedEncodingException ex) {
            StructuredLog.builder().exception(ex.getClass());
        }

        return resourceBundle.getString(this.name() + ".message");
    }
}
