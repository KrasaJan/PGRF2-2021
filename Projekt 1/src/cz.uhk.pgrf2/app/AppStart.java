package app;

import controller.Controller3D;
import view.Window;

import javax.swing.*;

public class AppStart {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Window window = new Window();
            new Controller3D(window.getPanel());
            window.setTitle("Z-Buffer Projekt");
            window.setVisible(true);
        });
    }

}
