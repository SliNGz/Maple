package com.maple.renderer.scene;

import com.maple.entity.IEntity;
import com.maple.entity.component.PositionComponent;
import com.maple.entity.component.RotationComponent;
import com.maple.entity.component.ScaleComponent;
import com.maple.entity.component.model.ModelComponent;
import com.maple.math.Matrix4f;
import com.maple.math.Vector3f;
import com.maple.renderer.Renderer;
import com.maple.renderer.model.Model;
import com.maple.renderer.model.ModelRenderer;

public class EntityRenderer {
    private ModelRenderer mModelRenderer;

    public EntityRenderer(ModelRenderer modelRenderer) {
        mModelRenderer = modelRenderer;
    }

    public void render(IEntity entity) {
        ModelComponent modelComponent = entity.getComponent(ModelComponent.class);
        Model model = modelComponent.getModel();
        Matrix4f modelMatrix = getModelMatrix(entity);

        mModelRenderer.render(model, modelMatrix);
    }

    private Matrix4f getModelMatrix(IEntity entity) {
        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        Vector3f position = positionComponent == null ? new Vector3f() : positionComponent.get();

        RotationComponent rotationComponent = entity.getComponent(RotationComponent.class);
        Vector3f rotation = rotationComponent == null ? new Vector3f() : rotationComponent.get();

        ScaleComponent scaleComponent = entity.getComponent(ScaleComponent.class);
        Vector3f scale = scaleComponent == null ? new Vector3f(1, 1, 1) : scaleComponent.get();

        // TODO: Retrieve model's dimensions and calculate rotation center.
//        Vector3f modelCenter = Vector3f.divide(scale, 2.0F);
        Matrix4f scaleMatrix = Matrix4f.createScale(scale);
//        Matrix4f rotationCenterMatrix = Matrix4f.createTranslation(Vector3f.negate(modelCenter));
        Matrix4f rotationCenterMatrix = Matrix4f.createIdentity();
        Matrix4f rotationMatrix = Matrix4f.multiply(Matrix4f.createRotationX(rotation.X),
                                                    Matrix4f.createRotationY(rotation.Y),
                                                    Matrix4f.createRotationZ(rotation.Z));
        Matrix4f translationMatrix = Matrix4f.createTranslation(position);
        Matrix4f modelMatrix = Matrix4f.multiply(translationMatrix, rotationMatrix, rotationCenterMatrix, scaleMatrix);

        return modelMatrix;
    }

    public Renderer getRenderer() {
        return mModelRenderer.getRenderer();
    }
}
