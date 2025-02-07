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

    // Nueva variable para el fondo, que sustituye al color
    private Image fondoPro;

    public PanelDeCarreraGlobos() {
        setPreferredSize(new Dimension(400, 750)); // Panel vertical
        globos = new ArrayList<>();
        clasificacion = new ArrayList<>();
        techo = new Techo();

        // Cargar las imágenes de los globos
        imagenGloboAzul = new ImageIcon(getClass().getResource("/src/imagen/GloboAzul.png")).getImage();
        imagenGloboVerde = new ImageIcon(getClass().getResource("/src/imagen/GloboVerde.png")).getImage();
        imagenGloboRojo = new ImageIcon(getClass().getResource("/src/imagen/GloboRojo.png")).getImage();
        imagenGloboAmarillo = new ImageIcon(getClass().getResource("/src/imagen/GloboAmarillo.png")).getImage();

        // Cargar la imagen de fondo (fondoPro.png)
        fondoPro = new ImageIcon(getClass().getResource("/src/imagen/fondoPro.png")).getImage();
    }

    public void iniciarCarrera() {
        if (carreraIniciada) return; // Evita reiniciar la carrera si ya comenzó
        carreraIniciada = true;

        globos.clear();
        clasificacion.clear();
        carreraTerminada = false;

        int startY = getHeight() - 40; // Ajuste para que inicien abajo

        // Ajustar las posiciones x de los globos
        int separacion = 80; // Reducir la separación entre globos
        int inicioX = 30; // Mover los globos un poco más a la izquierda

        globos.add(new Globo(inicioX, startY, 40, Color.RED, this));
        globos.add(new Globo(inicioX + separacion, startY, 40, Color.BLUE, this));
        globos.add(new Globo(inicioX + 2 * separacion, startY, 40, Color.GREEN, this));
        globos.add(new Globo(inicioX + 3 * separacion, startY, 40, Color.YELLOW, this));

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

        // Dibujar el fondo utilizando la imagen fondoPro.png
        g.drawImage(fondoPro, 0, 0, getWidth(), getHeight(), this);

        // Dibujar el techo y las nubes
        techo.dibujar(g, getWidth());

        // Dibujar el techo en la parte superior
        g.setColor(Color.GRAY);
        g.fillRect(0, techo.getY(), getWidth(), 10);

        // Dibujar los globos o la explosión
        for (Globo globo : globos) {
            if (globo.isExplotado() && !globo.isAnimacionCompletada()) {
                // Dibujar la imagen de explosión actual con un tamaño más grande
                int tamañoExplosion = (int) (globo.getTamaño() * 1.5); // 1.5 veces más grande
                g.drawImage(
                        globo.getImagenExplosionActual(),
                        globo.getX() - (tamañoExplosion - globo.getTamaño()) / 2, // Centrar la explosión
                        globo.getY() - (tamañoExplosion - globo.getTamaño()) / 2, // Centrar la explosión
                        tamañoExplosion,
                        tamañoExplosion,
                        this
                );
            } else if (!globo.isExplotado()) {
                // Dibujar el globo
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

            // Dibujar la animación de viento si está activa
            if (globo.isAnimacionVientoActiva()) {
                int tamañoViento = (int) (globo.getTamaño() * 0.7); // Tamaño del viento un 30% más pequeño
                if (globo.isVientoDerecha()) {
                    // Dibujar viento a la derecha
                    g.drawImage(
                            globo.getImagenVientoActual(),
                            globo.getX() + globo.getTamaño(), // Posición a la derecha del globo
                            globo.getY() + (globo.getTamaño() - tamañoViento) / 2, // Centrar verticalmente
                            tamañoViento,
                            tamañoViento,
                            this
                    );
                } else {
                    // Dibujar viento a la izquierda
                    g.drawImage(
                            globo.getImagenVientoActual(),
                            globo.getX() - tamañoViento, // Posición a la izquierda del globo
                            globo.getY() + (globo.getTamaño() - tamañoViento) / 2, // Centrar verticalmente
                            tamañoViento,
                            tamañoViento,
                            this
                    );
                }
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
        int totalGlobos = clasificacion.size();
        if (totalGlobos < 3) {
            JOptionPane.showMessageDialog(this, "No hay suficientes globos para el podio.", "Podio", JOptionPane.INFORMATION_MESSAGE);
            carreraIniciada = false;
            return;
        }
        // En la carrera, el último que llega es el ganador (oro),
        // el penúltimo es plata y el antepenúltimo es bronce.
        Globo oro = clasificacion.get(totalGlobos - 1);
        Globo plata = clasificacion.get(totalGlobos - 2);
        Globo bronce = clasificacion.get(totalGlobos - 3);

        // Mostrar el podio en una ventana modal
        PodioDialog podio = new PodioDialog(oro, plata, bronce);
        podio.setVisible(true);
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
