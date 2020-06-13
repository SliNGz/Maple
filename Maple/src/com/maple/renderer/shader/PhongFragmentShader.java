package com.maple.renderer.shader;

import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.program.ShaderProgram;
import com.maple.graphics.shader.uniform.ShaderUniformController;
import com.maple.math.Vector3f;
import com.maple.utils.Color;

public class PhongFragmentShader implements IShader {
    private static final String UNIFORM_CAMERA_POSITION = "u_CameraPosition";
    private static final String UNIFORM_LIGHT_POSITION = "u_LightPosition";
    private static final String UNIFORM_LIGHT_COLOR = "u_LightColor";
    private static final String UNIFORM_LIGHT_ATTENUATION_INTENSITY = "u_LightAttenuationIntensity";
    private static final String UNIFORM_AMBIENT_INTENSITY = "u_AmbientIntensity";

    private IShader mShader;

    public PhongFragmentShader(IShader shader) {
        mShader = shader;
    }

    @Override
    public ShaderType getType() {
        return mShader.getType();
    }

    @Override
    public ShaderProgram getProgram() {
        return mShader.getProgram();
    }

    @Override
    public ShaderUniformController getUniformController() {
        return mShader.getUniformController();
    }

    public void setCameraPosition(Vector3f cameraPosition) {
        getUniformController().setVector3f(UNIFORM_CAMERA_POSITION, cameraPosition);
    }

    public void setLightPosition(Vector3f lightPosition) {
        getUniformController().setVector3f(UNIFORM_LIGHT_POSITION, lightPosition);
    }

    public void setLightColor(Color lightColor) {
        getUniformController().setColor(UNIFORM_LIGHT_COLOR, lightColor);
    }

    public void setLightAttenuationIntensity(float lightAttenuationIntensity) {
        getUniformController().setFloat(UNIFORM_LIGHT_ATTENUATION_INTENSITY, lightAttenuationIntensity);
    }

    public void setAmbientIntensity(float ambientIntensity) {
        getUniformController().setFloat(UNIFORM_AMBIENT_INTENSITY, ambientIntensity);
    }
}
