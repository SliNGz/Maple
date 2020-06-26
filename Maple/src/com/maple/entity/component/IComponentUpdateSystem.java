package com.maple.entity.component;

import com.maple.game.runner.GameTime;

public interface IComponentUpdateSystem<T extends IComponent> extends IComponentSystem<T> {
    void update(GameTime gameTime);
}
