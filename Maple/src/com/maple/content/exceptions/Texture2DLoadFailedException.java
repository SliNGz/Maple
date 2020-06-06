package com.maple.content.exceptions;

public class Texture2DLoadFailedException extends RuntimeException {
    public Texture2DLoadFailedException(Throwable throwable) {
        super(throwable);
    }
}
