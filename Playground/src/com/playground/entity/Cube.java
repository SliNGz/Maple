package com.playground.entity;

import com.maple.entity.IEntity;
import com.maple.entity.component.IComponent;

import java.util.Map;

public class Cube implements IEntity {
    private IEntity mEntity;

    public Cube(IEntity entity) {
        mEntity = entity;
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
}
