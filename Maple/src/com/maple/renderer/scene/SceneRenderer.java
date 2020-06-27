package com.maple.renderer.scene;

import com.maple.entity.IEntity;
import com.maple.entity.component.model.ModelComponentSystem;
import com.maple.graphics.shader.effect.Effect;
import com.maple.renderer.Renderer;
import com.maple.renderer.camera.ICamera;
import com.maple.utils.Color;

public class SceneRenderer implements ISceneRenderer {
    private ModelComponentSystem mModelComponentSystem;

    private Renderer mRenderer;
    private EntityRenderer mEntityRenderer;
    private Effect mEffect;

    private SceneProperties mProperties;

    public SceneRenderer(ModelComponentSystem modelComponentSystem, EntityRenderer entityRenderer, Effect effect,
                         SceneProperties properties) {
        mModelComponentSystem = modelComponentSystem;
        mRenderer = entityRenderer.getRenderer();
        mEntityRenderer = entityRenderer;
        mEffect = effect;
        mProperties = properties;
    }

    public void render(float alpha) {
        ICamera camera = mProperties.getCamera();
        Color skyColor = mProperties.getSkyColor();

        mRenderer.clear(skyColor);
        mRenderer.setViewProjectionMatrix(camera.getViewProjectionMatrix());

        mRenderer.setEffect(mEffect);
        for (IEntity entity : mModelComponentSystem.getEntities()) {
            mEntityRenderer.render(entity);
        }
    }

    @Override
    public SceneProperties getProperties() {
        return mProperties;
    }
}
