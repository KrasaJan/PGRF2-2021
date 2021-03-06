package controller;

import model.Element;
import model.TopologyType;
import model.Vertex;
import rasterize.Raster;
import renderer.GPURenderer;
import renderer.RendererZBuffer;
import transforms.*;
import view.Panel;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private final Panel panel;
    private final Raster<Integer> imageRaster;
    private final GPURenderer renderer;

    private final List<Element> elementBuffer;
    private final List<Integer> indexBuffer;
    private final List<Vertex> vertexBuffer;

    private Mat4 model, projection;
    private Camera camera;

    public Controller3D(Panel panel) {
        this.panel = panel;
        this.imageRaster = panel.getRaster();
        this.renderer = new RendererZBuffer(imageRaster);

        elementBuffer = new ArrayList<>();
        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();

        initMatrices();
        initListeners(panel);
        createScene();
    }

    private void createScene() {
        vertexBuffer.add(new Vertex(new Point3D(.5, .0, .9), new Col(255, 0, 0))); // 0 // nejvíce vlevo
        vertexBuffer.add(new Vertex(new Point3D(.7, .7, .9), new Col(255, 120, 0))); // 1 // nejvíce dole
        vertexBuffer.add(new Vertex(new Point3D(.0, .5, .3), new Col(255, 255, 0))); // 2 // společný
        vertexBuffer.add(new Vertex(new Point3D(.3, .8, .5), new Col(0, 255, 0))); // 3 // nejvíce vpravo
        vertexBuffer.add(new Vertex(new Point3D(.1, .2, 1), new Col(0, 255, 120))); // 4 // nejvíce nahoře
        vertexBuffer.add(new Vertex(new Point3D(.7, .3, .2), new Col(0, 255, 255))); // 4 // nejvíce nahoře

        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(1);

        indexBuffer.add(3);
        indexBuffer.add(4);
        indexBuffer.add(5);

        elementBuffer.add(new Element(TopologyType.TRIANGLE, 0, 6));
        renderer.draw(elementBuffer, indexBuffer, vertexBuffer);
        panel.repaint(); // pouze pro debug
    }

    private void initMatrices() {
        model = new Mat4Identity();

        Vec3D e = new Vec3D(0, -5, 2);
        camera = new Camera()
                .withPosition(e)
                .withAzimuth(Math.toRadians(90))
                .withZenith(Math.toRadians(-20));

        projection = new Mat4PerspRH(
                Math.PI / 3,
                imageRaster.getHeight() / (float) imageRaster.getWidth(),
                0.5,
                50
        );
    }

    private void initListeners(Panel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX());
            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println(e.isControlDown());
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode() == KeyEvent.VK_ENTER);
            }
        });
    }

    private void display() {
        renderer.clear();
        // TODO draw
        panel.repaint();
    }

}
