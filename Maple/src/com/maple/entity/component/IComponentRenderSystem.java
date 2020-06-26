package com.maple.entity.component;

public interface IComponentRenderSystem<T extends IComponent> extends IComponentSystem<T> {
    void render(float alpha);
}
