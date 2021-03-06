package renderer;

import model.Element;
import model.TopologyType;
import model.Vertex;
import rasterize.DepthBuffer;
import rasterize.Raster;
import transforms.*;

import java.util.List;
import java.util.Optional;

public class RendererZBuffer implements GPURenderer {

    private final Raster<Integer> imageRaster;
    private final Raster<Double> depthBuffer;

    private Mat4 model, view, projection;

    public RendererZBuffer(Raster<Integer> imageRaster) {
        this.imageRaster = imageRaster;
        this.depthBuffer = new DepthBuffer(imageRaster);

        model = new Mat4Identity();
        view = new Mat4Identity();
        projection = new Mat4Identity();
    }

    @Override
    public void draw(List<Element> elements, List<Integer> indexBuffer, List<Vertex> vertexBuffer) {
        for (Element element : elements) {
            final TopologyType topologyType = element.getTopologyType();
            final int start = element.getStart();
            final int end = element.getEnd();

            if(topologyType == TopologyType.TRIANGLE) {
                for (int i = start; i < start + end; i += 3) {
                    final Integer i1 = indexBuffer.get(i);
                    final Integer i2 = indexBuffer.get(i + 1);
                    final Integer i3 = indexBuffer.get(i + 2);
                    final Vertex v1 = vertexBuffer.get(i1);
                    final Vertex v2 = vertexBuffer.get(i2);
                    final Vertex v3 = vertexBuffer.get(i3);
                    prepareTriangle(v1, v2, v3);
                }

            } else if (topologyType == TopologyType.LINE) {
                // TODO
            }
        }
    }

    private void prepareTriangle(Vertex v1, Vertex v2, Vertex v3) {

        // Transformation of points
        Vertex a = new Vertex(v1.getPoint().mul(model).mul(view).mul(projection), v1.getColor());
        Vertex b = new Vertex(v2.getPoint().mul(model).mul(view).mul(projection), v2.getColor());
        Vertex c = new Vertex(v3.getPoint().mul(model).mul(view).mul(projection), v3.getColor());

        // Cuts triangles completely outside projection view
        // TODO slide 93

        // Orders points according to Z (a.z > b.z > c.z)
        if (a.getZ() < b.getZ()) {
            Vertex aux = a;
            a = b;
            b = aux;
        }

        if (b.getZ() < c.getZ()) {
            Vertex aux = b;
            b = c;
            c = aux;
        }

        if (a.getZ() < b.getZ()) {
            Vertex aux = a;
            a = b;
            b = aux;
        }

        // a.Z is the biggest one => if it is smaller than is all are => there is nothing to display
        if (a.getZ() < 0) {
            return;
        } else if (b.getZ() < 0) {                          // a.Z is visible, rest are not.
            // subtract minimum, divide by range
            double t1 = (0 - a.getZ()) / (b.getZ() - a.getZ());
            // new point has Z coordinate 0
            Vertex ab = a.mul(1 - t1).add(b.mul(t1));

            double t2 = (0 - a.getZ()) / (c.getZ() - a.getZ());
            Vertex ac = a.mul(1 - t2).add(c.mul(t2));

            drawTriangle(a, ab, ac);

        } else if (c.getZ() < 0) {                          // Only c.Z is not visible.
            double t1 = (0 - b.getZ()) / (c.getZ() - b.getZ());
            Vertex bc = b.mul((1 - t1)).add(c.mul(t1));
            drawTriangle(a, b, bc);

            double t2 = (0 - a.getZ()) / (c.getZ() - a.getZ());
            Vertex ac = a.mul(1 - t2).add(c.mul(t2));
            drawTriangle(a, bc, ac);
        } else {                                            // Whole triangle is visible
            drawTriangle(a, b, c);
        }

    }

    private void drawTriangle(Vertex a, Vertex b, Vertex c) {
        // Dehomog
        Optional<Vertex> oA = a.dehomog();
        Optional<Vertex> oB = b.dehomog();
        Optional<Vertex> oC = c.dehomog();

        if (oA.isEmpty() || oB.isEmpty() || oC.isEmpty()) return;
        a = oA.get();
        b = oB.get();
        c = oC.get();

        // Transform to window
        a = transformToWindow(a);
        b = transformToWindow(b);
        c = transformToWindow(c);

        // Orders points according to Y (a.y < b.y < c.y)
        if (a.getY() > b.getY()) {
            Vertex aux = a;
            a = b;
            b = aux;
        }
        if (b.getY() > c.getY()) {
            Vertex aux = b;
            b = c;
            c = aux;
        }
        if (a.getY() > b.getY()) {
            Vertex aux = a;
            a = b;
            b = aux;
        }

        //Interpolation according to Y
        // A -> B
        long start = (long) Math.max(Math.ceil(a.getY()), 0);
        double end = Math.min(b.getY(), imageRaster.getHeight() - 1);
        for (long y = start; y <= end; y++) {
            double t1 = (y - a.getY()) / (b.getY() - a.getY());
            double t2 = (y - a.getY()) / (c.getY() - a.getY());

            Vertex ab = a.mul(1 - t1).add(b.mul(t1));
            Vertex ac = a.mul(1 - t2).add(c.mul(t2));

            fillLine(y, ab, ac);
        }

        // TODO B->C
    }

    private Vertex transformToWindow(Vertex vertex) {
        Vec3D vec3D = new Vec3D(vertex.getPoint())
                .mul(new Vec3D(1, -1, 1)) // Y goes upwards, we need it downwards
                .add(new Vec3D(1, 1, 0))  // (0,0) is at the middle, we need it in left-top corner
                // We got <0;2> => multiply by half the size of the window
                .mul(new Vec3D(imageRaster.getWidth() / 2f, imageRaster.getHeight() / 2f, 1));

        return new Vertex(new Point3D(vec3D), vertex.getColor());
    }

    private void fillLine(long y, Vertex a, Vertex b) {
        if (a.getX() > b.getX()) {
            Vertex aux = a;
            a = b;
            b = aux;
        }

        long start = (long) Math.max(Math.ceil(a.getX()), 0);
        double end = Math.min(b.getX(), imageRaster.getWidth() - 1);
        for (long x = start; x <= end; x++) {
            double t = (x - a.getX()) / (b.getX() - a.getX());
            Vertex finalVertex = a.mul(1 - t).add(b.mul(t));

            drawPixel((int)x, (int)y, finalVertex.getZ(), finalVertex.getColor());
        }
    }

    private void drawPixel(int x, int y, double z, Col color) {
        Optional<Double> zOptional = depthBuffer.getElement(x, y);
        if (zOptional.isPresent() && z < zOptional.get()) {
            depthBuffer.setElement(x, y, z);
            imageRaster.setElement(x, y, color.getRGB());
        }
    }

    @Override
    public void clear() {
        // TODO
    }

    @Override
    public void setModel(Mat4 model) {
        // TODO
    }

    @Override
    public void setView(Mat4 view) {
        // TODO
    }

    @Override
    public void setProjection(Mat4 projection) {
        // TODO
    }

}
