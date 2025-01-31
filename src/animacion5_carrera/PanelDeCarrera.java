package src.animacion5_carrera;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

class PanelDeCarrera extends JPanel {
    private final List<Bola> bolas; // Lista de bolas
    private boolean carreraTerminada = false; // Control de la carrera

    public PanelDeCarrera() {
        bolas = new ArrayList<>();
        iniciarBolas();
    }

    private void iniciarBolas() {
        // Crear varias bolas con posiciones y colores diferentes
        bolas.add(new Bola(0, 50, 30, Color.RED));
        bolas.add(new Bola(0, 150, 30, Color.BLUE));
        bolas.add(new Bola(0, 250, 30, Color.GREEN));

        // Iniciar cada bola como un hilo independiente
        for (Bola bola : bolas) {
            bola.start();
        }

        // Crear un hilo que actualice la pantalla periódicamente
        new Thread(() -> {
            while (!carreraTerminada) {
                repaint(); // Redibujar el panel
                verificarGanador(); // Verificar si alguna bola llegó a la meta
                try {
                    Thread.sleep(10); // Actualización periódica
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        // Dibujar todas las bolas según su posición actual
        for (Bola bola : bolas) {
            g.setColor(bola.getColor());
            g.fillOval(bola.getX(), bola.getY(), bola.getTamaño(), bola.getTamaño());
        }
    }

    private void verificarGanador() {
        for (Bola bola : bolas) {
            if (bola.getX() >= 750) { // Meta alcanzada getWidth() - bola.getTamaño()
                carreraTerminada = true;
                detenerCarrera();
                notificarGanador(bola);
                break;
            }
        }
    }

    private void detenerCarrera() {
        for (Bola bola : bolas) {
            bola.detener();
        }
    }

    private void notificarGanador(Bola ganadora) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                    "¡La bola de color " + obtenerNombreColor(ganadora.getColor()) + " ganó la carrera!",
                    "Ganador", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        return "Desconocido";
    }
}
