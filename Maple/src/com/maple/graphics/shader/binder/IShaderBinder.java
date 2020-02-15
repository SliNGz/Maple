package com.maple.graphics.shader.binder;

import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderType;

public interface IShaderBinder {
    void bind(Shader shader);

    void unbind(ShaderType shaderType);
}
