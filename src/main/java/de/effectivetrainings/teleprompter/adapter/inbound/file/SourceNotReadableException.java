package de.effectivetrainings.teleprompter.adapter.inbound.file;

public class SourceNotReadableException extends RuntimeException {

    public SourceNotReadableException(String message) {
        super(message);
    }

    public SourceNotReadableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceNotReadableException(Throwable cause) {
        super(cause);
    }
}
