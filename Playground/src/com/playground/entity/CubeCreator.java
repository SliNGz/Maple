package com.playground.entity;

import com.maple.entity.IEntityManager;
import com.maple.entity.component.PositionComponent;
import com.maple.entity.component.RotationComponent;
import com.maple.entity.component.ScaleComponent;
import com.maple.entity.component.model.ModelComponent;
import com.maple.math.Vector3f;
import com.maple.renderer.model.Model;

public class CubeCreator {
    private IEntityManager mEntityManager;
    private Model mModel;

    public CubeCreator(IEntityManager entityManager, Model model) {
        mEntityManager = entityManager;
        mModel = model;
    }

    public Cube create() {
        return new Cube(mEntityManager.createEntity(new PositionComponent(),
                                                    new RotationComponent(),
                                                    new ScaleComponent(new Vector3f(10, 10, 10)),
                                                    new ModelComponent(mModel)));
    }
}
