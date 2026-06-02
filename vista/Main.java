package vista;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // toda la UI de Swing debe correr en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            PantallaInicio pantalla = new PantallaInicio();
            pantalla.setVisible(true);
        });
    }
}
