package com.maple.entity.component.system;

import com.maple.entity.IEntity;
import com.maple.game.runner.GameTime;

public interface IComponentSystem {
    void add(IEntity entity);

    void remove(IEntity entity);

    void update(GameTime gameTime);
}
