package src.controller;

import src.view.PanelDeCarreraGlobos;

import javax.swing.*;

public class CarreraGlobos extends JFrame {

    public CarreraGlobos(){
        setTitle("Carrera de Globos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelDeCarreraGlobos panel = new PanelDeCarreraGlobos();
        add(panel);
    }
}