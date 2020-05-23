package com.maple.graphics.shader.exceptions;

public class ShaderLoadFailedException extends RuntimeException {
    public ShaderLoadFailedException(Throwable throwable) {
        super(throwable);
    }
}
