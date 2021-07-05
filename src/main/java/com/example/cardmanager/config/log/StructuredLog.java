package com.example.cardmanager.config.log;

import org.slf4j.MDC;

public class StructuredLog {

    private StructuredLog() {
    }

    public static StructuredLogBuilder builder() {
        return new StructuredLogBuilder();
    }

    public static StructuredLogRemove remove() {
        return new StructuredLogRemove();
    }

    public static class StructuredLogBuilder {

        StructuredLogBuilder() {
        }

        public StructuredLogBuilder exception(final Class<? extends Throwable> ex) {
            MDC.put("exception", ex.getSimpleName());
            return this;
        }

        public StructuredLogBuilder externalErrorCode(final String errorCode) {
            MDC.put("externalErrorCode", errorCode);
            return this;
        }

        public StructuredLogBuilder externalErrorMessage(final String errorMessage) {
            MDC.put("externalErrorMessage", errorMessage);
            return this;
        }

        public StructuredLogBuilder internalErrorCode(final String errorCode) {
            MDC.put("internalErrorCode", errorCode);
            return this;
        }

        public StructuredLogBuilder internalErrorMessage(final String errorMessage) {
            MDC.put("internalErrorMessage", errorMessage);
            return this;
        }

    }

    public static class StructuredLogRemove {

        public StructuredLogRemove exception() {
            MDC.remove("exception");
            return this;
        }

        public StructuredLogRemove externalErrorCode() {
            MDC.remove("externalErrorCode");
            return this;
        }

        public StructuredLogRemove externalErrorMessage() {
            MDC.remove("externalErrorMessage");
            return this;
        }

        public StructuredLogRemove internalErrorCode() {
            MDC.remove("internalErrorCode");
            return this;
        }

        public StructuredLogRemove internalErrorMessage() {
            MDC.remove("internalErrorMessage");
            return this;
        }
        
    }
}
