package com.maple.content;

import com.maple.content.exceptions.ContentTypeNotSupportedException;
import com.maple.content.loaders.ShaderLoader;
import com.maple.content.loaders.Texture2DLoader;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderCreator;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.texture.Texture2DCreator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class ContentLoader {
    private File mContentFolder;
    private Map<Class<?>, IContentLoader<?>> mContentLoaders;
    private Map<Class<?>, List<?>> mLoadedContent;

    public ContentLoader(File contentFolder) {
        mContentFolder = contentFolder;

        mContentLoaders = new HashMap<>();
        mContentLoaders.put(IShader.class, createShaderContentLoader());
        mContentLoaders.put(Texture2D.class, createTexture2DContentLoader());

        mLoadedContent = new HashMap<>();
        for (Class<?> type : mContentLoaders.keySet()) {
            mLoadedContent.put(type, new ArrayList<>());
        }
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

    private IContentLoader<IShader> createShaderContentLoader() {
        ShaderCreator shaderCreator = new ShaderCreator();

        return new ShaderLoader(shaderCreator);
    }

    private IContentLoader<Texture2D> createTexture2DContentLoader() {
        stbi_set_flip_vertically_on_load(true);
        Texture2DCreator texture2DCreator = new Texture2DCreator();

        return new Texture2DLoader(texture2DCreator);
    }
}
