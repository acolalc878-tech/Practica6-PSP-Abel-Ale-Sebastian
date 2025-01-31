package src.animacion5_carrera;

import javax.swing.*;

public class CarreraDeBolas extends JFrame {

    public CarreraDeBolas() {
        setTitle("Carrera de Bolas");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear el panel personalizado
        PanelDeCarrera panel = new PanelDeCarrera();
        add(panel); // Agregar el panel al JFrame
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarreraDeBolas frame = new CarreraDeBolas();
            frame.setVisible(true);
        });
    }
}
