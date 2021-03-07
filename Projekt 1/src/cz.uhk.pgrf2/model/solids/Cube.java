package model.solids;

import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Cube extends Solid {
    //TODO Set Colors
    public Cube() {

        vertexBuffer.add(new Vertex(new Point3D(1, -1, 1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, 1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, 1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 1), new Col(0,0,0)));

        vertexBuffer.add(new Vertex(new Point3D(1, -1, -1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(1, 1, -1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, 1, -1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(-1, -1, -1), new Col(0,0,0)));

        addIndices(0, 1, 1, 2, 2, 3, 3, 0);
        addIndices(4, 5, 5, 6, 6, 7, 7, 4);
        addIndices(0, 4, 1, 5, 2, 6, 3, 7);

    }
}
