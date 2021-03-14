package controller;

import model.Element;
import model.Scene;
import model.TopologyType;
import model.Vertex;
import model.solids.Axis;
import model.solids.Cube;
import model.solids.Solid;
import model.solids.Tetrahedron;
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
    private final Scene scene;

    private Mat4 model, view, projection, orthoPro, perspPro;

    private final double step = 0.5, scaleUp = 1.1, scaleDown = 0.9;
    private double totalZ = 0, totalA = 0;
    private Camera camera;


    private boolean leftMB, middleMB, rightMB;
    private int ogX, ogY, endX, endY;


    public Controller3D(Panel panel) {
        this.panel = panel;
        this.imageRaster = panel.getRaster();
        this.renderer = new RendererZBuffer(imageRaster);
        this.scene = new Scene();

        initMatrices();

        view = camera.getViewMatrix();
        projection = perspPro;
        renderer.setView(view);
        renderer.setProjection(perspPro);

        scene.addSolid(new Axis());
        scene.addSolid(new Cube(TopologyType.TRIANGLE));
        scene.addSolid(new Tetrahedron(TopologyType.TRIANGLE));

        initListeners(panel);
        createScene();
    }

    private void createScene() {


        for (Solid solid : scene.getSolids()) {
            renderer.draw(solid.getElementBuffer(), solid.getIndexBuffer(), solid.getVertexBuffer());
        }


    }

    private void initMatrices() {
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

    private void initListeners(Panel panel) {

        panel.addMouseListener(new MouseAdapter() {

            @Override
            /* Gets the coordinates of a click and remembers the button. */
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) { leftMB = true; }
                if (e.getButton() == 2) { middleMB = true; }
                if (e.getButton() == 3) { rightMB = true; }
                ogX = e.getX();
                ogY = e.getY();
            }

            @Override
            /* Resets pressed button. */
            public void mouseReleased(MouseEvent e) {
                leftMB = false;
                middleMB = false;
                rightMB = false;
            }

        });

        panel.addMouseMotionListener(new MouseAdapter() {

            @Override
            /* Computes the difference of x,y coordinates, if LMB moves camera, if MMB moves solids, if RMB rotes them. */
            public void mouseDragged(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();
                int moveX = ogX - endX;
                int moveY = ogY - endY;

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

                    totalA += azimuth;
                    totalZ += zenith;
                }
//                if (middleMB) {
//                    Mat4 transl = new Mat4Transl((double)moveX/100,(double)moveY/100,0);
//                    computeModel(transl);
//                }
//                if (rightMB) {
//                    Mat4 rot;
//                    rot = new Mat4RotXYZ(0, + moveY * Math.PI / 720, moveX * Math.PI / 720);
//                    if (e.isControlDown()) {
//                        rot = new Mat4RotX(moveX * Math.PI/720);
//                    }
//                    if (e.isShiftDown()) {
//                        rot = new Mat4RotY(moveY * Math.PI/720);
//                    }
//                    if (e.isAltDown()) {
//                        rot = new Mat4RotZ(moveX * Math.PI/720);
//                    }
//                    computeModel(rot);
//                }

            }

        });

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            /* Moves camera up/down, scales solids. */
            public void mouseWheelMoved(MouseWheelEvent e) {
//                if (JCamera.isSelected()) {
//                    if (e.isControlDown()){
//                        if (e.getWheelRotation() < 0) {
//                            scale = new Mat4Scale(scaleUp);
//                        } else {
//                            scale = new Mat4Scale(scaleDown);
//                        }
//                        computeModel(scale);
//                    } else {
//                        if (e.getWheelRotation() < 0) {
//                            camera = camera.up(step);
//                        } else {
//                            camera = camera.down(step);
//                        }
//                    }
//                } else {
//                    if (e.getWheelRotation() < 0) {
//                        scale = new Mat4Scale(scaleUp);
//                    } else {
//                        scale = new Mat4Scale(scaleDown);
//                    }
//                    computeModel(scale);
//                }
//                display();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            /* Sets WSAD movement + R for reset button */
            public void keyReleased(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_R) { reInitiate(); }
                if (e.getKeyCode() == KeyEvent.VK_W) { camera = camera.forward(step); }
                if (e.getKeyCode() == KeyEvent.VK_S) { camera = camera.backward(step); }
                if (e.getKeyCode() == KeyEvent.VK_A) { camera = camera.left(step); }
                if (e.getKeyCode() == KeyEvent.VK_D) { camera = camera.right(step); }
                if (e.getKeyCode() == KeyEvent.VK_Q) { camera = camera.up(step); }
                if (e.getKeyCode() == KeyEvent.VK_E) { camera = camera.down(step); }
                display();
            }
        });

    }

    private void display() {
        renderer.clear();
        renderer.setView(camera.getViewMatrix());
        // TODO draw
        createScene();
        panel.repaint();
    }


}
