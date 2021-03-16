package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Tetrahedron extends Solid {
    public Tetrahedron(TopologyType topologyType) {

        vertexBuffer.add(new Vertex(new Point3D(-1, 1, -1), new Col(255,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, -1), new Col(255,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(0, -1, -1), new Col(255,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, 2), new Col(0,0,255)));

        if (topologyType == TopologyType.LINE) {
            addIndices(0, 1, 1, 2, 2, 0);
            addIndices(0, 3, 1, 3, 2, 3);
            elementBuffer.add(new Element(topologyType, 0, 12));
        }
        if (topologyType == TopologyType.TRIANGLE) {
            addIndices(0, 1, 2);
            addIndices(0, 1, 3);
            addIndices(0, 2, 3);
            addIndices(1, 2, 3);
            elementBuffer.add(new Element(topologyType, 0, 12));
        }

    }

}
