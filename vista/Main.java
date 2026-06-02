package vista;

import javax.swing.SwingUtilities;

/**
 * Punto de entrada del juego Yu-Gi-Oh! (version GUI).
 * 
 * Solo lanza la PantallaInicio en el hilo de eventos de Swing (EDT).
 * Esto es importante para que Swing funcione correctamente — si no
 * se usa invokeLater puede haber problemas de sincronizacion en la UI.
 */
public class Main {

    public static void main(String[] args) {
        // toda la UI de Swing debe correr en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            PantallaInicio pantalla = new PantallaInicio();
            pantalla.setVisible(true);
        });
    }
}
