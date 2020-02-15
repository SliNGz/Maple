package com.maple.graphics.shader.binder;

import com.maple.graphics.GLHelper;

public class ShaderBinderHelper {
    public static ShaderBinder create() {
        return new ShaderBinder(GLHelper.createPipeline());
    }

    public static void setCurrentBinder(ShaderBinder shaderBinder) {
        GLHelper.bindPipeline(shaderBinder.getPipeline());
    }

    public static void destroy(ShaderBinder shaderBinder) {
        GLHelper.deletePipeline(shaderBinder.getPipeline());
    }
}
