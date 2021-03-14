package controller;

import model.Scene;
import model.TopologyType;
import model.solids.Axis;
import model.solids.Bicubic;
import model.solids.Cube;
import model.solids.Solid;
import model.solids.Tetrahedron;
import rasterize.Raster;
import renderer.GPURenderer;
import renderer.RendererZBuffer;
import transforms.*;
import view.Panel;

import java.awt.event.*;

public class Controller3D {

    private final Panel panel;
    private final Raster<Integer> imageRaster;
    private final GPURenderer renderer;
    private final Scene scene;

    private Mat4 model, view, projection, orthoPro, perspPro;

    private final double step = 0.5, scaleUp = 1.1, scaleDown = 0.9;
    private Camera camera;


    private boolean leftMB, rightMB;
    private int ogX, ogY, endX, endY;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.imageRaster = panel.getRaster();
        this.renderer = new RendererZBuffer(imageRaster);
        this.scene = new Scene();

        initiate();

        view = camera.getViewMatrix();
        projection = perspPro;
        renderer.setView(view);
        renderer.setProjection(perspPro);

        scene.addSolid(new Axis());
        scene.addSolid(new Cube(TopologyType.TRIANGLE));
        scene.addSolid(new Tetrahedron(TopologyType.TRIANGLE));
        scene.addSolid(new Bicubic());

        new UI(panel, this, scene);
        initListeners(panel);
        createScene();
    }

    private void createScene() {

        for (Solid solid : scene.getSolids()) {
            renderer.setModel(solid.getModel());
            renderer.draw(solid.getElementBuffer(), solid.getIndexBuffer(), solid.getVertexBuffer());
        }

    }

    public void initiate() {
        model = new Mat4Identity();

        camera = new Camera()
                .withPosition(new Vec3D(4, 4, 4))
                .withAzimuth(Math.toRadians(225))
                .withZenith(Math.toRadians(-30))
                .withFirstPerson(true);

        orthoPro = new Mat4OrthoRH(
                20,
                20 * 0.75,
                0.1,
                20
        );

        perspPro = new Mat4PerspRH(
                Math.PI / 3,
                imageRaster.getHeight() / (float) imageRaster.getWidth(),
                1,
                20
        );
    }

    public void initListeners(Panel panel) {

        panel.addMouseListener(new MouseAdapter() {

            @Override
            /* Gets the coordinates of a click and remembers the button. */
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) { leftMB = true; }
                if (e.getButton() == 3) { rightMB = true; }
                ogX = e.getX();
                ogY = e.getY();
            }

            @Override
            /* Resets pressed button. */
            public void mouseReleased(MouseEvent e) {
                leftMB = false;
                rightMB = false;
            }

        });

        panel.addMouseMotionListener(new MouseAdapter() {

            @Override
            /* Computes the difference of x,y coordinates, if LMB rotates camera, if RMB rotes them. */
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                int moveX = ogX - endX;
                int moveY = ogY - endY;

                /* Rotates camera */
                if (leftMB) {
                    double azimuth = Math.PI * moveX / imageRaster.getWidth();
                    double zenith = Math.PI * moveY / imageRaster.getHeight();

                    if (zenith > 90) {
                        zenith = 90;
                    } else if (zenith < -90) {
                        zenith = -90;
                    }

                    camera = camera.addAzimuth(azimuth);
                    camera = camera.addZenith(zenith);

                    display();

                    ogX = e.getX();
                    ogY = e.getY();

                }
                /* Rotates selected solids */
                if (rightMB) {
                    Mat4 rot = new Mat4Identity();
                    if (e.isControlDown()) {
                        rot = new Mat4RotX(moveX * Math.PI/7200);
                    }
                    if (e.isShiftDown()) {
                        rot = new Mat4RotY(moveX * Math.PI/7200);
                    }
                    if (e.isAltDown()) {
                        rot = new Mat4RotZ(moveX * Math.PI/7200);
                    }
                    computeModel(rot);

                    display();
                }

            }

        });

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            /* Moves camera up/down, scales solids. */
            public void mouseWheelMoved(MouseWheelEvent e) {
                Mat4 scale;
//                if (JCamera.isSelected()) {
                if (e.getWheelRotation() < 0) {
                    scale = new Mat4Scale(scaleUp);
                } else {
                    scale = new Mat4Scale(scaleDown);
                }
                computeModel(scale);
//                } else {
//                    if (e.getWheelRotation() < 0) {
//                        scale = new Mat4Scale(scaleUp);
//                    } else {
//                        scale = new Mat4Scale(scaleDown);
//                    }
//                    computeModel(scale);
//                }
                display();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            /* Sets WSADQE movement of camera + IJKLUO movement of solids + R for reset button */
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) { initiate(); display(); }

                if (e.getKeyCode() == KeyEvent.VK_W) { camera = camera.forward(step); }
                if (e.getKeyCode() == KeyEvent.VK_S) { camera = camera.backward(step); }
                if (e.getKeyCode() == KeyEvent.VK_A) { camera = camera.left(step); }
                if (e.getKeyCode() == KeyEvent.VK_D) { camera = camera.right(step); }
                if (e.getKeyCode() == KeyEvent.VK_Q) { camera = camera.up(step); }
                if (e.getKeyCode() == KeyEvent.VK_E) { camera = camera.down(step); }

                if (e.getKeyCode() == KeyEvent.VK_I) { Mat4 transl = new Mat4Transl(step,0,0); computeModel(transl); }
                if (e.getKeyCode() == KeyEvent.VK_K) { Mat4 transl = new Mat4Transl(-step,0,0);computeModel(transl); }
                if (e.getKeyCode() == KeyEvent.VK_J) { Mat4 transl = new Mat4Transl(0,-step,0);computeModel(transl); }
                if (e.getKeyCode() == KeyEvent.VK_L) { Mat4 transl = new Mat4Transl(0,step,0); computeModel(transl); }
                if (e.getKeyCode() == KeyEvent.VK_U) { Mat4 transl = new Mat4Transl(0,0,-step);computeModel(transl); }
                if (e.getKeyCode() == KeyEvent.VK_O) { Mat4 transl = new Mat4Transl(0,0,step); computeModel(transl); }

                display();
            }
        });

    }

    /* Changes the model matrix of active solids. */
    public void computeModel(Mat4 mat) {
        for (Solid solid : scene.getSolids()) {
            if (solid.isTransformable()) {
                model = solid.getModel().mul(mat);
                solid.setModel(model);
            }
        }
    }

    public void display() {
        renderer.clear();
        renderer.setView(camera.getViewMatrix());
        // TODO draw
        createScene();
        panel.repaint();
    }


}
