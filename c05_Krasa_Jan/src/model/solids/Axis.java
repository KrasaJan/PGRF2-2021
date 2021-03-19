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

        vertexBuffer.add(new Vertex(new Point3D(2.8, 0.1, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, -0.1, 0), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, 0, 0.1), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(2.8, 0, -0.1), new Col(255, 0, 0)));

        addIndices(1, 4, 5);
        elementBuffer.add(new Element(TopologyType.LINELOOP, 6, 3));
        addIndices(1, 6, 7);
        elementBuffer.add(new Element(TopologyType.LINELOOP, 9, 3));

        vertexBuffer.add(new Vertex(new Point3D(0.1, 2.8, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(-0.1, 2.8, 0), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 2.8, 0.1), new Col(0, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 2.8, -0.1), new Col(0, 255, 0)));

        addIndices(2, 8, 9);
        elementBuffer.add(new Element(TopologyType.LINELOOP, 12, 3));
        addIndices(2, 10, 11);
        elementBuffer.add(new Element(TopologyType.LINELOOP, 15, 3));

        vertexBuffer.add(new Vertex(new Point3D(0.1, 0, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(-0.1, 0, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0.1, 2.8), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, -0.1, 2.8), new Col(0, 0, 255)));

        addIndices(3, 12, 13);
        elementBuffer.add(new Element(TopologyType.LINELOOP, 18, 3));
        addIndices(3, 14, 15);
        elementBuffer.add(new Element(TopologyType.LINELOOP, 21, 3));

    }

}
