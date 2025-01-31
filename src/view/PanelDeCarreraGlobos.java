package src.view;

import src.model.Globo;
import src.model.Techo;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class PanelDeCarreraGlobos extends JPanel {
    private final List<Globo> globos;
    private final Techo techo;
    private final List<Globo> clasificacion;
    private boolean carreraTerminada = false;

    public PanelDeCarreraGlobos() {
        globos = new ArrayList<>();
        clasificacion = new ArrayList<>();
        techo = new Techo();
    }

    public void iniciarCarrera() {
        globos.clear();
        clasificacion.clear();
        carreraTerminada = false;
        globos.add(new Globo(200, 500, 40, Color.RED, this));
        globos.add(new Globo(300, 500, 40, Color.BLUE, this));
        globos.add(new Globo(400, 500, 40, Color.GREEN, this));
        globos.add(new Globo(500, 500, 40, Color.YELLOW, this));

        for (Globo globo : globos) {
            globo.start();
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.CYAN);

        g.setColor(Color.GRAY);
        g.fillRect(0, techo.getY(), getWidth(), 10);

        for (Globo globo : globos) {
            g.setColor(globo.getColor());
            g.fillOval(globo.getX(), globo.getY(), globo.getTamaño(), globo.getTamaño());
        }
    }

    public synchronized void registrarLlegada(Globo globo) {
        if (!clasificacion.contains(globo)) {
            clasificacion.add(globo);
        }
        if (clasificacion.size() == globos.size()) {
            carreraTerminada = true;
            mostrarPodio();
        }
    }

    private void mostrarPodio() {
        StringBuilder mensaje = new StringBuilder("Clasificación:\n");
        for (int i = 0; i < clasificacion.size(); i++) {
            mensaje.append((i + 1)).append("° Lugar: ")
                    .append(obtenerNombreColor(clasificacion.get(i).getColor())).append("\n");
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Podio", JOptionPane.INFORMATION_MESSAGE);
    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        if (color.equals(Color.YELLOW)) return "Amarillo";
        return "Desconocido";
    }
}
