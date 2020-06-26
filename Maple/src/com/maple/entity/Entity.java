package com.maple.entity;

import com.maple.entity.component.IComponent;

import java.util.Map;
import java.util.Objects;

public class Entity implements IEntity {
    private int mId;
    private Map<Class<? extends IComponent>, IComponent> mComponents;

    public Entity(int id, Map<Class<? extends IComponent>, IComponent> components) {
        mId = id;
        mComponents = components;
    }

    public int getId() {
        return mId;
    }

    public Map<Class<? extends IComponent>, IComponent> getComponents() {
        return mComponents;
    }

    public <T extends IComponent> T getComponent(Class<T> componentType) {
        return (T) mComponents.get(componentType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return mId == entity.mId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId);
    }
}
