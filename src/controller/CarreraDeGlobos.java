package src.controller;

import src.view.PanelDeCarreraGlobos;

import javax.swing.*;

public class CarreraDeGlobos extends JFrame {
    public CarreraDeGlobos() {
        setTitle("Carrera de Globos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelDeCarreraGlobos panel = new PanelDeCarreraGlobos();
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarreraDeGlobos frame = new CarreraDeGlobos();
            frame.setVisible(true);
        });
    }
}
