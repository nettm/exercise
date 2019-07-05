package com.nettm.exercise.sync.database;

public class MetadataConfigException extends RuntimeException {

    public MetadataConfigException() {
        super();
    }

    public MetadataConfigException(String message) {
        super(message);
    }

    public MetadataConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public MetadataConfigException(Throwable cause) {
        super(cause);
    }
}
