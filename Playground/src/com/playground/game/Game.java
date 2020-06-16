package com.playground.game;

import com.maple.content.ContentLoader;
import com.maple.game.GameContext;
import com.maple.game.IGame;
import com.maple.game.exceptions.OperationFailedException;
import com.maple.game.runner.GameTime;
import com.maple.graphics.GraphicsManager;
import com.maple.graphics.buffer.BufferUsage;
import com.maple.graphics.buffer.index.IndexBuffer;
import com.maple.graphics.buffer.index.IndexBufferCreator;
import com.maple.graphics.buffer.vertex.VertexArray;
import com.maple.graphics.buffer.vertex.VertexArrayCreator;
import com.maple.graphics.buffer.vertex.VertexBuffer;
import com.maple.graphics.buffer.vertex.VertexBufferCreator;
import com.maple.graphics.buffer.vertex.specification.VertexPositionColorNormal;
import com.maple.graphics.framebuffer.Framebuffer;
import com.maple.graphics.framebuffer.FramebufferCreator;
import com.maple.graphics.shader.IShader;
import com.maple.graphics.shader.effect.Effect;
import com.maple.graphics.texture.TexelDataFormat;
import com.maple.graphics.texture.TexelDataType;
import com.maple.graphics.texture.Texture2D;
import com.maple.graphics.texture.TextureInternalFormat;
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
import com.maple.renderer.camera.OrthographicCamera;
import com.maple.renderer.camera.PerspectiveCamera;
import com.maple.renderer.camera.PerspectiveCameraController;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.mesh.terrain.ITerrainColorizer;
import com.maple.renderer.mesh.terrain.TerrainColorBufferCreator;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.renderer.shader.PhongFragmentShader;
import com.maple.renderer.sprite.Sprite;
import com.maple.renderer.sprite.SpriteRenderer;
import com.maple.utils.Color;
import com.maple.world.terrain.Terrain;
import com.maple.world.terrain.TerrainCreator;
import com.maple.world.terrain.heightmap.DefaultHeightMapGeneratorBuilder;

import java.util.List;
import java.util.Random;

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
    private PhongFragmentShader mFragmentShader;

    private Effect mEffect;

    private PerspectiveCamera mPerspectiveCamera;
    private OrthographicCamera mOrthographicCamera;

    private Terrain mTerrain;
    private Mesh mTerrainMesh;

    private Texture2D mTexture;

    private Mesh mCubeMesh;

    private int[][] mMap;

    private Vector3f mLightPosition = new Vector3f(-10, -10, -10);
    private Color mLightColor = new Color(255, 255, 255);

    private Framebuffer mFramebuffer;

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

        mVertexShader = mContentLoader.load(IShader.class, "phong_vertex_shader.vs");
        mFragmentShader = new PhongFragmentShader(mContentLoader.load(IShader.class, "phong_fragment_shader.fs"));

        mEffect = new Effect(mVertexShader, mFragmentShader);

        mPerspectiveCamera = new PerspectiveCamera(45, mWindow.getAspectRatio(), 0.01F, 1000);
        mPerspectiveCamera.setPosition(new Vector3f(40, 40, 40));
        mPerspectiveCamera.addRotation(new Vector3f(MathHelper.toRadians(-30), MathHelper.toRadians(45), 0));
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

        Random random = new Random();
        mMap = new int[16][16];
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                mMap[i][j] = random.nextInt(6) * random.nextInt(2);
            }
        }


        Color color = new Color(1.0F, 0.0F, 0.0F);
        VertexBuffer cubeVertexBuffer = vertexBufferCreator.create(List.of(
                // West
                new VertexPositionColorNormal(new Vector3f(0, 0, 0), color, new Vector3f(-1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 0), color, new Vector3f(-1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 1), color, new Vector3f(-1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 0, 1), color, new Vector3f(-1, 0, 0)),

                // East
                new VertexPositionColorNormal(new Vector3f(1, 0, 0), color, new Vector3f(1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 1), color, new Vector3f(1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 1), color, new Vector3f(1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 0), color, new Vector3f(1, 0, 0)),

                // North
                new VertexPositionColorNormal(new Vector3f(0, 0, 0), color, new Vector3f(0, 0, -1)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 0), color, new Vector3f(0, 0, -1)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 0), color, new Vector3f(0, 0, -1)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 0), color, new Vector3f(0, 0, -1)),

                // South
                new VertexPositionColorNormal(new Vector3f(0, 0, 1), color, new Vector3f(0, 0, 1)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 1), color, new Vector3f(0, 0, 1)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 1), color, new Vector3f(0, 0, 1)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 1), color, new Vector3f(0, 0, 1)),

                // Down
                new VertexPositionColorNormal(new Vector3f(0, 0, 0), color, new Vector3f(0, -1, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 0, 1), color, new Vector3f(0, -1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 1), color, new Vector3f(0, -1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 0), color, new Vector3f(0, -1, 0)),

                // Up
                new VertexPositionColorNormal(new Vector3f(0, 1, 0), color, new Vector3f(0, 1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 0), color, new Vector3f(0, 1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 1), color, new Vector3f(0, 1, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 1), color, new Vector3f(0, 1, 0))
        ), BufferUsage.STATIC_DRAW);

        VertexArrayCreator vertexArrayCreator = mGraphicsManager.getVertexArrayCreator();
        VertexArray cubeVertexArray = vertexArrayCreator.create(cubeVertexBuffer);

        int[] indices = new int[36];
        for (int i = 0; i < indices.length / 6; ++i) {
            int vertexIndexBase = i * 4;
            int indicesIndex = i * 6;
            indices[indicesIndex] = vertexIndexBase;
            indices[indicesIndex + 1] = vertexIndexBase + 1;
            indices[indicesIndex + 2] = vertexIndexBase + 2;

            indices[indicesIndex + 3] = vertexIndexBase + 2;
            indices[indicesIndex + 4] = vertexIndexBase + 3;
            indices[indicesIndex + 5] = vertexIndexBase;
        }

        IndexBufferCreator indexBufferCreator = mGraphicsManager.getIndexBufferCreator();
        IndexBuffer cubeIndexBuffer = indexBufferCreator.create(indices, BufferUsage.STATIC_DRAW);

        mCubeMesh = new Mesh(cubeVertexArray, cubeIndexBuffer);

        FramebufferCreator framebufferCreator = mGraphicsManager.getFramebufferCreator();
        mFramebuffer = framebufferCreator.create(mWindow.getWidth(), mWindow.getHeight(),
                                                 TextureInternalFormat.RGB, TexelDataFormat.RGB, TexelDataType.UNSIGNED_BYTE);
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

        mLightPosition.add(new Vector3f(0.1F, 0.1F, 0.1F));
    }

    @Override
    public void render(float alpha) {
        mRenderer.bindFramebuffer(mFramebuffer);
        mRenderer.clear(new Color(0.392F, 0.584F, 0.929F, 1.0F));
        mFragmentShader.setCameraPosition(mPerspectiveCamera.getPosition());
        mFragmentShader.setLightPosition(mLightPosition);
        mFragmentShader.setLightColor(mLightColor);
        mFragmentShader.setLightAttenuationIntensity(0.001F);
        mFragmentShader.setAmbientIntensity(0.07F);

        mRenderer.setEffect(mEffect);
        mRenderer.setViewProjectionMatrix(mPerspectiveCamera.getViewProjectionMatrix());
        mRenderer.setModelMatrix(Matrix4f.multiply(Matrix4f.createTranslation(new Vector3f(-10, -10, -10)), Matrix4f.createScale(20)));
        mRenderer.bindMesh(mCubeMesh);
        mRenderer.render();

        mRenderer.unbindFramebuffer();

        mSpriteRenderer.beginScene(mOrthographicCamera);
        mSpriteRenderer.render(new Sprite(mFramebuffer.getTexture()));
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
    }
}
