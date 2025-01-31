package src.controller;


import src.animacion5_carrera.PanelDeCarrera;

import javax.swing.*;

public class CarreraGlobos extends JFrame {

    public CarreraGlobos(){
        setTitle("Carrera de Globas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelDeCarrera panel = new PanelDeCarrera();
        add(panel);
    }

}
