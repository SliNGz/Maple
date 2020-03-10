package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.shader.Shader;
import com.maple.graphics.shader.ShaderType;
import com.maple.graphics.shader.exceptions.ShaderLoadFailedException;
import com.maple.graphics.shader.manager.IShaderManager;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.IKeyAction;
import com.maple.input.keyboard.Key;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;
import com.maple.log.Logger;
import com.maple.math.MathHelper;
import com.maple.math.Vector3f;
import com.maple.renderer.Renderer;
import com.maple.renderer.camera.PerspectiveCamera;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.mesh.TerrainMeshCreator;
import com.maple.utils.Color;
import com.maple.utils.PerspectiveCameraController;
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

    private Shader mVertexShader;
    private Shader mFragmentShader;

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

        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(45, mWindow.getAspectRatio(), 0.01F, 1000);
        perspectiveCamera.setPosition(new Vector3f(0, 80, 0));
        mMousePositionCallbackDispatcher.addDisabledCursorCallback(new PerspectiveCameraController(perspectiveCamera, 0.5F));
        mRenderer.setCamera(perspectiveCamera);

        mKeymap.add(new Key(GLFW_KEY_W), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(perspectiveCamera.getRotation().X, perspectiveCamera.getRotation().Y);
                vector3f.normalize();
                perspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_A), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(0,
                                                          perspectiveCamera.getRotation().Y - MathHelper.PI / 2);
                vector3f.normalize();
                perspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_S), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(perspectiveCamera.getRotation().X,
                                                          perspectiveCamera.getRotation().Y);
                vector3f.negate();
                vector3f.normalize();
                perspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_D), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(0,
                                                          perspectiveCamera.getRotation().Y + MathHelper.PI / 2);
                vector3f.normalize();
                perspectiveCamera.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });

        TerrainCreator terrainCreator = new TerrainCreator(new DefaultHeightMapGeneratorBuilder().build());
        Terrain terrain = terrainCreator.create(256, 256);
        TerrainMeshCreator terrainMeshCreator = new TerrainMeshCreator(mGraphicsManager.getVertexArrayCreator(),
                                                                       mGraphicsManager.getIndexBufferCreator());
        mTerrainMesh = terrainMeshCreator.create(terrain);
    }

    @Override
    public void update(GameTime gameTime) {
    }

    @Override
    public void render(float alpha) {
        mRenderer.clear(new Color(0.392F, 0.584F, 0.929F, 1.0F));

        mRenderer.bindShader(mVertexShader);
        mRenderer.bindShader(mFragmentShader);

        mRenderer.bindMesh(mTerrainMesh);
        mRenderer.render();
        mRenderer.unbindMesh();

        mRenderer.unbindShader(ShaderType.VERTEX_SHADER);
        mRenderer.unbindShader(ShaderType.FRAGMENT_SHADER);
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}
