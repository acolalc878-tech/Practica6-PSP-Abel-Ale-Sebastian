package src.model;

import src.view.PanelDeCarreraGlobos;

import javax.swing.*;
import java.awt.*;

public class Globo extends Thread {
    private int x;
    private int y;
    private final int tamaño;
    private final Color color;
    private final PanelDeCarreraGlobos panel;
    private boolean corriendo = true;
    private int velocidad = 3;
    private boolean pausado = false;
    private boolean explotado = false;
    private Image[] imagenesExplosion; // Array para las imágenes de explosión
    private int frameActual = 0; // Índice de la imagen actual de la explosión
    private boolean animacionCompletada = false; // Indica si la animación ha terminado

    public Globo(int x, int y, int tamaño, Color color, PanelDeCarreraGlobos panel) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
        this.panel = panel;
        this.imagenesExplosion = cargarImagenesExplosion(); // Cargar las imágenes de explosión
    }

    private Image[] cargarImagenesExplosion() {
        Image[] imagenes = new Image[6];
        String colorNombre = obtenerNombreColor(color).toLowerCase(); // Obtener el nombre del color en minúsculas
        for (int i = 0; i < 6; i++) {
            String ruta = "/src/imagen/Explosion" + colorNombre + (i + 1) + ".png";
            imagenes[i] = new ImageIcon(getClass().getResource(ruta)).getImage();
        }
        return imagenes;
    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        if (color.equals(Color.YELLOW)) return "Amarillo";
        return "Desconocido";
    }

    @Override
    public void run() {
        while (corriendo && y > 50) {
            if (!pausado) {
                y -= velocidad;
                panel.repaint();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Cuando el globo llega al techo, comenzar la animación de explosión
        if (y <= 50) {
            explotado = true;
            animarExplosion();
        }
        panel.registrarLlegada(this);
    }

    private void animarExplosion() {
        for (int i = 0; i < imagenesExplosion.length; i++) {
            frameActual = i; // Cambiar el frame de la animación
            panel.repaint(); // Forzar repintado para mostrar el nuevo frame

            try {
                Thread.sleep(120); // Ajusta este valor para controlar la velocidad de la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        animacionCompletada = true; // Marcar la animación como completada
        panel.repaint(); // Forzar repintado para eliminar la explosión
    }

    public boolean contienePunto(int px, int py) {
        return px >= x && px <= x + tamaño && py >= y && py <= y + tamaño;
    }

    public void pausarMedioSegundo() {
        if (!pausado) {
            pausado = true;
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pausado = false;
            }).start();
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public Color getColor() { return color; }
    public boolean isExplotado() { return explotado; }
    public boolean isAnimacionCompletada() { return animacionCompletada; }
    public Image getImagenExplosionActual() { return imagenesExplosion[frameActual]; }
}
