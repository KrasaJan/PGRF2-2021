package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Axis extends Solid {

    public Axis() {

        transformable = false;

        vertexBuffer.add(new Vertex(new Point3D(0, 0, 0), new Col(255, 255, 255)));
        vertexBuffer.add(new Vertex(new Point3D(3, 0, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 3, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 3), new Col(0, 0, 255)));

        addIndices(0, 1);
        addIndices(0, 2);
        addIndices(0, 3);

        elementBuffer.add(new Element(TopologyType.LINE, 0, 6));

        vertexBuffer.add(new Vertex(new Point3D(2.8, 0, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, 0.1, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, -0.1, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, 0, 0.1), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, 0, -0.1), new Col(255, 0, 0)));

        addIndices(4, 5, 5, 1, 4, 6, 6, 1);
        addIndices(4, 7, 7, 1, 4, 8, 8, 1);

        elementBuffer.add(new Element(TopologyType.LINE, 6, 16));

        vertexBuffer.add(new Vertex(new Point3D(0, 2.8, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0.1, 2.8, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(-0.1, 2.8, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 2.8, 0.1), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 2.8, -0.1), new Col(0, 255, 0)));

        addIndices(9, 10, 10, 2, 9, 11, 11, 2);
        addIndices(9, 12, 12, 2, 9, 13, 13, 2);

        elementBuffer.add(new Element(TopologyType.LINE, 22, 16));

        vertexBuffer.add(new Vertex(new Point3D(0, 0, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0.1, 0, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(-0.1, 0, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0.1, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, -0.1, 2.8), new Col(0, 0, 255)));

        addIndices(14, 15, 15, 3, 14, 16, 16, 3);
        addIndices(14, 17, 17, 3, 14, 18, 18, 3);

        elementBuffer.add(new Element(TopologyType.LINE, 38, 16));
    }

}
