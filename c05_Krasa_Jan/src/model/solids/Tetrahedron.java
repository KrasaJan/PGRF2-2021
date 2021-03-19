package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Tetrahedron extends Solid {
    public Tetrahedron(Boolean filled) {

        vertexBuffer.add(new Vertex(new Point3D(-1, 1, -1), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(1, -1, -1), new Col(255, 0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 1), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, 1), new Col(0, 0, 255)));

        if (!filled) {
            addIndices(0, 1, 1, 2, 2, 0);
            addIndices(0, 3, 1, 3, 2, 3);
            elementBuffer.add(new Element(TopologyType.LINE, 0, 12));
        }
        if (filled) {
            addIndices(3, 2, 1, 0, 3, 2);
            elementBuffer.add(new Element(TopologyType.TRIANGLESTRIP, 0, 6));
        }

    }

}
