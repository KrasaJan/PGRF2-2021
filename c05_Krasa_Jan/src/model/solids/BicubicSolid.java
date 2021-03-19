package model.solids;

import model.Element;
import model.TopologyType;
import model.Vertex;
import transforms.*;

public class BicubicSolid extends Solid {

    public BicubicSolid() {

        final int ACCURACY = 50;

        Point3D p0 = new Point3D(0, 1, 0);
        Point3D p1 = new Point3D(-1, -0.5, 0.3);
        Point3D p2 = new Point3D(0.3, 0.8, -0.5);
        Point3D p3 = new Point3D(0, -1, 0);

        Point3D p4 = new Point3D(1, 0, 0);
        Point3D p5 = new Point3D(-0.5, 0.3, -1);
        Point3D p6 = new Point3D(0.8, -0.5, 0.3);
        Point3D p7 = new Point3D(-1, 0, 0);

        Point3D p8 = new Point3D(-1,-0.8,0.6);
        Point3D p9 = new Point3D(0.6,1.2,-0.3);
        Point3D p10 = new Point3D(0.5,-0.8,-0.4);
        Point3D p11 = new Point3D(0.9,0.5,-0.5);

        Point3D p12 = new Point3D(0.7,0.1,-0.9);
        Point3D p13 = new Point3D(0.6,0.7,0.3);
        Point3D p14 = new Point3D(-0.8,-0.4,-0.6);
        Point3D p15 = new Point3D(0.7,-0.3,0.7);

        Point3D[] points = { p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15 };

                Mat4 baseMat = Cubic.BEZIER;
        Bicubic bicubic = new Bicubic(baseMat, points);

        for (int i = 0; i < ACCURACY; i++) {
            for (int j = 0; j < ACCURACY; j++) {
                vertexBuffer.add(new Vertex(new Point3D(bicubic.compute((double) i / ACCURACY, (double) j / ACCURACY)), new Col(128, 128, 128)));
                /* Creates separate cubics */
                if (j != 0) {
                    indexBuffer.add((j - 1) + (ACCURACY * i));
                indexBuffer.add((j) + (ACCURACY * i));
                }
                /* Connects vertexes within the cubics */
                if (i != 0) {
                    indexBuffer.add(j + (ACCURACY) * (i - 1));
                    indexBuffer.add(j + (ACCURACY * i));
                }
            }
        }

        elementBuffer.add(new Element(TopologyType.LINE,0,ACCURACY*ACCURACY));

    }

}
