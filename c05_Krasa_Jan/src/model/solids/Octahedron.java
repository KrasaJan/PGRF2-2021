package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Octahedron extends Solid {

    public Octahedron(Boolean filled) {

        vertexBuffer.add(new Vertex(new Point3D(1, 1, 0), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(1, -1, 0), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 0), new Col(0, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, 0), new Col(0, 0, 255)));

        vertexBuffer.add(new Vertex(new Point3D(0, 0, 1), new Col(255, 0, 255)));
        vertexBuffer.add(new Vertex(new Point3D(0, 0, -1), new Col(255, 0, 0)));

        if (!filled) {
            addIndices(0, 1, 2, 3);
            elementBuffer.add(new Element(TopologyType.LINELOOP,0 , 4));
            addIndices(0, 4, 1, 2, 4, 3);
            addIndices(0, 5, 1, 2, 5, 3);
            elementBuffer.add(new Element(TopologyType.LINESTRIP, 4, 12));
        }
        if (filled) {
            addIndices(4, 0, 1, 2, 3, 0);
            elementBuffer.add(new Element(TopologyType.TRIANGLEFAN, 0, 6));
            addIndices(5, 0, 1, 2, 3, 0);
            elementBuffer.add(new Element(TopologyType.TRIANGLEFAN, 6, 6));
        }

    }

}
