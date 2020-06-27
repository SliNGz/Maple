package com.maple.entity.component.model;

import com.maple.entity.component.IComponent;
import com.maple.renderer.model.Model;

public class ModelComponent implements IComponent {
    private Model mModel;

    public ModelComponent(Model model) {
        mModel = model;
    }

    public Model getModel() {
        return mModel;
    }

    public void setModel(Model model) {
        mModel = model;
    }
}
