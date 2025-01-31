package src.controller;

import src.view.PanelDeCarreraGlobos;

import javax.swing.*;
import java.awt.*;

public class CarreraDeGlobos extends JFrame {
    public CarreraDeGlobos() {
        setTitle("Carrera de Globos");
        setSize(350, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelDeCarreraGlobos panel = new PanelDeCarreraGlobos();
        add(panel, BorderLayout.CENTER);

        JButton iniciarButton = new JButton("Iniciar Carrera");
        iniciarButton.addActionListener(e -> panel.iniciarCarrera());
        add(iniciarButton, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarreraDeGlobos frame = new CarreraDeGlobos();
            frame.setVisible(true);
        });
    }
}
