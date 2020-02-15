package com.maple.graphics.shader;

import com.maple.graphics.shader.exceptions.ShaderCompilationFailedException;
import com.maple.graphics.shader.exceptions.ShaderCreationFailedException;
import com.maple.graphics.shader.exceptions.ShaderProgramCreationFailedException;
import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.program.ShaderProgramCreator;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.log.Logger;

import static org.lwjgl.opengl.GL41.*;

public class ShaderCreator {
    private static final int SHADER_CREATION_ERROR = 0;

    public static Shader create(ShaderType shaderType, String shaderSource) throws ShaderCreationFailedException {
        int shader = createShader(shaderType);

        try {
            glShaderSource(shader, shaderSource);
            compileShader(shader);
            ShaderProgram program = ShaderProgramCreator.create(shader);
            ShaderUniformController uniformController = new ShaderUniformController(program);

            return new Shader(shaderType, program, uniformController);
        } catch (ShaderCompilationFailedException | ShaderProgramCreationFailedException e) {
            throw new ShaderCreationFailedException(e);
        } finally {
            glDeleteShader(shader);
        }
    }

    private static int createShader(ShaderType shaderType) throws ShaderCreationFailedException {
        int shader = glCreateShader(shaderType.getValue());
        if (shader == SHADER_CREATION_ERROR) {
            throw new ShaderCreationFailedException();
        }

        return shader;
    }

    private static void compileShader(int shader) throws ShaderCompilationFailedException {
        glCompileShader(shader);

        int[] compilationStatusBuffer = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compilationStatusBuffer);

        int compilationStatus = compilationStatusBuffer[0];
        if (compilationStatus == GL_FALSE) {
            int[] maxLengthBuffer = new int[1];
            glGetShaderiv(shader, GL_INFO_LOG_LENGTH, maxLengthBuffer);

            int maxLength = maxLengthBuffer[0];
            String log = glGetShaderInfoLog(shader, maxLength);
            Logger.errorCore("SHADER_COMPILATION_FAILED\n" + log);

            throw new ShaderCompilationFailedException();
        }
    }
}
