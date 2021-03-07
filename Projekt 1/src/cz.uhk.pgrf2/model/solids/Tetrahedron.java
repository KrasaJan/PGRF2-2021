package model.solids;

import model.Vertex;
import transforms.Col;
import transforms.Point3D;

public class Tetrahedron extends Solid {
    //TODO Set Colors
    public Tetrahedron() {

        vertexBuffer.add(new Vertex(new Point3D(-1, -1, 1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(1, -1, 1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(0, -1, -1), new Col(0,0,0)));
        vertexBuffer.add(new Vertex(new Point3D(0, 1, 0), new Col(0,0,0)));

        addIndices(0, 1, 1, 2, 2, 0);
        addIndices(0, 3, 1, 3, 2, 3);

    }

}
