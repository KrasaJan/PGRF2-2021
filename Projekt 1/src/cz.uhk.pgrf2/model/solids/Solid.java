package model.solids;

import model.Element;
import model.Vertex;
import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Solid {

    final List<Vertex> vertexBuffer = new ArrayList<>();
    final List<Integer> indexBuffer = new ArrayList<>();
    final List<Element> elementBuffer = new ArrayList<>();

    private Mat4 model = new Mat4Identity();
    boolean transformable = true;

    final void addIndices(Integer... indices) {
        indexBuffer.addAll(Arrays.asList(indices));
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void setTransformable(boolean transformable) {
        this.transformable = transformable;
    }

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Element> getElementBuffer() {
        return elementBuffer;
    }

    public Mat4 getModel() {
        return model;
    }

    public boolean isTransformable() {
        return transformable;
    }


}
