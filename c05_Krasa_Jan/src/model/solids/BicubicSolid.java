package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.*;

import java.util.ArrayList;
import java.util.List;

public class BicubicSolid extends Solid {

    public BicubicSolid(TopologyType topologyType) {

        List<Point3D> points = new ArrayList<>();
        final int ACCURACY = 50;

        points.add(new Point3D(0, 1, 0));
        points.add(new Point3D(-1, -0.5, 0.3));
        points.add(new Point3D(0.3, 0.8, -0.5));
        points.add(new Point3D(0, -1, 0));

        points.add(new Point3D(1, 0, 0));
        points.add(new Point3D(-0.5, 0.3, -1));
        points.add(new Point3D(0.8, -0.5, 0.3));
        points.add(new Point3D(-1, 0, 0));

        points.add(new Point3D(-1,-0.8,0.6));
        points.add(new Point3D(0.6,1.2,-0.3));
        points.add(new Point3D(0.5,-0.8,-0.4));
        points.add(new Point3D(0.9,0.5,-0.5));

        points.add(new Point3D(0.7,0.1,-0.9));
        points.add(new Point3D(0.6,0.7,0.3));
        points.add(new Point3D(-0.8,-0.4,-0.6));
        points.add(new Point3D(0.7,-0.3,0.7));

        Mat4 baseMat = Cubic.BEZIER;
        Bicubic bicubic = new Bicubic(baseMat,
                points.get(0),points.get(1),points.get(2),points.get(3),
                points.get(4),points.get(5),points.get(6),points.get(7),
                points.get(8),points.get(9),points.get(10),points.get(11),
                points.get(12),points.get(13),points.get(14),points.get(15));

        for (int i = 0; i < ACCURACY; i++) {
            for (int j = 0; j < ACCURACY; j++) {
                vertexBuffer.add(new Vertex(new Point3D(bicubic.compute((double) i/ACCURACY, (double) j/ACCURACY)), new Col(128,128,128)));
                /* Creates separate cubics */
                if (j != 0) {
                    indexBuffer.add((j - 1) + (ACCURACY * i));
                    indexBuffer.add((j) + (ACCURACY * i));
                }
                /* Connects vertexes within the cubics */
                if ((j != 0)&&(i != 0)) {
                    indexBuffer.add((j - 1) + (ACCURACY * i));
                    indexBuffer.add(j - 1 + (ACCURACY) * (i - 1));
                }
            }
        }

        elementBuffer.add(new Element(TopologyType.LINE,0,ACCURACY*ACCURACY));

    }

}
