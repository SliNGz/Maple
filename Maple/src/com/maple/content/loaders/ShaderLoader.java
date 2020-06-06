package com.maple.content.loaders;

import com.maple.content.IContentLoader;
import com.maple.content.exceptions.ShaderLoadFailedException;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderCreator;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.exceptions.ShaderCreationFailedException;
import com.maple.graphics.shader.exceptions.ShaderSourceRetrievalFailedException;
import com.maple.graphics.shader.exceptions.ShaderTypeResolutionFailedException;
import com.maple.graphics.shader.exceptions.UnknownShaderType;
import com.maple.utils.FileUtils;
import com.maple.utils.exceptions.NoFileExtensionException;

import java.io.*;

public class ShaderLoader implements IContentLoader<IShader> {
    private ShaderCreator mShaderCreator;

    public ShaderLoader(ShaderCreator shaderCreator) {
        mShaderCreator = shaderCreator;
    }

    public IShader load(String path) {
        try {
            File shaderFile = new File(path);
            ShaderType shaderType = getShaderType(shaderFile);
            String shaderSource = getShaderSource(shaderFile);

            return mShaderCreator.create(shaderType, shaderSource);
        } catch (ShaderTypeResolutionFailedException | ShaderSourceRetrievalFailedException | ShaderCreationFailedException e) {
            throw new ShaderLoadFailedException(e);
        }
    }

    public void unload(IShader shader) {
        mShaderCreator.destroy(shader);
    }

    private ShaderType getShaderType(File shaderFile) throws ShaderTypeResolutionFailedException {
        try {
            String fileExtension = FileUtils.getExtension(shaderFile);

            switch (fileExtension) {
                case "vs":
                    return ShaderType.VERTEX_SHADER;
                case "fs":
                    return ShaderType.FRAGMENT_SHADER;
                case "gs":
                    return ShaderType.GEOMETRY_SHADER;
                default:
                    throw new UnknownShaderType();
            }
        } catch (NoFileExtensionException | UnknownShaderType e) {
            throw new ShaderTypeResolutionFailedException(e);
        }
    }

    private String getShaderSource(File shaderFile) throws ShaderSourceRetrievalFailedException {
        StringBuilder stringBuilder = new StringBuilder();

        try (FileInputStream fileInputStream = new FileInputStream(shaderFile)) {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            throw new ShaderSourceRetrievalFailedException(e);
        }

        return stringBuilder.toString();
    }
}
