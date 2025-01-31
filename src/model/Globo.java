package src.model;

import src.view.PanelDeCarreraGlobos;
import java.awt.Color;

public class Globo extends Thread {
    private int x;
    private int y;
    private final int tamaño;
    private final Color color;
    private final PanelDeCarreraGlobos panel;
    private boolean corriendo = true;
    private int velocidad = 3;
    private boolean pausado = false;

    public Globo(int x, int y, int tamaño, Color color, PanelDeCarreraGlobos panel) {
        this.x = x;
        this.y = y;
        this.tamaño = tamaño;
        this.color = color;
        this.panel = panel;
    }

    @Override
    public void run() {
        while (corriendo && y > 50) { // Se mueve mientras no llega al techo
            if (!pausado) {
                y -= velocidad; // Movimiento hacia arriba
                panel.repaint();
            }

            try {
                Thread.sleep(50); // Control de velocidad
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        panel.registrarLlegada(this);
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
}
