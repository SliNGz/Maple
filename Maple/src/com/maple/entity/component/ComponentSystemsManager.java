package com.maple.entity.component;

import com.maple.entity.IEntity;
import com.maple.entity.component.exceptions.ComponentTypeResolutionFailedException;
import com.maple.entity.component.exceptions.MissingComponentSystemException;
import com.maple.game.runner.GameTime;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentSystemsManager {
    private Map<Class<? extends IComponent>, IComponentSystem<? extends IComponent>> mComponentSystems;
    private List<IComponentUpdateSystem<? extends IComponent>> mComponentUpdateSystem;
    private List<IComponentRenderSystem<? extends IComponent>> mComponentRenderSystem;

    public ComponentSystemsManager() {
        mComponentSystems = new HashMap<>();
        mComponentUpdateSystem = new ArrayList<>();
        mComponentRenderSystem = new ArrayList<>();
    }

    private Class<? extends IComponent> getComponentType(IComponentSystem<? extends IComponent> componentSystem) {
        Class<?> componentSystemClass = componentSystem.getClass();

        for (Type genericInterface : componentSystemClass.getGenericInterfaces()) {
            ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
            Class<?> rawType = (Class<?>) parameterizedType.getRawType();

            if (IComponentSystem.class.isAssignableFrom(rawType)) {
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                return (Class<? extends IComponent>) typeArguments[0];
            }
        }

        throw new ComponentTypeResolutionFailedException();
    }

    private void addComponentSystem(IComponentSystem<? extends IComponent> componentSystem) {
        Class<? extends IComponent> componentType = getComponentType(componentSystem);
        mComponentSystems.put(componentType, componentSystem);

        if (componentSystem instanceof IComponentUpdateSystem) {
            mComponentUpdateSystem.add((IComponentUpdateSystem<? extends IComponent>) componentSystem);
        }

        if (componentSystem instanceof IComponentRenderSystem) {
            mComponentRenderSystem.add((IComponentRenderSystem<? extends IComponent>) componentSystem);
        }
    }

    public IComponentSystem<? extends IComponent> get(Class<? extends IComponent> componentType) throws MissingComponentSystemException {
        IComponentSystem<? extends IComponent> componentSystem = mComponentSystems.get(componentType);
        if (componentSystem == null) {
            throw new MissingComponentSystemException();
        }

        return componentSystem;
    }

    public <T extends IComponent> void addEntity(IEntity entity) {
        Map<Class<? extends IComponent>, IComponent> components = entity.getComponents();
        for (Map.Entry<Class<? extends IComponent>, IComponent> entry : components.entrySet()) {
            Class<? extends IComponent> componentType = entry.getKey();

            try {
                IComponentSystem<T> componentSystem = (IComponentSystem<T>) get(componentType);
                componentSystem.add(entity, (T) entry.getValue());
            } catch (MissingComponentSystemException ignored) {
            }
        }
    }

    public <T extends IComponent> void removeEntity(IEntity entity) {
        Map<Class<? extends IComponent>, IComponent> components = entity.getComponents();
        for (Map.Entry<Class<? extends IComponent>, IComponent> entry : components.entrySet()) {
            Class<? extends IComponent> componentType = entry.getKey();

            try {
                IComponentSystem<? extends IComponent> componentSystem = get(componentType);
                componentSystem.remove(entity);
            } catch (MissingComponentSystemException ignored) {
            }
        }
    }

    public void update(GameTime gameTime) {
        for (IComponentUpdateSystem<?> componentUpdateSystem : mComponentUpdateSystem) {
            componentUpdateSystem.update(gameTime);
        }
    }

    public void render(float alpha) {
        for (IComponentRenderSystem<?> componentRenderSystem : mComponentRenderSystem) {
            componentRenderSystem.render(alpha);
        }
    }
}
