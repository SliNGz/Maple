package com.maple.entity.component;

import com.maple.entity.IEntity;

import java.util.HashMap;
import java.util.Map;

public class RotationComponentManager implements IEntityComponentManager<RotationComponent> {
    private Map<IEntity, RotationComponent> mComponents;

    public RotationComponentManager() {
        mComponents = new HashMap<>();
    }

    @Override
    public void add(IEntity entity, RotationComponent component) {
        mComponents.put(entity, component);
    }

    @Override
    public RotationComponent get(IEntity entity) {
        return mComponents.get(entity);
    }

    @Override
    public void remove(IEntity entity) {
        mComponents.remove(entity);
    }

    @Override
    public void update() {
    }
}
