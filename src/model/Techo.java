package src.model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Techo {
    private final int y = 50; // Posición Y del techo
    private final List<Image> imagenesNubes; // Imágenes de las nubes
    private int frameActual = 0; // Índice de la imagen actual
    private Timer timer; // Timer para la animación

    public Techo() {
        // Cargar las imágenes de las nubes
        imagenesNubes = new ArrayList<>();
        imagenesNubes.add(new ImageIcon(getClass().getResource("/src/imagen/NubeElectrica1.png")).getImage());
        imagenesNubes.add(new ImageIcon(getClass().getResource("/src/imagen/NubeElectrica2.png")).getImage());

        // Iniciar la animación
        iniciarAnimacion();
    }

    public int getY() {
        return y;
    }

    private void iniciarAnimacion() {
        // Crear un Timer para alternar entre las imágenes cada 100 ms (más rápido)
        timer = new Timer(300, e -> {
            frameActual = (frameActual + 1) % imagenesNubes.size(); // Cambiar al siguiente frame
        });
        timer.start(); // Iniciar el Timer
    }

    public void dibujar(Graphics g, int anchoPanel) {
        // Dibujar el techo base
        g.setColor(Color.GRAY);
        g.fillRect(0, y, anchoPanel, 10); // Techo base

        // Dibujar las nubes eléctricas
        dibujarNubesElectricas(g, anchoPanel);
    }

    private void dibujarNubesElectricas(Graphics g, int anchoPanel) {
        int separacionNubes = 128; // Separación entre nubes (reducida)
        int anchoNube = 80; // Ancho de cada nube (reducido)
        int alturaNube = 40; // Altura de cada nube (reducida)

        for (int x = 0; x < anchoPanel; x += separacionNubes) {
            // Dibujar la imagen actual de la nube
            g.drawImage(
                    imagenesNubes.get(frameActual), // Imagen actual
                    x, y - alturaNube, // Posición (x, y)
                    anchoNube, alturaNube, // Tamaño (ancho, alto)
                    null
            );
        }
    }
}