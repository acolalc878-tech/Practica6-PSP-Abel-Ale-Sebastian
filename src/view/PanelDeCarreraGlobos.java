package src.view;

import src.model.Globo;
import src.model.Techo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class PanelDeCarreraGlobos extends JPanel {
    private final List<Globo> globos;
    private final Techo techo;
    private final List<Globo> clasificacion;
    private boolean carreraTerminada = false;
    private boolean carreraIniciada = false;
    private boolean listenerActivado = false; // Para evitar múltiples listeners

    private Image imagenGloboRojo;
    private Image imagenGloboAzul;
    private Image imagenGloboVerde;
    private Image imagenGloboAmarillo;

    public PanelDeCarreraGlobos() {
        setPreferredSize(new Dimension(400, 750)); // Panel vertical
        globos = new ArrayList<>();
        clasificacion = new ArrayList<>();
        techo = new Techo();

        // Cargar las imágenes
        imagenGloboRojo = new ImageIcon(getClass().getResource("/controller/imagen/Globo_Rojo-removebg-preview.png")).getImage();
        imagenGloboAzul = new ImageIcon(getClass().getResource("/controller/imagen/Globo_Azul-removebg-preview.png")).getImage();
        imagenGloboVerde = new ImageIcon(getClass().getResource("/controller/imagen/Globo_Verde-removebg-preview.png")).getImage();
        imagenGloboAmarillo = new ImageIcon(getClass().getResource("/controller/imagen/Globo_Amarillo-removebg-preview.png")).getImage();

    }

    public void iniciarCarrera() {
        if (carreraIniciada) return; // Evita reiniciar la carrera si ya comenzó
        carreraIniciada = true;

        globos.clear();
        clasificacion.clear();
        carreraTerminada = false;

        int startY = getHeight() - 100; // Ajuste para que inicien abajo
        globos.add(new Globo(100, startY, 40, Color.RED, this));
        globos.add(new Globo(160, startY, 40, Color.BLUE, this));
        globos.add(new Globo(220, startY, 40, Color.GREEN, this));
        globos.add(new Globo(280, startY, 40, Color.YELLOW, this));

        for (Globo globo : globos) {
            globo.start();
        }

        activarMouseListener(); // Activa la detección de clics en los globos
        repaint();
    }

    public void activarMouseListener() {
        if (!listenerActivado) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // Verificar si se ha hecho clic sobre un globo
                    for (Globo globo : globos) {
                        if (globo.contienePunto(e.getX(), e.getY())) {
                            globo.pausarMedioSegundo(); // Pausar el globo durante medio segundo
                            break; // Solo un globo puede ser pausado a la vez
                        }
                    }
                }
            });
            listenerActivado = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.CYAN);

        // Dibujar el techo en la parte superior
        g.setColor(Color.GRAY);
        g.fillRect(0, techo.getY(), getWidth(), 10);

        // Dibujar los globos usando imágenes
        for (Globo globo : globos) {
            switch (globo.getColor().toString()) {
                case "java.awt.Color[r=255,g=0,b=0]": // Rojo
                    g.drawImage(imagenGloboRojo, globo.getX(), globo.getY(), globo.getTamaño(), globo.getTamaño(), this);
                    break;
                case "java.awt.Color[r=0,g=0,b=255]": // Azul
                    g.drawImage(imagenGloboAzul, globo.getX(), globo.getY(), globo.getTamaño(), globo.getTamaño(), this);
                    break;
                case "java.awt.Color[r=0,g=255,b=0]": // Verde
                    g.drawImage(imagenGloboVerde, globo.getX(), globo.getY(), globo.getTamaño(), globo.getTamaño(), this);
                    break;
                case "java.awt.Color[r=255,g=255,b=0]": // Amarillo
                    g.drawImage(imagenGloboAmarillo, globo.getX(), globo.getY(), globo.getTamaño(), globo.getTamaño(), this);
                    break;
            }
        }
    }

    public synchronized void registrarLlegada(Globo globo) {
        if (!clasificacion.contains(globo)) {
            clasificacion.add(globo);
        }

        if (clasificacion.size() == globos.size()) {
            carreraTerminada = true;
            SwingUtilities.invokeLater(this::mostrarPodio);
        }
    }

    private void mostrarPodio() {
        StringBuilder mensaje = new StringBuilder("Clasificación:\n");
        for (int i = 0; i < clasificacion.size(); i++) {
            mensaje.append((i + 1)).append("° Lugar: ")
                    .append(obtenerNombreColor(clasificacion.get(i).getColor())).append("\n");
        }
        JOptionPane.showMessageDialog(this, mensaje.toString(), "Podio", JOptionPane.INFORMATION_MESSAGE);
        carreraIniciada = false; // Permitir reiniciar la carrera
    }

    private String obtenerNombreColor(Color color) {
        if (color.equals(Color.RED)) return "Rojo";
        if (color.equals(Color.BLUE)) return "Azul";
        if (color.equals(Color.GREEN)) return "Verde";
        if (color.equals(Color.YELLOW)) return "Amarillo";
        return "Desconocido";
    }
}
