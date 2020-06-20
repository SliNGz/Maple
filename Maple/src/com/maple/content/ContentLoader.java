package com.maple.content;

import com.maple.content.exceptions.ContentTypeNotSupportedException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentLoader {
    private File mContentFolder;
    private Map<Class<?>, IContentLoader<?>> mContentLoaders;
    private Map<Class<?>, List<?>> mLoadedContent;

    public ContentLoader(File contentFolder) {
        mContentFolder = contentFolder;

        mContentLoaders = new HashMap<>();
        mLoadedContent = new HashMap<>();
    }

    public void addContentLoader(Class<?> type, IContentLoader<?> contentLoader) {
        mContentLoaders.put(type, contentLoader);
        mLoadedContent.put(type, new ArrayList<>());
    }

    public <T> T load(Class<T> type, String path) {
        IContentLoader<?> contentLoader = mContentLoaders.get(type);
        if (contentLoader == null) {
            throw new ContentTypeNotSupportedException();
        }

        T content = (T) contentLoader.load(mContentFolder.getAbsolutePath() + '\\' + path);

        List<T> contentList = (List<T>) mLoadedContent.get(type);
        contentList.add(content);

        return content;
    }

    public <T> void cleanup() {
        for (Map.Entry<Class<?>, List<?>> entry : mLoadedContent.entrySet()) {
            Class<?> type = entry.getKey();
            List<T> loadedContentList = (List<T>) entry.getValue();

            IContentLoader<T> contentLoader = (IContentLoader<T>) mContentLoaders.get(type);
            for (T content : loadedContentList) {
                contentLoader.unload(content);
            }
        }
    }
}
