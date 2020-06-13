package com.maple.renderer.sprite;

import com.maple.graphics.texture.Texture2D;
import com.maple.math.MathHelper;
import com.maple.math.Vector2f;
import com.maple.math.Vector3f;
import com.maple.utils.Color;

public class Sprite {
    private Texture2D mTexture;

    private Vector3f mPosition;
    private float mRoll;
    private Vector2f mScale;
    private Vector2f mDimensions;
    private Vector2f mMaskPosition;
    private Vector2f mMaskDimensions;
    private Color mColor;

    public Sprite(Texture2D texture) {
        mTexture = texture;
        mPosition = new Vector3f();
        mRoll = 0.0F;
        mScale = new Vector2f(1, 1);
        mDimensions = new Vector2f(mTexture.getWidth(), mTexture.getHeight());
        mMaskPosition = new Vector2f();
        mMaskDimensions = new Vector2f(1, 1);
        mColor = new Color(255, 255, 255);
    }

    public Texture2D getTexture() {
        return mTexture;
    }

    public Vector3f getPosition() {
        return mPosition;
    }

    public Sprite setPosition(int x, int y, float z) {
        mPosition.X = x;
        mPosition.Y = y;
        mPosition.Z = z;
        return this;
    }

    public Sprite setPosition(int x, int y) {
        return setPosition(x, y, mPosition.Z);
    }

    public float getRoll() {
        return mRoll;
    }

    public Sprite setRoll(float roll) {
        mRoll = roll;
        return this;
    }

    public Vector2f getScale() {
        return mScale;
    }

    public Sprite setScale(float xScale, float yScale) {
        mScale.X = MathHelper.clamp(xScale, 0, Integer.MAX_VALUE);
        mScale.Y = MathHelper.clamp(yScale, 0, Integer.MAX_VALUE);
        return this;
    }

    public Sprite setScale(float scale) {
        return setScale(scale, scale);
    }

    public Vector2f getDimensions() {
        return mDimensions;
    }

    public float getWidth() {
        return mDimensions.X;
    }

    public float getHeight() {
        return mDimensions.Y;
    }

    public Sprite setDimensions(int width, int height) {
        mDimensions.X = MathHelper.clamp(width, 0, Integer.MAX_VALUE);
        mDimensions.Y = MathHelper.clamp(height, 0, Integer.MAX_VALUE);
        return this;
    }

    public Vector2f getMaskPosition() {
        return mMaskPosition;
    }

    public Sprite setMaskPosition(int x, int y) {
        mMaskPosition.X = MathHelper.clamp(x / (float) mTexture.getWidth(), 0, 1);
        mMaskPosition.Y = MathHelper.clamp(y / (float) mTexture.getHeight(), 0, 1);
        return this;
    }


    public Vector2f getMaskDimensions() {
        return mMaskDimensions;
    }

    public Sprite setMaskDimensions(int width, int height) {
        mMaskDimensions.X = MathHelper.clamp(width / (float) mTexture.getWidth(), 0, 1);
        mMaskDimensions.Y = MathHelper.clamp(height / (float) mTexture.getHeight(), 0, 1);
        return this;
    }

    public Color getColor() {
        return mColor;
    }

    public Sprite setColor(Color color) {
        mColor = color;
        return this;
    }
}
