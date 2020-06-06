package com.maple.content.exceptions;

public class ShaderLoadFailedException extends RuntimeException {
    public ShaderLoadFailedException(Throwable throwable) {
        super(throwable);
    }
}
