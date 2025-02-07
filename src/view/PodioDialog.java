package src.view;

import src.model.Globo;
import javax.swing.*;
import java.awt.*;

public class PodioDialog extends JDialog {

    public PodioDialog(Globo oro, Globo plata, Globo bronce) {
        setTitle("Podio de Ganadores");
        setModal(true);
        setSize(500, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Se agrega el panel personalizado que dibuja el podio
        PodioPanel panel = new PodioPanel(oro, plata, bronce);
        add(panel);
    }

    // Panel interno que se encarga de dibujar el podio y las imágenes
    class PodioPanel extends JPanel {
        private Globo oro, plata, bronce;
        private Image imagenOro, imagenPlata, imagenBronce;

        public PodioPanel(Globo oro, Globo plata, Globo bronce) {
            this.oro = oro;
            this.plata = plata;
            this.bronce = bronce;

            // Cargar la imagen del globo correspondiente según su color
            imagenOro = cargarImagenPorColor(oro.getColor());
            imagenPlata = cargarImagenPorColor(plata.getColor());
            imagenBronce = cargarImagenPorColor(bronce.getColor());
        }

        /**
         * Método auxiliar que según el color devuelve la imagen del globo.
         */
        private Image cargarImagenPorColor(Color color) {
            String ruta = "";
            if (color.equals(Color.RED)) {
                ruta = "/src/imagen/GloboRojo.png";
            } else if (color.equals(Color.BLUE)) {
                ruta = "/src/imagen/GloboAzul.png";
            } else if (color.equals(Color.GREEN)) {
                ruta = "/src/imagen/GloboVerde.png";
            } else if (color.equals(Color.YELLOW)) {
                ruta = "/src/imagen/GloboAmarillo.png";
            } else {
                ruta = "/src/imagen/GloboRojo.png"; // Valor por defecto
            }
            return new ImageIcon(getClass().getResource(ruta)).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Fondo blanco
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int marginBottom = 50;  // Margen inferior

            // Dimensiones de cada escalón (podio)
            int anchoPodio = 100;
            int alturaOro = 150;
            int alturaPlata = 120;
            int alturaBronce = 100;

            // Posición del podio de oro (centrado)
            int xOro = (panelWidth - anchoPodio) / 2;
            int yOro = panelHeight - alturaOro - marginBottom;

            // Posición del podio de plata (a la izquierda)
            int xPlata = xOro - 120;
            int yPlata = panelHeight - alturaPlata - marginBottom;

            // Posición del podio de bronce (a la derecha)
            int xBronce = xOro + 120;
            int yBronce = panelHeight - alturaBronce - marginBottom;

            // Dibujar cada escalón
            // Escalón de plata (segundo lugar)
            g.setColor(new Color(192, 192, 192)); // Color plata
            g.fillRect(xPlata, yPlata, anchoPodio, alturaPlata);
            g.setColor(Color.BLACK);
            g.drawRect(xPlata, yPlata, anchoPodio, alturaPlata);

            // Escalón de oro (primer lugar)
            g.setColor(new Color(255, 215, 0)); // Color dorado
            g.fillRect(xOro, yOro, anchoPodio, alturaOro);
            g.setColor(Color.BLACK);
            g.drawRect(xOro, yOro, anchoPodio, alturaOro);

            // Escalón de bronce (tercer lugar)
            g.setColor(new Color(205, 127, 50)); // Color bronce
            g.fillRect(xBronce, yBronce, anchoPodio, alturaBronce);
            g.setColor(Color.BLACK);
            g.drawRect(xBronce, yBronce, anchoPodio, alturaBronce);

            // Dimensiones para las imágenes de los globos (por encima de cada escalón)
            int anchoImagen = 60;
            int altoImagen = 60;

            // Dibujar la imagen del globo de plata
            int xImgPlata = xPlata + (anchoPodio - anchoImagen) / 2;
            int yImgPlata = yPlata - altoImagen - 10;
            g.drawImage(imagenPlata, xImgPlata, yImgPlata, anchoImagen, altoImagen, this);

            // Dibujar la imagen del globo de oro
            int xImgOro = xOro + (anchoPodio - anchoImagen) / 2;
            int yImgOro = yOro - altoImagen - 10;
            g.drawImage(imagenOro, xImgOro, yImgOro, anchoImagen, altoImagen, this);

            // Dibujar la imagen del globo de bronce
            int xImgBronce = xBronce + (anchoPodio - anchoImagen) / 2;
            int yImgBronce = yBronce - altoImagen - 10;
            g.drawImage(imagenBronce, xImgBronce, yImgBronce, anchoImagen, altoImagen, this);

            // Dibujar las etiquetas para cada puesto debajo de los escalones
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.BLACK);
            int offsetEtiqueta = 25; // Espacio entre el escalón y la etiqueta

            // Etiqueta para plata (segundo lugar)
            String etiquetaPlata = "2° (Plata)";
            int sw = g.getFontMetrics().stringWidth(etiquetaPlata);
            g.drawString(etiquetaPlata, xPlata + (anchoPodio - sw) / 2, yPlata + alturaPlata + offsetEtiqueta);

            // Etiqueta para oro (primer lugar)
            String etiquetaOro = "1° (Oro)";
            sw = g.getFontMetrics().stringWidth(etiquetaOro);
            g.drawString(etiquetaOro, xOro + (anchoPodio - sw) / 2, yOro + alturaOro + offsetEtiqueta);

            // Etiqueta para bronce (tercer lugar)
            String etiquetaBronce = "3° (Bronce)";
            sw = g.getFontMetrics().stringWidth(etiquetaBronce);
            g.drawString(etiquetaBronce, xBronce + (anchoPodio - sw) / 2, yBronce + alturaBronce + offsetEtiqueta);
        }
    }
}
