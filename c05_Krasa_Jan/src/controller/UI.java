package controller;

import model.Scene;
import model.TopologyType;
import model.solids.Cube;
import model.solids.Octahedron;
import model.solids.Solid;
import model.solids.Tetrahedron;
import transforms.Mat4;
import util.BasicColorShader;
import util.BlackWhiteColorShader;
import view.Panel;

import javax.swing.*;

public class UI {

    private final Controller3D controller3D;
    private final Scene scene;
    private final Solid bicubic;
    private Solid cube, tetra, octa;
    private JCheckBox JCubeV, JTetraV, JOctaV, JBicubicV;
    private JCheckBox JCubeT, JTetraT, JOctaT, JBicubicT;
    private JComboBox<String> JTopology, JShader;

    public UI(Panel panel, Controller3D controller3D, Scene scene) {
        this.controller3D = controller3D;
        this.scene = scene;
        cube = scene.getSolids().get(1);
        tetra = scene.getSolids().get(2);
        octa = scene.getSolids().get(3);
        bicubic = scene.getSolids().get(4);

        JToolBar tb = new JToolBar();
        tb.setFocusable(false);
        panel.add(tb);

        createButtons(tb);
    }

    /* Fills in toolbar, adds listeners */
    private void createButtons(JToolBar tb) {

        JRadioButton JPersp = new JRadioButton("Perspective");
        JPersp.setFocusable(false);
        JPersp.setSelected(true);

        JRadioButton JOrtho = new JRadioButton("Orthogonal");
        JOrtho.setFocusable(false);

        ButtonGroup projModes = new ButtonGroup();
        projModes.add(JPersp);
        projModes.add(JOrtho);

        JCubeV = new JCheckBox("CubeV");
        JCubeV.setFocusable(false);
        JCubeV.setSelected(true);

        JTetraV = new JCheckBox("TetraV");
        JTetraV.setFocusable(false);
        JTetraV.setSelected(true);

        JOctaV = new JCheckBox("OctaV");
        JOctaV.setFocusable(false);
        JOctaV.setSelected(true);

        JBicubicV = new JCheckBox("BicubicV");
        JBicubicV.setFocusable(false);
        JBicubicV.setSelected(true);

        JCubeT = new JCheckBox("CubeT");
        JCubeT.setFocusable(false);
        JCubeT.setSelected(true);

        JTetraT = new JCheckBox("TetraT");
        JTetraT.setFocusable(false);
        JTetraT.setSelected(true);

        JOctaT = new JCheckBox("OctaT");
        JOctaT.setFocusable(false);
        JOctaT.setSelected(true);

        JBicubicT = new JCheckBox("BicubicT");
        JBicubicT.setFocusable(false);
        JBicubicT.setSelected(true);

        String[] cb1Options = {"Filled", "Wireframe"};
        JTopology = new JComboBox<>(cb1Options);
        JTopology.setFocusable(false);

        String[] cb2Options = {"Basic", "Black&White"};
        JShader = new JComboBox<>(cb2Options);
        JShader.setFocusable(false);

        tb.add(JPersp);
        tb.add(JOrtho);
        tb.add(JCubeV);
        tb.add(JTetraV);
        tb.add(JOctaV);
        tb.add(JBicubicV);
        tb.add(JCubeT);
        tb.add(JTetraT);
        tb.add(JOctaT);
        tb.add(JBicubicT);
        tb.add(JTopology);
        tb.add(JShader);

        JPersp.addActionListener(e -> persp());
        JOrtho.addActionListener(e -> ortho());
        JCubeV.addActionListener(e -> cubeV());
        JTetraV.addActionListener(e -> tetraV());
        JOctaV.addActionListener(e -> octaV());
        JBicubicV.addActionListener(e -> bicubicV());
        JCubeT.addActionListener(e -> cubeT());
        JTetraT.addActionListener(e -> tetraT());
        JOctaT.addActionListener(e -> octaT());
        JBicubicT.addActionListener(e -> bicubicT());
        JTopology.addActionListener(e -> checkCB());
        JShader.addActionListener(e -> setShaders());

    }

    /* Resets view with perspective projection */
    private void persp() {
        controller3D.setProjection(controller3D.getPerspPro());
        controller3D.display();
    }

    /* Resets view with orthogonal projection */
    private void ortho() {
        controller3D.setProjection(controller3D.getOrthoPro());
        controller3D.display();
    }

    /* Adds or removes cube from the scene */
    private void cubeV() {
        if (JCubeV.isSelected()) {
            scene.addSolid(cube);
        } else {
            scene.removeSolid(cube);
        }
        controller3D.display();
    }

    /* Adds or removes tetrahedron from the scene */
    private void tetraV() {
        if (JTetraV.isSelected()) {
            scene.addSolid(tetra);
        } else {
            scene.removeSolid(tetra);
        }
        controller3D.display();
    }

    /* Adds or removes octahedron from the scene */
    private void octaV() {
        if (JOctaV.isSelected()) {
            scene.addSolid(octa);
        } else {
            scene.removeSolid(octa);
        }
        controller3D.display();
    }

    /* Adds or removes bicubic from the scene */
    private void bicubicV() {
        if (JBicubicV.isSelected()) {
            scene.addSolid(bicubic);
        } else {
            scene.removeSolid(bicubic);
        }
        controller3D.display();
    }

    private void cubeT() {
        cube.setTransformable(JCubeT.isSelected());
    }

    private void tetraT() {
        tetra.setTransformable(JTetraT.isSelected());
    }

    private void octaT() {
        octa.setTransformable(JOctaT.isSelected());
    }

    private void bicubicT() {
        bicubic.setTransformable(JBicubicT.isSelected());
    }

    /* Recreates some solids with different topology, resets Visibility and Transformbility options */
    private void checkCB() {
        Mat4 model;
        if (JTopology.getSelectedIndex() == 0) {
            /* Cube */
            model = cube.getModel();
            scene.removeSolid(cube);
            cube = new Cube(TopologyType.TRIANGLE);
            cube.setModel(model);
            scene.addSolid(cube);
            JCubeV.setSelected(true);
            JCubeT.setSelected(true);
            /* Tetrahedron */
            model = tetra.getModel();
            scene.removeSolid(tetra);
            tetra = new Tetrahedron(TopologyType.TRIANGLE);
            tetra.setModel(model);
            scene.addSolid(tetra);
            JTetraV.setSelected(true);
            JTetraT.setSelected(true);
            /* Octahedron */
            model = octa.getModel();
            scene.removeSolid(octa);
            octa = new Octahedron(TopologyType.TRIANGLE);
            octa.setModel(model);
            scene.addSolid(octa);
            JOctaV.setSelected(true);
            JOctaT.setSelected(true);

            controller3D.display();
        } else if (JTopology.getSelectedIndex() == 1) {
            /* Cube */
            model = cube.getModel();
            scene.removeSolid(cube);
            cube = new Cube(TopologyType.LINE);
            cube.setModel(model);
            scene.addSolid(cube);
            JCubeV.setSelected(true);
            JCubeT.setSelected(true);
            /* Tetrahedron */
            model = tetra.getModel();
            scene.removeSolid(tetra);
            tetra = new Tetrahedron(TopologyType.LINE);
            tetra.setModel(model);
            scene.addSolid(tetra);
            JTetraV.setSelected(true);
            JTetraT.setSelected(true);
            /* Octahedron */
            model = octa.getModel();
            scene.removeSolid(octa);
            octa = new Octahedron(TopologyType.LINE);
            octa.setModel(model);
            scene.addSolid(octa);
            JOctaV.setSelected(true);
            JOctaT.setSelected(true);

            controller3D.display();
        }
    }

    private void setShaders() {
        if (JShader.getSelectedIndex() == 0) {
            controller3D.setShader(new BasicColorShader());
        } else if (JShader.getSelectedIndex() == 1) {
            controller3D.setShader(new BlackWhiteColorShader());
        }
        controller3D.display();
    }

}
