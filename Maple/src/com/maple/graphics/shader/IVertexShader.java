package com.maple.graphics.shader;

import com.maple.math.Matrix4f;

public interface IVertexShader extends IShader {
    void setMVP(Matrix4f mvp);
}
