package com.maple.content.loaders.model.exceptions;

public class ModelLoadFailedException extends RuntimeException {
    public ModelLoadFailedException(Throwable throwable) {
        super(throwable);
    }
}
