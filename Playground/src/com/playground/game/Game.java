package com.playground.game;

import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexList;
import com.maple.graphics.buffer.vertex.specification.VertexPositionColor;
import com.maple.graphics.buffer.vertex.specification.VertexPositionColorList;
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
import com.maple.utils.Color;
import com.maple.utils.PerspectiveCameraController;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class Game implements IGame {
    private GraphicsManager mGraphicsManager;
    private Window mWindow;
    private Renderer mRenderer;
    private IKeymap mKeymap;
    private IShaderManager mShaderManager;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    private Shader mVertexShader;
    private Shader mFragmentShader;

    private VertexArray mVertexArray;
    private IndexBuffer mIndexBuffer;

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

        VertexArrayCreator vertexArrayCreator = mGraphicsManager.getVertexArrayCreator();
        VertexList<VertexPositionColor> vertexPositionColorVertexList = new VertexPositionColorList();
        float z = -20;
        vertexPositionColorVertexList.add(new VertexPositionColor(new Vector3f(-5, -5, z), new Color(1.0F, 0, 0, 1.0F)));
        vertexPositionColorVertexList.add(new VertexPositionColor(new Vector3f(-5, 5, z), new Color(1.0F, 0, 0, 1.0F)));
        vertexPositionColorVertexList.add(new VertexPositionColor(new Vector3f(5, -5, z), new Color(1.0F, 0, 0, 1.0F)));
        vertexPositionColorVertexList.add(new VertexPositionColor(new Vector3f(5, 5, z), new Color(1.0F, 0, 0, 1.0F)));
        mVertexArray = vertexArrayCreator.create(vertexPositionColorVertexList, GL_STATIC_DRAW);

        IndexBufferCreator indexBufferCreator = mGraphicsManager.getIndexBufferCreator();
        mIndexBuffer = indexBufferCreator.create(new int[]{0, 1, 2, 3, 2, 1}, GL_STATIC_DRAW);

        PerspectiveCamera perspectiveCamera = new PerspectiveCamera(45, mWindow.getAspectRatio(), 0.01F, 1000);
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
    }

    @Override
    public void update(GameTime gameTime) {
    }

    @Override
    public void render(float alpha) {
        glClear(GL_COLOR_BUFFER_BIT);
        glClearColor(0.392F, 0.584F, 0.929F, 1.0F);

        mRenderer.bindShader(mVertexShader);
        mRenderer.bindShader(mFragmentShader);

        mRenderer.bindVertexArray(mVertexArray);
        mRenderer.bindIndexBuffer(mIndexBuffer);

        mRenderer.render();

        mRenderer.unbindVertexArray();
        mRenderer.unbindIndexBuffer();

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
