package com.playground.game;

import com.maple.content.ContentLoader;
import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.effect.Effect;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.IKeyAction;
import com.maple.input.keyboard.Key;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;
import com.maple.log.Logger;
import com.maple.math.MathHelper;
import com.maple.math.Vector3f;
import com.maple.renderer.Renderer;
import com.maple.renderer.camera.OrthographicCamera;
import com.maple.renderer.camera.PerspectiveCamera;
import com.maple.renderer.camera.PerspectiveCameraController;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.mesh.terrain.ITerrainColorizer;
import com.maple.renderer.mesh.terrain.TerrainColorBufferCreator;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.renderer.sprite.Sprite;
import com.maple.renderer.sprite.SpriteRenderer;
import com.maple.utils.Color;
import com.maple.world.terrain.Terrain;
import com.maple.world.terrain.TerrainCreator;
import com.maple.world.terrain.heightmap.DefaultHeightMapGeneratorBuilder;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements IGame {
    private GraphicsManager mGraphicsManager;
    private Window mWindow;
    private Renderer mRenderer;
    private SpriteRenderer mSpriteRenderer;
    private IKeymap mKeymap;
    private ContentLoader mContentLoader;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;

    private IShader mVertexShader;
    private IShader mFragmentShader;

    private Effect mEffect;

    private PerspectiveCamera mPerspectiveCamera;
    private OrthographicCamera mOrthographicCamera;

    private Terrain mTerrain;
    private Mesh mTerrainMesh;

    private Texture2D mTexture;

    public Game(GameContext context) {
        mGraphicsManager = context.getGraphicsManager();
        mWindow = mGraphicsManager.getWindow();
        mRenderer = mGraphicsManager.getRenderer();
        mSpriteRenderer = mGraphicsManager.getSpriteRenderer();
        mKeymap = context.getKeymap();
        mContentLoader = context.getContentLoader();
        mMousePositionCallbackDispatcher = context.getMousePositionCallbackDispatcher();
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setApplicationTag("Playground");

        mVertexShader = mContentLoader.load(IShader.class, "vs_shader.vs");
        mFragmentShader = mContentLoader.load(IShader.class, "fs_shader.fs");

        mEffect = new Effect(mVertexShader, mFragmentShader);

        mPerspectiveCamera = new PerspectiveCamera(45, mWindow.getAspectRatio(), 0.01F, 1000);
        mPerspectiveCamera.setPosition(new Vector3f(0, 80, 0));
        mMousePositionCallbackDispatcher.addDisabledCursorCallback(new PerspectiveCameraController(mPerspectiveCamera, 2.5F));

        initializeKeyBindings();

        mOrthographicCamera = new OrthographicCamera(mWindow.getWidth(), mWindow.getHeight());

        TerrainCreator terrainCreator = new TerrainCreator(new DefaultHeightMapGeneratorBuilder().build());
        mTerrain = terrainCreator.create(256, 256);
        TerrainMeshCreator terrainMeshCreator = mGraphicsManager.getTerrainMeshCreator();
        VertexBufferCreator vertexBufferCreator = mGraphicsManager.getVertexBufferCreator();
        ITerrainColorizer terrainColorizer = (x, y, z) -> new Color(x, (int) y, z);
        TerrainColorBufferCreator terrainColorBufferCreator = new TerrainColorBufferCreator(vertexBufferCreator,
                                                                                            terrainColorizer);
        VertexBuffer colorBuffer = terrainColorBufferCreator.create(mTerrain);

        mTerrainMesh = terrainMeshCreator.create(mTerrain, colorBuffer);

        mTexture = mContentLoader.load(Texture2D.class, "image.png");
    }

    @Override
    public void update(GameTime gameTime) {
//        float y = mTerrain.get(mPerspectiveCamera.getPosition().X, mPerspectiveCamera.getPosition().Z);
//        y += 1.8;
//        mPerspectiveCamera.setPosition(new Vector3f(mPerspectiveCamera.getPosition().X, y, mPerspectiveCamera.getPosition().Z));

//        mOrthographicCamera.setPosition(new Vector3f(mOrthographicCamera.getPosition().X + 0.001F,
//                                                     mOrthographicCamera.getPosition().Y,
//                                                     mOrthographicCamera.getPosition().Z));
//
//        mOrthographicCamera.setRoll(mOrthographicCamera.getRoll() + 0.007F);
    }

    @Override
    public void render(float alpha) {
        mRenderer.clear(new Color(0.392F, 0.584F, 0.929F, 1.0F));

        mSpriteRenderer.beginScene(mOrthographicCamera);
        mSpriteRenderer.render(new Sprite(mTexture).setPosition(8, 0, 0.1F).setMaskDimensions(16, 16).setDimensions(16, 16).setMaskPosition(0, 0));
        mSpriteRenderer.render(new Sprite(mTexture).setMaskDimensions(16, 16).setDimensions(16, 16).setMaskPosition(48, 0));
        mSpriteRenderer.render(new Sprite(mTexture).setMaskDimensions(16, 16).setDimensions(16, 16).setMaskPosition(32, 0));
        mSpriteRenderer.endScene();
    }

    @Override
    public void cleanup() {
    }

    @Override
    public boolean shouldExit() {
        return false;
    }

    private void initializeKeyBindings() {
        mKeymap.add(new Key(GLFW_KEY_W), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(mPerspectiveCamera.getRotation().X, mPerspectiveCamera.getRotation().Y);
                vector3f.normalize();
                mPerspectiveCamera.addPosition(vector3f);

                mOrthographicCamera.setScale(mOrthographicCamera.getScale() - 0.01F);
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

                mOrthographicCamera.setScale(1.0F);
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

                mOrthographicCamera.setScale(mOrthographicCamera.getScale() + 0.01F);
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
    }
}
