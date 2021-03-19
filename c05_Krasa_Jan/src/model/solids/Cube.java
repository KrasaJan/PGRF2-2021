package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.Col;
import transforms.Point3D;
import transforms.Vec2D;

public class Cube extends Solid {

    public Cube(Boolean filled) {

        vertexBuffer.add(new Vertex(new Point3D(1, -1, 1), new Col(255, 167, 0), new Vec2D(1, 0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, 1), new Col(255, 167, 0), new Vec2D(0, 0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, 1), new Col(255, 167, 0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 1), new Col(255, 167, 0)));

        vertexBuffer.add(new Vertex(new Point3D(1, -1, -1), new Col(255, 255, 0), new Vec2D(1, 1)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, -1), new Col(255, 255, 0), new Vec2D(0, 1)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, -1), new Col(255, 255, 0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, -1), new Col(255, 255, 0)));

        if (!filled) {
            addIndices(0, 1, 2, 3);
            elementBuffer.add(new Element(TopologyType.LINELOOP, 0, 4));
            addIndices(4, 5, 6, 7);
            elementBuffer.add(new Element(TopologyType.LINELOOP, 4, 4));
            addIndices(0, 4, 1, 5, 2, 6, 3, 7);
            elementBuffer.add(new Element(TopologyType.LINE, 8, 8));
        }
        if (filled) {
            addIndices(1, 0, 3, 1, 2, 3);
            addIndices(0, 1, 5, 0, 4, 5);
            addIndices(4, 5, 6, 4, 7, 6);
            addIndices(7, 3, 2, 7, 6, 2);
            addIndices(3, 0, 7, 0, 4, 7);
            addIndices(2, 1, 6, 1, 5, 6);
            elementBuffer.add(new Element(TopologyType.TRIANGLE, 0, 36));
        }

    }
}
