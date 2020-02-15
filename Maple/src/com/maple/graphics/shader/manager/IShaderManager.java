package com.maple.graphics.shader.manager;

import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.exceptions.ShaderLoadFailedException;

public interface IShaderManager {
    Shader load(String shaderPath) throws ShaderLoadFailedException;
}
