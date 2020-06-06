package com.maple.renderer.sprite;

import com.maple.graphics.shader.effect.Effect;
import com.maple.math.Matrix4f;
import com.maple.math.Vector2f;
import com.maple.math.Vector3f;
import com.maple.renderer.DepthTestController;
import com.maple.renderer.Renderer;
import com.maple.renderer.blending.BlendingController;
import com.maple.renderer.camera.CameraStub;
import com.maple.renderer.camera.ICamera;
import com.maple.renderer.cull.CullingController;
import com.maple.renderer.exceptions.SceneAlreadyBegunException;
import com.maple.renderer.exceptions.SceneHasNotBegunException;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.sprite.shader.SpriteFragmentShader;
import com.maple.renderer.sprite.shader.SpriteVertexShader;

import static org.lwjgl.opengl.GL11.GL_LEQUAL;

public class SpriteRenderer {
    private static final Effect sEffectStub = new Effect();
    private static final ICamera sCameraStub = new CameraStub();

    private Renderer mRenderer;
    private DepthTestController mDepthTestController;
    private CullingController mCullingController;
    private BlendingController mBlendingController;

    private Effect mEffect;
    private SpriteVertexShader mVertexShader;
    private SpriteFragmentShader mFragmentShader;

    private Mesh mQuadMesh;
    private ICamera mCamera;

    private boolean mSceneBegun;

    private boolean mWasDepthTestEnabled;
    private boolean mWasCullingEnabled;
    private boolean mWasBlendingEnabled;

    public SpriteRenderer(Renderer renderer, SpriteVertexShader vertexShader, SpriteFragmentShader fragmentShader, Mesh quadMesh) {
        mRenderer = renderer;
        mDepthTestController = mRenderer.getDepthTestController();
        mCullingController = mRenderer.getCullingController();
        mBlendingController = mRenderer.getBlendingController();

        mEffect = new Effect(vertexShader, fragmentShader);
        mVertexShader = vertexShader;
        mFragmentShader = fragmentShader;

        mQuadMesh = quadMesh;
        mCamera = sCameraStub;

        mSceneBegun = false;
    }

    public void beginScene(ICamera camera) {
        if (mSceneBegun) {
            throw new SceneAlreadyBegunException();
        }

        mRenderer.setEffect(mEffect);
        mCamera = camera;
        mRenderer.bindMesh(mQuadMesh);
        enableDepthTest();
        disableCulling();
        enableBlending();

        mSceneBegun = true;
    }

    public void endScene() {
        if (!mSceneBegun) {
            throw new SceneHasNotBegunException();
        }

        restoreBlendingState();
        restoreCullingState();
        restoreDepthTestState();
        mRenderer.unbindTexture();
        mRenderer.unbindMesh();
        mCamera = sCameraStub;
        mRenderer.setEffect(sEffectStub);

        mSceneBegun = false;
    }

    public void render(Sprite sprite) {
        if (!mSceneBegun) {
            throw new SceneHasNotBegunException();
        }

        mRenderer.bindTexture(sprite.getTexture());

        Vector2f dimensions = sprite.getDimensions();
        Vector2f scale = sprite.getScale();
        Vector2f size = Vector2f.multiply(dimensions, scale);
        Vector3f size3f = new Vector3f(size, 1);

        Vector3f spriteCenter = Vector3f.divide(size3f, 2.0F);

        float roll = (float) Math.toRadians(sprite.getRoll());

        Vector3f position = Vector3f.add(sprite.getPosition(), spriteCenter);

        Matrix4f scaleMatrix = Matrix4f.createScale(size3f);
        Matrix4f rotationCenterMatrix = Matrix4f.createTranslation(Vector3f.negate(spriteCenter));
        Matrix4f rotationMatrix = Matrix4f.createRotationZ(-roll);
        Matrix4f translationMatrix = Matrix4f.createTranslation(position);
        Matrix4f viewProjectionMatrix = mCamera.getViewProjectionMatrix();
        Matrix4f mvp = Matrix4f.multiply(viewProjectionMatrix,
                                         translationMatrix,
                                         rotationMatrix,
                                         rotationCenterMatrix,
                                         scaleMatrix);
        mRenderer.setMVP(mvp);
        mVertexShader.setMask(sprite.getMaskPosition(), sprite.getMaskDimensions());
        mFragmentShader.setColor(sprite.getColor());
        mRenderer.render();
    }

    private void enableDepthTest() {
        mWasDepthTestEnabled = mDepthTestController.isEnabled();
        mDepthTestController.enable();
        mDepthTestController.setFunction(GL_LEQUAL);
        mRenderer.setClearDepth(1.0F);
        mRenderer.clear();
    }

    private void disableCulling() {
        mWasCullingEnabled = mCullingController.isEnabled();
        mCullingController.disable();
    }

    private void enableBlending() {
        mWasBlendingEnabled = mBlendingController.isEnabled();
        mBlendingController.enable();
    }

    private void restoreDepthTestState() {
        if (mWasDepthTestEnabled) {
            mDepthTestController.enable();
        } else {
            mDepthTestController.disable();
        }
    }

    private void restoreCullingState() {
        if (mWasCullingEnabled) {
            mCullingController.enable();
        } else {
            mCullingController.disable();
        }
    }

    private void restoreBlendingState() {
        if (mWasBlendingEnabled) {
            mBlendingController.enable();
        } else {
            mBlendingController.disable();
        }
    }
}
