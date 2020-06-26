package com.playground.entity;

import com.maple.entity.IEntity;
import com.maple.entity.component.IComponent;
import com.maple.entity.component.PositionComponent;
import com.maple.entity.component.RotationComponent;
import com.maple.math.Vector3f;

import java.util.Map;

public class Player implements IEntity {
    private IEntity mEntity;
    private PositionComponent mPositionComponent;
    private RotationComponent mRotationComponent;

    public Player(IEntity entity) {
        mEntity = entity;
        mPositionComponent = entity.getComponent(PositionComponent.class);
        mRotationComponent = entity.getComponent(RotationComponent.class);
    }

    @Override
    public int getId() {
        return mEntity.getId();
    }

    @Override
    public Map<Class<? extends IComponent>, IComponent> getComponents() {
        return mEntity.getComponents();
    }

    @Override
    public <T extends IComponent> T getComponent(Class<T> componentType) {
        return mEntity.getComponent(componentType);
    }

    public PositionComponent getPositionComponent() {
        return mPositionComponent;
    }

    public Vector3f getPosition() {
        return mPositionComponent.get();
    }

    public void setPosition(Vector3f position) {
        mPositionComponent.set(position);
    }

    public void addPosition(Vector3f position) {
        mPositionComponent.add(position);
    }

    public RotationComponent getRotationComponent() {
        return mRotationComponent;
    }

    public Vector3f getRotation() {
        return mRotationComponent.get();
    }

    public void setRotation(Vector3f rotation) {
        mRotationComponent.set(rotation);
    }

    public void addRotation(Vector3f rotation) {
        mRotationComponent.add(rotation);
    }

}
