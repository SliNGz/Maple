package com.maple.graphics.shader;

import com.maple.graphics.shader.exceptions.*;
import com.maple.utils.FileUtils;
import com.maple.utils.exceptions.NoFileExtensionException;

import java.io.*;

public class ShaderLoader {
    private ShaderCreator mShaderCreator;

    public ShaderLoader(ShaderCreator shaderCreator) {
        mShaderCreator = shaderCreator;
    }

    public IShader load(File shaderFile) throws ShaderLoadFailedException {
        try {
            ShaderType shaderType = getShaderType(shaderFile);
            String shaderSource = getShaderSource(shaderFile);

            return mShaderCreator.create(shaderType, shaderSource);
        } catch (ShaderTypeResolutionFailedException | ShaderSourceRetrievalFailedException | ShaderCreationFailedException e) {
            throw new ShaderLoadFailedException(e);
        }
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

    public void unload(IShader shader) {
        mShaderCreator.destroy(shader);
    }
}
