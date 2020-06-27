package com.maple.entity.component.model;

import com.maple.entity.IEntity;
import com.maple.entity.component.system.IComponentSystem;
import com.maple.game.runner.GameTime;

import java.util.ArrayList;
import java.util.List;

public class ModelComponentSystem implements IComponentSystem {
    private List<IEntity> mEntities;

    public ModelComponentSystem() {
        mEntities = new ArrayList<>();
    }

    @Override
    public void add(IEntity entity) {
        mEntities.add(entity);
    }

    @Override
    public void remove(IEntity entity) {
        mEntities.remove(entity);
    }

    @Override
    public void update(GameTime gameTime) {
    }

    public List<IEntity> getEntities() {
        return mEntities;
    }
}
