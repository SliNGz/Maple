package com.maple.entity.component;

import com.maple.entity.IEntity;

import java.util.HashMap;
import java.util.Map;

public class PositionComponentManager implements IEntityComponentManager<PositionComponent> {
    private Map<IEntity, PositionComponent> mComponents;

    public PositionComponentManager() {
        mComponents = new HashMap<>();
    }

    @Override
    public void add(IEntity entity, PositionComponent component) {
        mComponents.put(entity, component);
    }

    @Override
    public PositionComponent get(IEntity entity) {
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
