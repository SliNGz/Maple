package com.maple.entity.component.system;

import com.maple.entity.IEntity;
import com.maple.entity.component.IComponent;
import com.maple.entity.component.exceptions.MissingComponentSystemException;
import com.maple.game.runner.GameTime;

import java.util.HashMap;
import java.util.Map;

public class ComponentSystemsManager {
    private Map<Class<? extends IComponent>, IComponentSystem> mComponentSystems;

    public ComponentSystemsManager() {
        mComponentSystems = new HashMap<>();
    }

    public void addComponentSystem(Class<? extends IComponent> componentType, IComponentSystem componentSystem) {
        mComponentSystems.put(componentType, componentSystem);
    }

    public IComponentSystem get(Class<? extends IComponent> componentType) throws MissingComponentSystemException {
        IComponentSystem componentSystem = mComponentSystems.get(componentType);
        if (componentSystem == null) {
            throw new MissingComponentSystemException();
        }

        return componentSystem;
    }

    public <T extends IComponent> void addEntity(IEntity entity) {
        Map<Class<? extends IComponent>, IComponent> components = entity.getComponents();
        for (Class<? extends IComponent> componentType : components.keySet()) {
            try {
                IComponentSystem componentSystem = get(componentType);
                componentSystem.add(entity);
            } catch (MissingComponentSystemException ignored) {
            }
        }
    }

    public <T extends IComponent> void removeEntity(IEntity entity) {
        Map<Class<? extends IComponent>, IComponent> components = entity.getComponents();
        for (Class<? extends IComponent> componentType : components.keySet()) {
            try {
                IComponentSystem componentSystem = get(componentType);
                componentSystem.remove(entity);
            } catch (MissingComponentSystemException ignored) {
            }
        }
    }

    public void update(GameTime gameTime) {
        for (IComponentSystem componentSystem : mComponentSystems.values()) {
            componentSystem.update(gameTime);
        }
    }
}
