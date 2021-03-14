package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid {

    public Cube(TopologyType topologyType) {

        vertexBuffer.add(new Vertex(new Point3D(1, -1, 1), new Col(255,164,0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, 1), new Col(255,164,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, 1), new Col(255,164,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 1), new Col(255,164,0)));

        vertexBuffer.add(new Vertex(new Point3D(1, -1, -1), new Col(255,255,0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, -1), new Col(255,255,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, -1), new Col(255,255,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, -1), new Col(255,255,0)));


        if (topologyType == TopologyType.LINE) {
            addIndices(0, 1, 1, 2, 2, 3, 3, 0);
            addIndices(4, 5, 5, 6, 6, 7, 7, 4);
            addIndices(0, 4, 1, 5, 2, 6, 3, 7);
            elementBuffer.add(new Element(topologyType, 0, 24));
        }
        if (topologyType == TopologyType.TRIANGLE) {
            addIndices(1, 0, 3, 1, 2, 3);
            addIndices(0, 1, 5, 0, 4, 5);
            addIndices(4, 5, 6, 4, 7, 6);
            addIndices(7, 3, 2, 7, 6, 2);
            addIndices(3, 0, 7, 0, 4, 7);
            addIndices(2, 1, 6, 1, 5, 6);
            elementBuffer.add(new Element(topologyType, 0, 36));
        }


    }
}
