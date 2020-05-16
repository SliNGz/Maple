package com.maple.graphics.shader.manager;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.exceptions.ShaderLoadFailedException;

public interface IShaderManager {
    IShader load(String shaderPath) throws ShaderLoadFailedException;
}
