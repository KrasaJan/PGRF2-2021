package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Octahedron extends Solid {

    public Octahedron(TopologyType topologyType) {

        vertexBuffer.add(new Vertex(new Point3D(1, 1, 0), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(1, -1, 0), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 0), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, 0), new Col(0, 0, 255)));

        vertexBuffer.add(new Vertex(new Point3D(0, 0, 1), new Col(255, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, -1), new Col(255, 0, 0)));

        if (topologyType == TopologyType.LINE) {
            addIndices(0, 1, 1, 2, 2, 3, 3, 0);
            addIndices(0, 4, 1, 4, 2, 4, 3, 4);
            addIndices(0, 5, 1, 5, 2, 5, 3, 5);
            elementBuffer.add(new Element(topologyType, 0, 24));
        }
        if (topologyType == TopologyType.TRIANGLE) {
            addIndices(0, 1, 4);
            addIndices(0, 1, 5);
            addIndices(1, 2, 4);
            addIndices(1, 2, 5);
            addIndices(2, 3, 4);
            addIndices(2, 3, 5);
            addIndices(0, 3, 4);
            addIndices(0, 3, 5);
            elementBuffer.add(new Element(topologyType, 0, 24));
        }

    }

}
