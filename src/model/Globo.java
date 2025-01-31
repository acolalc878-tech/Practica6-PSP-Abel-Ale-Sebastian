package src.model;

import src.view.PanelDeCarreraGlobos;

import java.awt.*;

public class Globo extends Thread {
    private int x, y;
    private final int tamaño;
    private final Color color;
    private boolean corriendo = true;
    private final PanelDeCarreraGlobos panel;

    public Globo(int x, int y, int tamaño, Color color, PanelDeCarreraGlobos panel) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (corriendo && y > 50) { // Se mueve hacia arriba
            y -= 5;
            x += (Math.random() > 0.5) ? 1 : -1; // Balanceo
            panel.repaint();
            try {
                Thread.sleep((int) (Math.random() * 15 + 15));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        panel.registrarLlegada(this);
    }

    public void detener() {
        corriendo = false;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getTamaño() { return tamaño; }
    public Color getColor() { return color; }
}
