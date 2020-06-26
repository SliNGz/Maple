package com.playground.game;

import com.maple.content.ContentLoader;
import com.maple.entity.IEntityManager;
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
import com.maple.graphics.texture.*;
import com.maple.graphics.texture.parameters.TextureParameterBorderColor;
import com.maple.graphics.texture.parameters.TextureParameterWrap;
import com.maple.graphics.texture.parameters.TextureParameterWrapS;
import com.maple.graphics.texture.parameters.TextureParameterWrapT;
import com.maple.graphics.window.Window;
import com.maple.input.keyboard.IKeyAction;
import com.maple.input.keyboard.Key;
import com.maple.input.keyboard.map.IKeymap;
import com.maple.input.mouse.IMousePositionCallbackDispatcher;
import com.maple.log.Logger;
import com.maple.math.MathHelper;
import com.maple.math.Matrix4f;
import com.maple.math.Vector3f;
import com.maple.math.Vector4f;
import com.maple.renderer.Renderer;
import com.maple.renderer.camera.MouseRotationController;
import com.maple.renderer.camera.OrthographicCamera;
import com.maple.renderer.camera.PerspectiveCamera;
import com.maple.renderer.mesh.Mesh;
import com.maple.renderer.mesh.terrain.ITerrainColorizer;
import com.maple.renderer.mesh.terrain.TerrainColorBufferCreator;
import com.maple.renderer.mesh.terrain.TerrainMeshCreator;
import com.maple.renderer.model.Model;
import com.maple.renderer.model.ModelMesh;
import com.maple.renderer.shader.PhongFragmentShader;
import com.maple.renderer.sprite.SpriteRenderer;
import com.maple.utils.Color;
import com.maple.utils.IFramesCounter;
import com.maple.world.terrain.Terrain;
import com.maple.world.terrain.TerrainCreator;
import com.maple.world.terrain.heightmap.DefaultHeightMapGeneratorBuilder;
import com.playground.entity.Player;
import com.playground.entity.PlayerCreator;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements IGame {
    private IFramesCounter mFramesCounter;
    private GraphicsManager mGraphicsManager;
    private Window mWindow;
    private Renderer mRenderer;
    private SpriteRenderer mSpriteRenderer;
    private IKeymap mKeymap;
    private ContentLoader mContentLoader;
    private IMousePositionCallbackDispatcher mMousePositionCallbackDispatcher;
    private IEntityManager mEntityManager;

    private IShader mVertexShader;
    private PhongFragmentShader mFragmentShader;

    private IShader mHDRFragmentShader;
    private IShader mSpriteFragmentShader;

    private Effect mColorEffect;
    private Effect mShadowMappingEffect;

    private PerspectiveCamera mPerspectiveCamera;
    private OrthographicCamera mOrthographicCamera;

    private OrthographicCamera m2DCamera;

    private final int SHADOW_MAP_WIDTH = 2000;
    private final int SHADOW_MAP_HEIGHT = 2000;

    private Terrain mTerrain;
    private Mesh mTerrainMesh;

    private Texture2D mTexture;

    private Mesh mCubeMesh;

    private Vector3f mLightPosition = new Vector3f(50, 200, 50);
    private Vector4f mLightColor = new Vector4f(3.0F, 3.0F, 3.0F, 3.0F);

    private Framebuffer mShadowMapFramebuffer;
    private Framebuffer mOrthographicCameraViewFramebuffer;
    private Framebuffer mHDRFramebuffer;

    private Player mPlayer;
    private Model mCubeModel;

    private Effect mBasicEffect;

    public Game(GameContext context) {
        mFramesCounter = context.getFramesCounter();
        mGraphicsManager = context.getGraphicsManager();
        mWindow = mGraphicsManager.getWindow();
        mRenderer = mGraphicsManager.getRenderer();
        mSpriteRenderer = mGraphicsManager.getSpriteRenderer();
        mKeymap = context.getKeymap();
        mContentLoader = context.getContentLoader();
        mMousePositionCallbackDispatcher = context.getMousePositionCallbackDispatcher();
        mEntityManager = context.getEntityManager();
    }

    @Override
    public void initialize() throws OperationFailedException {
        Logger.setApplicationTag("Playground");

        mVertexShader = mContentLoader.load(IShader.class, "phong_vertex_shader.vs");
        mFragmentShader = new PhongFragmentShader(mContentLoader.load(IShader.class, "phong_fragment_shader.fs"));
        mHDRFragmentShader = mContentLoader.load(IShader.class, "hdr_fragment_shader.fs");
        mSpriteFragmentShader = mContentLoader.load(IShader.class, "sprite_fragment_shader.fs");

        IShader colorVertexShader = mContentLoader.load(IShader.class, "color_shader.vs");
        mColorEffect = new Effect(colorVertexShader);

        IShader basicVertexShader = mContentLoader.load(IShader.class, "basic_shader.vs");
        mBasicEffect = new Effect(basicVertexShader);

        mCubeModel = mContentLoader.load(Model.class, "cube.obj");

        IShader shadowMappingVertexShader = mContentLoader.load(IShader.class, "shadow_mapping.vs");
        IShader shadowMappingFragmentShader = mContentLoader.load(IShader.class, "shadow_mapping.fs");
        mShadowMappingEffect = new Effect(shadowMappingVertexShader, shadowMappingFragmentShader);

        mPerspectiveCamera = new PerspectiveCamera(45, mWindow.getAspectRatio(), 0.01F, 1000);

        initializeKeyBindings();

        m2DCamera = new OrthographicCamera(mWindow.getWidth(), mWindow.getHeight(), -1.0F, 1.0F);

        mOrthographicCamera = new OrthographicCamera(-300, 300, -300, 300, 1, 300);
        mOrthographicCamera.setPosition(mLightPosition);
        mOrthographicCamera.setRotation(new Vector3f(MathHelper.toRadians(90), MathHelper.toRadians(0), 0));

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

        Color colorX = new Color(1.0F, 0.0F, 0.0F, 1.0F);
        Color colorY = new Color(0.0F, 1.0F, 0.0F, 1.0F);
        Color colorZ = new Color(0.0F, 0.0F, 1.0F, 1.0F);

//        Color colorX = new Color(1.0F, 1.0F, 1.0F, 1.0F);
//        Color colorY = new Color(1.0F, 1.0F, 1.0F, 1.0F);
//        Color colorZ = new Color(1.0F, 1.0F, 1.0F, 1.0F);
        VertexBuffer cubeVertexBuffer = vertexBufferCreator.create(List.of(
                // West
                new VertexPositionColorNormal(new Vector3f(0, 0, 0), colorX, new Vector3f(-1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 0), colorX, new Vector3f(-1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 1), colorX, new Vector3f(-1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 0, 1), colorX, new Vector3f(-1, 0, 0)),

                // East
                new VertexPositionColorNormal(new Vector3f(1, 0, 0), colorX, new Vector3f(1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 1), colorX, new Vector3f(1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 1), colorX, new Vector3f(1, 0, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 0), colorX, new Vector3f(1, 0, 0)),

                // North
                new VertexPositionColorNormal(new Vector3f(0, 0, 0), colorY, new Vector3f(0, 0, -1)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 0), colorY, new Vector3f(0, 0, -1)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 0), colorY, new Vector3f(0, 0, -1)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 0), colorY, new Vector3f(0, 0, -1)),

                // South
                new VertexPositionColorNormal(new Vector3f(0, 0, 1), colorY, new Vector3f(0, 0, 1)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 1), colorY, new Vector3f(0, 0, 1)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 1), colorY, new Vector3f(0, 0, 1)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 1), colorY, new Vector3f(0, 0, 1)),

                // Down
                new VertexPositionColorNormal(new Vector3f(0, 0, 0), colorZ, new Vector3f(0, -1, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 0, 1), colorZ, new Vector3f(0, -1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 1), colorZ, new Vector3f(0, -1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 0, 0), colorZ, new Vector3f(0, -1, 0)),

                // Up
                new VertexPositionColorNormal(new Vector3f(0, 1, 0), colorZ, new Vector3f(0, 1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 0), colorZ, new Vector3f(0, 1, 0)),
                new VertexPositionColorNormal(new Vector3f(1, 1, 1), colorZ, new Vector3f(0, 1, 0)),
                new VertexPositionColorNormal(new Vector3f(0, 1, 1), colorZ, new Vector3f(0, 1, 0))
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
//        Texture2DCreator texture2DCreator = new Texture2DCreator();
//        Texture2D depthBuffer = texture2DCreator.create(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT,
//                                                        TextureInternalFormat.DEPTH_COMPONENT, PixelDataFormat.DEPTH_COMPONENT, PixelDataType.FLOAT);

        Texture2DCreator texture2DCreator = mGraphicsManager.getTexture2DCreator();
        Texture2D depthBuffer = texture2DCreator.create(SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT,
                                                        TextureInternalFormat.DEPTH_COMPONENT32,
                                                        PixelDataFormat.DEPTH_COMPONENT, PixelDataType.FLOAT,
                                                        new TextureParameterWrapS(TextureParameterWrap.Values.CLAMP_TO_BORDER),
                                                        new TextureParameterWrapT(TextureParameterWrap.Values.CLAMP_TO_BORDER),
                                                        new TextureParameterBorderColor(new Color(1.0F, 1.0F, 1.0F, 1.0F)));
        mShadowMapFramebuffer = framebufferCreator.create(depthBuffer);

        Texture2D colorTexture = texture2DCreator.create(mWindow.getWidth(), mWindow.getHeight(),
                                                         TextureInternalFormat.RGB8,
                                                         PixelDataFormat.RGB, PixelDataType.UNSIGNED_BYTE);
        mOrthographicCameraViewFramebuffer = framebufferCreator.create(colorTexture);

        Texture2D hdrDepthTexture = texture2DCreator.create(mWindow.getWidth(), mWindow.getHeight(),
                                                            TextureInternalFormat.DEPTH_COMPONENT24,
                                                            PixelDataFormat.DEPTH_COMPONENT, PixelDataType.FLOAT);
        Texture2D hdrColorTexture = texture2DCreator.create(mWindow.getWidth(), mWindow.getHeight(),
                                                            TextureInternalFormat.RGB16F,
                                                            PixelDataFormat.RGB, PixelDataType.FLOAT);
        mHDRFramebuffer = framebufferCreator.create(hdrColorTexture, hdrDepthTexture);

        PlayerCreator playerCreator = new PlayerCreator(mEntityManager);
        mPlayer = playerCreator.create();
        mPlayer.setPosition(new Vector3f(40, 40, 40));
        mPlayer.addRotation(new Vector3f(MathHelper.toRadians(-30), MathHelper.toRadians(45), 0));

        mMousePositionCallbackDispatcher.addDisabledCursorCallback(new MouseRotationController(mPlayer.getRotationComponent(), 2.5F));
    }

    @Override
    public void update(GameTime gameTime) {
//        Logger.infoCore("Position: " + mOrthographicCamera.getPosition() +
//                        " Rotation: " + MathHelper.toDegrees(mOrthographicCamera.getRotation().X));
        mPerspectiveCamera.setPosition(mPlayer.getPosition());
        mPerspectiveCamera.setRotation(mPlayer.getRotation());
    }

    boolean updateExposure = false;
    float exposure = 1;

    void renderScene() {
        mRenderer.bindMesh(mCubeMesh);
        mRenderer.setModelMatrix(Matrix4f.multiply(Matrix4f.createTranslation(new Vector3f(-10, 20, -10)), Matrix4f.createScale(20)));
        mRenderer.render();

        mRenderer.setModelMatrix(Matrix4f.multiply(Matrix4f.createTranslation(new Vector3f(-500, 0, -500)),
                                                   Matrix4f.createScale(new Vector3f(1000, 1, 1000))));
        mRenderer.render();

//        mRenderer.setModelMatrix(Matrix4f.multiply(Matrix4f.createTranslation(new Vector3f(0, 0, -100)), Matrix4f.createScale(new Vector3f(200, 200, 1))));
//        mRenderer.render();
    }

    float degrees = 0;

    @Override
    public void render(float alpha) {
        mRenderer.clear(new Color(0.392F, 0.584F, 0.929F));
        int i = 0;
        for (ModelMesh modelMesh : mCubeModel.getMeshes()) {
            ++i;
            if (i == 2) {
                degrees += 0.05F;
                modelMesh.setModelMatrix(Matrix4f.createRotationX(MathHelper.toRadians(degrees)));
            }
            mRenderer.bindMesh(modelMesh.getMesh());
            mRenderer.setEffect(mBasicEffect);
            mRenderer.setViewProjectionMatrix(mPerspectiveCamera.getViewProjectionMatrix());
            mRenderer.setModelMatrix(Matrix4f.multiply(modelMesh.getModelMatrix(), Matrix4f.createScale(new Vector3f(10, 10, 10))));
            mRenderer.render();
        }

//        try {
//            glViewport(0, 0, SHADOW_MAP_WIDTH, SHADOW_MAP_HEIGHT);
//            mRenderer.bindFramebuffer(mShadowMapFramebuffer);
//            mRenderer.clear(new Color(0.392F, 0.584F, 0.929F, 1.0F));
//            mRenderer.setEffect(mColorEffect);
//            mRenderer.setViewProjectionMatrix(mOrthographicCamera.getViewProjectionMatrix());
//            renderScene();
//            mRenderer.unbindFramebuffer();
//
//            glViewport(0, 0, mWindow.getWidth(), mWindow.getHeight());
//            IShader shadowMappingVertexShader = mShadowMappingEffect.getVertexShader();
//            shadowMappingVertexShader.getUniformController().setMatrix4f("u_LightViewProjection",
//                                                                         mOrthographicCamera.getViewProjectionMatrix());
//            PhongFragmentShader shadowMappingFragmentShader = new PhongFragmentShader(mShadowMappingEffect.getFragmentShader());
//            shadowMappingFragmentShader.setLightPosition(mLightPosition);
//            shadowMappingFragmentShader.setLightColor(mLightColor);
//            shadowMappingFragmentShader.setLightAttenuationIntensity(0.001F);
//            shadowMappingFragmentShader.setAmbientIntensity(0.2F);
//            shadowMappingFragmentShader.setCameraPosition(mPerspectiveCamera.getPosition());
//
//            mRenderer.bindFramebuffer(mHDRFramebuffer);
//            mRenderer.bindTexture(mShadowMapFramebuffer.getDepthBuffer());
//            mRenderer.clear(new Color(0.392F, 0.584F, 0.929F, 1.0F));
//            mRenderer.setEffect(mShadowMappingEffect);
//            mRenderer.setViewProjectionMatrix(mPerspectiveCamera.getViewProjectionMatrix());
//            renderScene();
//            mRenderer.unbindFramebuffer();
//
//            mHDRFragmentShader.getUniformController().setFloat("u_Exposure", 0.1F);
//            mSpriteRenderer.beginScene(m2DCamera, mHDRFragmentShader);
//            mSpriteRenderer.render(new Sprite(mHDRFramebuffer.getColorBuffer()));
//            mSpriteRenderer.endScene();
//        } catch (ShaderNotSetException e) {
//            e.printStackTrace();
//        }
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
                Vector3f vector3f = Vector3f.createLookAt(mPlayer.getRotation().X, mPlayer.getRotation().Y);
                vector3f.normalize();
                mPlayer.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_A), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(0,
                                                          mPlayer.getRotation().Y - MathHelper.PI / 2);
                vector3f.normalize();
                mPlayer.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_S), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(mPlayer.getRotation().X,
                                                          mPlayer.getRotation().Y);
                vector3f.negate();
                vector3f.normalize();
                mPlayer.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });
        mKeymap.add(new Key(GLFW_KEY_D), new IKeyAction() {
            @Override
            public void onKeyDown() {
                Vector3f vector3f = Vector3f.createLookAt(0,
                                                          mPlayer.getRotation().Y + MathHelper.PI / 2);
                vector3f.normalize();
                mPlayer.addPosition(vector3f);
            }

            @Override
            public void onKeyUp() {
            }
        });

        mKeymap.add(new Key(GLFW_KEY_UP), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(0, 0, -1)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_DOWN), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(0, 0, 1)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_LEFT), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(-1, 0, 0)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_RIGHT), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(1, 0, 0)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_SPACE), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(0, 1, 0)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_LEFT_SHIFT), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(0, -1, 0)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_SPACE), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setPosition(mOrthographicCamera.getPosition().add(new Vector3f(0, 1, 0)));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_O), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setRotation(new Vector3f(mOrthographicCamera.getRotation().X + MathHelper.toRadians(-1F),
                                                             0, 0));
            }

            @Override
            public void onKeyUp() {

            }
        });

        mKeymap.add(new Key(GLFW_KEY_L), new IKeyAction() {
            @Override
            public void onKeyDown() {
                mOrthographicCamera.setRotation(new Vector3f(mOrthographicCamera.getRotation().X + MathHelper.toRadians(1F),
                                                             0, 0));
            }

            @Override
            public void onKeyUp() {

            }
        });
    }
}
