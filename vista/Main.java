package vista;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            PantallaInicio pantalla = new PantallaInicio();
            pantalla.setVisible(true);
        });
    }
}
