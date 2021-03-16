package controller;

import model.Scene;
import model.TopologyType;
import model.solids.Cube;
import model.solids.Solid;
import model.solids.Tetrahedron;
import transforms.Mat4;
import view.Panel;

import javax.swing.*;

public class UI {

    private final Controller3D controller3D;
    private final Scene scene;
    private Solid cube, tetra, bicubic;
    private JCheckBox JCubeV, JTetraV, JBicubicV;
    private JCheckBox JCubeT, JTetraT, JBicubicT;
    private JComboBox<String> JTopology;

    public UI(Panel panel, Controller3D controller3D, Scene scene) {
        this.controller3D = controller3D;
        this.scene = scene;
        cube = scene.getSolids().get(1);
        tetra = scene.getSolids().get(2);
        bicubic = scene.getSolids().get(3);

        JToolBar tb = new JToolBar();
        tb.setFocusable(false);
        panel.add(tb);

        createButtons(tb);
    }

    /* Fills in toolbar, adds listeners */
    private void createButtons(JToolBar tb) {

        JRadioButton JPersp = new JRadioButton("Perspective Projection");
        JPersp.setFocusable(false);
        JPersp.setSelected(true);

        JRadioButton JOrtho = new JRadioButton("Orthogonal Projection");
        JOrtho.setFocusable(false);

        ButtonGroup projModes = new ButtonGroup();
        projModes.add(JPersp);
        projModes.add(JOrtho);

        JCubeV = new JCheckBox("CubeV");
        JCubeV.setFocusable(false);
        JCubeV.setSelected(true);

        JTetraV = new JCheckBox("TetrahedronV");
        JTetraV.setFocusable(false);
        JTetraV.setSelected(true);

        JBicubicV = new JCheckBox("BicubicV");
        JBicubicV.setFocusable(false);
        JBicubicV.setSelected(true);

        JCubeT = new JCheckBox("CubeT");
        JCubeT.setFocusable(false);
        JCubeT.setSelected(true);

        JTetraT = new JCheckBox("TetrahedronT");
        JTetraT.setFocusable(false);
        JTetraT.setSelected(true);

        JBicubicT = new JCheckBox("BicubicT");
        JBicubicT.setFocusable(false);
        JBicubicT.setSelected(true);

        String[] cbOptions = {"Filled", "Wireframe"};
        JTopology = new JComboBox<>(cbOptions);
        JTopology.setFocusable(false);

        tb.add(JPersp);
        tb.add(JOrtho);
        tb.add(JCubeV);
        tb.add(JTetraV);
        tb.add(JBicubicV);
        tb.add(JCubeT);
        tb.add(JTetraT);
        tb.add(JBicubicT);
        tb.add(JTopology);

        JPersp.addActionListener(e -> persp());
        JOrtho.addActionListener(e -> ortho());
        JCubeV.addActionListener(e -> cubeV());
        JTetraV.addActionListener(e -> tetraV());
        JBicubicV.addActionListener(e -> bicubicV());
        JCubeT.addActionListener(e -> cubeT());
        JTetraT.addActionListener(e -> tetraT());
        JBicubicT.addActionListener(e -> bicubicT());
        JTopology.addActionListener(e -> checkCB());

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

            controller3D.display();
        }
    }

}
