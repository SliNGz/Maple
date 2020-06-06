package com.maple.content;

public interface IContentLoader<T> {
    T load(String path);

    void unload(T content);
}
