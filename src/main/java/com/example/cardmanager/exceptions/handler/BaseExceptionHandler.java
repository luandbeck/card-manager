package com.example.cardmanager.exceptions.handler;

import com.example.cardmanager.config.log.StructuredLog;
import com.example.cardmanager.exceptions.BaseBusinessException;
import com.example.cardmanager.exceptions.domain.DefaultErrorResponse;
import com.example.cardmanager.exceptions.domain.StandardError;
import com.example.cardmanager.exceptions.enums.Errors;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

/**
 * Responsible to create an simple structure to register all application exceptions.
 * <p>
 * Default errors are recorded into class constructor and the known application error into
 * {@link ControllerExceptionHandler}
 */
public abstract class BaseExceptionHandler {

    private static final StandardError DEFAULT_ERROR = StandardError.builder()
            .status(INTERNAL_SERVER_ERROR.value())
            .printStack(Boolean.TRUE)
            .timestamp(new Timestamp(System.currentTimeMillis()))
            .build();

    private final Logger log;
    private final Map<Class, StandardError> exceptionMappings = new HashMap<>();

    BaseExceptionHandler(final Logger log) {
        this.log = log;

        //400
        this.registerMapping(ServletRequestBindingException.class, BAD_REQUEST, Boolean.TRUE);
        this.registerMapping(HttpMessageNotReadableException.class, BAD_REQUEST, Boolean.TRUE);
        this.registerMapping(MethodArgumentTypeMismatchException.class, BAD_REQUEST, Boolean.TRUE);
        this.registerMapping(MissingServletRequestParameterException.class, BAD_REQUEST, Boolean.TRUE);

        //405
        this.registerMapping(HttpRequestMethodNotSupportedException.class, METHOD_NOT_ALLOWED, Boolean.TRUE);

        //415
        this.registerMapping(HttpMediaTypeException.class, UNSUPPORTED_MEDIA_TYPE, Boolean.TRUE);
    }

    /**
     * Responsible to find into exceptionMappings the error of that exception.
     * If the exception received does not found into mapping, an default error (INTERNAL_SERVER_ERROR)
     * will be returned to request.
     * If the exception received found into mapping, a error registered into {@link ControllerExceptionHandler}
     * will be returned to request.
     *
     * @param ex      Exception that will be found into mapping
     * @param request Object request that happened the exception
     * @return An known message response to request
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity handlerException(final Throwable ex, final HttpServletRequest request) {
        final StandardError mapping = exceptionMappings.getOrDefault(ex.getClass(), DEFAULT_ERROR);

        final DefaultErrorResponse defaultErrorResponse = DefaultErrorResponse.builder()
                .code(Errors.CAR001.name())
                .message(Errors.CAR001.getMessage(request.getLocale()))
                .build();

        final StandardError error = StandardError.builder()
                .error(defaultErrorResponse)
                .status(mapping.getStatus())
                .path(request.getRequestURI())
                .printStack(mapping.isPrintStack())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        if (mapping.isPrintStack()) {
            StructuredLog.builder().exception(ex.getClass());
            log.error("Exception caught", ex);
            StructuredLog.remove().exception();
        }

        return ResponseEntity
                .status(mapping.getStatus())
                .body(error);
    }

    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<StandardError> handlerBusinessException(final BaseBusinessException bbe,
                                                                  final HttpServletRequest request) {
        if (bbe.isPrintStack()) {
            StructuredLog.builder().exception(bbe.getClass());
            log.error("Exception caught", bbe);
            StructuredLog.remove().exception();
        }

        return ResponseEntity
                .status(bbe.getStatus())
                .body(bbe.getStandardError(request.getRequestURI(), request.getLocale()));
    }

    private void registerMapping(
            final Class<?> clazz,
            final HttpStatus status,
            final Boolean printStack) {

        exceptionMappings.put(clazz, StandardError.builder()
                .status(status.value())
                .printStack(printStack)
                .build());
    }
}
