package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.effect.Effect;
import com.maple.graphics.shader.exceptions.ShaderLoadFailedException;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.IKeyAction;
import com.maple.input.keyboard.Key;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;
import com.maple.log.Logger;
import com.maple.math.MathHelper;
import com.maple.math.Matrix4f;
import com.maple.math.Vector3f;
import com.maple.renderer.Renderer;
import com.maple.renderer.camera.PerspectiveCamera;
import com.maple.renderer.camera.PerspectiveCameraController;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.mesh.terrain.ITerrainColorizer;
import com.maple.renderer.mesh.terrain.TerrainColorBufferCreator;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.utils.Color;
import com.maple.world.terrain.Terrain;
import com.maple.world.terrain.TerrainCreator;
import com.maple.world.terrain.heightmap.DefaultHeightMapGeneratorBuilder;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements IGame {
    private GraphicsManager mGraphicsManager;
    private Window mWindow;
    private Renderer mRenderer;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    private IShader mVertexShader;
    private IShader mFragmentShader;
    private Effect mEffect;

    private PerspectiveCamera mPerspectiveCamera;

    private Terrain mTerrain;
    private Mesh mTerrainMesh;

    public Game(GameContext context) {
        mGraphicsManager = context.getGraphicsManager();
        mWindow = mGraphicsManager.getWindow();
        mRenderer = mGraphicsManager.getRenderer();
        mKeymap = context.getKeymap();
        mShaderManager = context.getShaderManager();
        mMousePositionCallbackDispatcher = context.getMousePositionCallbackDispatcher();
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setApplicationTag("Playground");

        try {
            mVertexShader = mShaderManager.load("Playground/res/vs_shader.vs");
            mFragmentShader = mShaderManager.load("Playground/res/fs_shader.fs");
        } catch (ShaderLoadFailedException e) {
            throw new OperationFailedException(e);
        }

        mEffect = new Effect(mVertexShader, mFragmentShader);

        mPerspectiveCamera = new PerspectiveCamera(45, mWindow.getAspectRatio(), 0.01F, 1000);
        mPerspectiveCamera.setPosition(new Vector3f(0, 80, 0));
        mMousePositionCallbackDispatcher.addDisabledCursorCallback(new PerspectiveCameraController(mPerspectiveCamera, 2.5F));

        mKeymap.add(new Key(GLFW_KEY_W), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(mPerspectiveCamera.getRotation().X, mPerspectiveCamera.getRotation().Y);
                vector3f.normalize();
                mPerspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_A), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(0,
                                                          mPerspectiveCamera.getRotation().Y - MathHelper.PI / 2);
                vector3f.normalize();
                mPerspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_S), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(mPerspectiveCamera.getRotation().X,
                                                          mPerspectiveCamera.getRotation().Y);
                vector3f.negate();
                vector3f.normalize();
                mPerspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_D), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(0,
                                                          mPerspectiveCamera.getRotation().Y + MathHelper.PI / 2);
                vector3f.normalize();
                mPerspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });

        TerrainCreator terrainCreator = new TerrainCreator(new DefaultHeightMapGeneratorBuilder().build());
        mTerrain = terrainCreator.create(256, 256);
        TerrainMeshCreator terrainMeshCreator = mGraphicsManager.getTerrainMeshCreator();
        mTerrainMesh = terrainMeshCreator.create(mTerrain);

        VertexBufferCreator vertexBufferCreator = mGraphicsManager.getVertexBufferCreator();
        ITerrainColorizer terrainColorizer = (x, y, z) -> new Color(x, (int) y, z);
        TerrainColorBufferCreator terrainColorBufferCreator = new TerrainColorBufferCreator(vertexBufferCreator,
                                                                                            terrainColorizer);
        VertexBuffer colorBuffer = terrainColorBufferCreator.create(mTerrain);

        mTerrainMesh = terrainMeshCreator.create(mTerrain, colorBuffer);

        mRenderer.setEffect(mEffect);
        mRenderer.bindMesh(mTerrainMesh);
    }

    @Override
    public void update(GameTime gameTime) {
        float y = mTerrain.get(mPerspectiveCamera.getPosition().X, mPerspectiveCamera.getPosition().Z);
        y += 1.8;
        mPerspectiveCamera.setPosition(new Vector3f(mPerspectiveCamera.getPosition().X, y, mPerspectiveCamera.getPosition().Z));
    }

    @Override
    public void render(float alpha) {
        mRenderer.clear(new Color(0.392F, 0.584F, 0.929F, 1.0F));

        for (int i = 0; i < 2; ++i) {
            Matrix4f transform = Matrix4f.createTranslation(new Vector3f(0, 0, i * 256));
            mRenderer.setMVP(Matrix4f.multiply(transform, mPerspectiveCamera.getViewProjectionMatrix()));
            mRenderer.render();
        }
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
