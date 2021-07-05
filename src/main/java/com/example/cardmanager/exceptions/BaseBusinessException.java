package com.example.cardmanager.exceptions;

import com.example.cardmanager.exceptions.domain.DefaultErrorData;
import com.example.cardmanager.exceptions.domain.DefaultErrorMessage;
import com.example.cardmanager.exceptions.domain.DefaultErrorResponse;
import com.example.cardmanager.exceptions.domain.StandardError;
import com.example.cardmanager.exceptions.enums.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@Getter
@AllArgsConstructor
public abstract class BaseBusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final HttpStatus status;
    private final Errors errors;
    private final boolean printStack;
    private final List<DefaultErrorData> error;

    public BaseBusinessException(final HttpStatus httpStatus, final Errors errors, final boolean printStack) {
        this.status = httpStatus;
        this.errors = errors;
        this.printStack = printStack;
        this.error = new ArrayList<>();
    }

    public final StandardError getStandardError(final String path, final Locale messageLocale) {
        return StandardError.builder()
                .path(path)
                .status(status.value())
                .printStack(printStack)
                .error(this.buildErrorDataList(messageLocale))
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    protected DefaultErrorResponse buildErrorDataList(final Locale messageLocale) {
        return DefaultErrorResponse.builder()
                .code(errors.name())
                .message(errors.getMessage(messageLocale))
                .build();
    }

    private DefaultErrorResponse convert(final Optional<DefaultErrorData> defaultErrorData,
                                         final Locale messageLocale) {
        if (defaultErrorData.isPresent()) {
            final DefaultErrorData errorData = defaultErrorData.get();
            final Optional<DefaultErrorMessage> errorMessage = errorData.getMessage().stream()
                    .filter(Objects::nonNull)
                    .filter(lang -> lang.getLang().equals(messageLocale.getLanguage()))
                    .findFirst();

            if (errorMessage.isPresent()) {
                return DefaultErrorResponse.builder()
                        .code(errorData.getCode())
                        .message(errorMessage.get().getMessage())
                        .build();
            } else {
                final Optional<DefaultErrorMessage> msg = errorData.getMessage().stream()
                        .filter(Objects::nonNull)
                        .filter(lang -> lang.getLang().equals("en"))
                        .findFirst();

                if (msg.isPresent()) {
                    return DefaultErrorResponse.builder()
                            .code(errorData.getCode())
                            .message(msg.get().getMessage())
                            .build();
                }
            }
        }

        return null;
    }
}