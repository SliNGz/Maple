package com.maple.graphics.shader.binder;

import com.maple.graphics.GLHelper;
import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.program.ProgramPipeline;
import com.maple.graphics.shader.program.ShaderProgram;

public class ShaderBinder implements IShaderBinder {
    private final ProgramPipeline mPipeline;

    public ShaderBinder(ProgramPipeline pipeline) {
        mPipeline = pipeline;
    }

    public void bind(Shader shader) {
        ShaderType shaderType = shader.getType();
        GLHelper.useProgramStages(mPipeline, shaderType.getStageBit(), shader.getProgram());
    }

    public void unbind(ShaderType shaderType) {
        GLHelper.useProgramStages(mPipeline, shaderType.getStageBit(), ShaderProgram.NULL);
    }

    public ProgramPipeline getPipeline() {
        return mPipeline;
    }
}
