package com.maple.graphics.shader.manager;

import com.maple.graphics.GLHelper;
import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderLoader;
import com.maple.graphics.shader.exceptions.ShaderLoadFailedException;
import com.maple.log.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShaderManager implements IShaderManager {
    private ShaderLoader mShaderLoader;
    private List<Shader> mShaders;

    public ShaderManager(ShaderLoader shaderLoader) {
        mShaderLoader = shaderLoader;
        mShaders = new ArrayList<>();
    }

    public Shader load(String shaderPath) throws ShaderLoadFailedException {
        File shaderFile = new File(shaderPath);
        Shader shader = mShaderLoader.load(shaderFile);
        mShaders.add(shader);

        Logger.infoCore(String.format("SHADER_LOADED_SUCCESSFULLY SHADER:%s", shaderFile.getName()));

        return shader;
    }

    public void cleanup() {
        for (Shader shader : mShaders) {
            GLHelper.destroyShader(shader);
        }
    }
}
