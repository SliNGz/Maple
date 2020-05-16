package com.maple.renderer.exceptions;

public class RenderingFailedException extends RuntimeException {
    public RenderingFailedException(Throwable throwable) {
        super(throwable);
    }
}
