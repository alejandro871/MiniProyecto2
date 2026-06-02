package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class PantallaInicio extends JFrame {

    // colores principales del tema oscuro
    private static final Color COLOR_FONDO          = new Color(10, 10, 30);
    private static final Color COLOR_PANEL_OSCURO   = new Color(20, 20, 50);
    private static final Color COLOR_DORADO         = new Color(255, 215, 0);
    private static final Color COLOR_DORADO_OSCURO  = new Color(180, 140, 0);
    private static final Color COLOR_TEXTO_CLARO    = new Color(220, 220, 255);
    private static final Color COLOR_BOTON_FONDO    = new Color(40, 0, 80);
    private static final Color COLOR_BOTON_HOVER    = new Color(80, 0, 140);
    private static final Color COLOR_BORDE_DORADO   = new Color(200, 160, 0);

    // componentes de la UI
    private JTextField campoDuelista1;
    private JTextField campoDuelista2;
    private JButton    botonIniciar;
    private JLabel     labelMensajeError;

    public PantallaInicio() {
        super("Yu-Gi-Oh! Duelo — Pantalla de Inicio");
        inicializarUI();
    }

    private void inicializarUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 650);
        setLocationRelativeTo(null); // centrar en pantalla
        setResizable(false);
        getContentPane().setBackground(COLOR_FONDO);

        setLayout(new BorderLayout(0, 0));

        add(crearPanelLogo(),      BorderLayout.NORTH);
        add(crearPanelFormulario(), BorderLayout.CENTER);
        add(crearPanelFooter(),    BorderLayout.SOUTH);
    }

    //panel con el titulo animado
    private JPanel crearPanelLogo() {
        JPanel panelLogo = new JPanel();
        panelLogo.setBackground(COLOR_FONDO);
        panelLogo.setLayout(new BoxLayout(panelLogo, BoxLayout.Y_AXIS));
        panelLogo.setBorder(new EmptyBorder(30, 20, 10, 20));

        // Titulo principal grande
        JLabel labelTitulo = new JLabel("  YU-GI-OH!  ", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("Impact", Font.BOLD, 52));
        labelTitulo.setForeground(COLOR_DORADO);
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // subtitulo
        JLabel labelSubtitulo = new JLabel("SIMULADOR DE DUELO", SwingConstants.CENTER);
        labelSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        labelSubtitulo.setForeground(COLOR_TEXTO_CLARO);
        labelSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelLinea = new JLabel("════════════════════════════", SwingConstants.CENTER);
        labelLinea.setFont(new Font("Arial", Font.PLAIN, 14));
        labelLinea.setForeground(COLOR_DORADO_OSCURO);
        labelLinea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelReglas = new JLabel(
            "<html><center>8000 LP · 25 cartas · Monstruos, Magia y Trampas<br>"
            + "¡El primero en llegar a 0 LP o agotar su mazo pierde!</center></html>",
            SwingConstants.CENTER);
        labelReglas.setFont(new Font("Arial", Font.ITALIC, 12));
        labelReglas.setForeground(new Color(160, 160, 200));
        labelReglas.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelLogo.add(labelTitulo);
        panelLogo.add(Box.createVerticalStrut(8));
        panelLogo.add(labelSubtitulo);
        panelLogo.add(Box.createVerticalStrut(6));
        panelLogo.add(labelLinea);
        panelLogo.add(Box.createVerticalStrut(10));
        panelLogo.add(labelReglas);

        return panelLogo;
    }

    private JPanel crearPanelFormulario() {
        JPanel panelExterno = new JPanel(new GridBagLayout());
        panelExterno.setBackground(COLOR_FONDO);

        JPanel panelForm = new JPanel();
        panelForm.setBackground(COLOR_PANEL_OSCURO);
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_DORADO, 2),
            new EmptyBorder(25, 40, 25, 40)
        ));
        panelForm.setPreferredSize(new Dimension(420, 280));

        // titulo del formulario
        JLabel labelFormTitulo = new JLabel("— Ingresa los nombres —", SwingConstants.CENTER);
        labelFormTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        labelFormTitulo.setForeground(COLOR_DORADO);
        labelFormTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // campo duelista 1
        JLabel labelD1 = new JLabel("Duelista 1:");
        labelD1.setFont(new Font("Arial", Font.BOLD, 13));
        labelD1.setForeground(COLOR_TEXTO_CLARO);
        labelD1.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoDuelista1 = crearCampoTexto("Nombre del Duelista 1...");
        campoDuelista1.setAlignmentX(Component.LEFT_ALIGNMENT);

        // campo duelista 2
        JLabel labelD2 = new JLabel("Duelista 2:");
        labelD2.setFont(new Font("Arial", Font.BOLD, 13));
        labelD2.setForeground(COLOR_TEXTO_CLARO);
        labelD2.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoDuelista2 = crearCampoTexto("Nombre del Duelista 2...");
        campoDuelista2.setAlignmentX(Component.LEFT_ALIGNMENT);

        labelMensajeError = new JLabel(" ");
        labelMensajeError.setFont(new Font("Arial", Font.ITALIC, 12));
        labelMensajeError.setForeground(Color.RED);
        labelMensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        botonIniciar = crearBotonEstilizado("  INICIAR DUELO  ");
        botonIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonIniciar.setMaximumSize(new Dimension(300, 45));

        botonIniciar.addActionListener(e -> validarYArrancarDuelo());

        ActionListener enterListener = e -> validarYArrancarDuelo();
        campoDuelista1.addActionListener(enterListener);
        campoDuelista2.addActionListener(enterListener);

        panelForm.add(labelFormTitulo);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(labelD1);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(campoDuelista1);
        panelForm.add(Box.createVerticalStrut(15));
        panelForm.add(labelD2);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(campoDuelista2);
        panelForm.add(Box.createVerticalStrut(12));
        panelForm.add(labelMensajeError);
        panelForm.add(Box.createVerticalStrut(8));
        panelForm.add(botonIniciar);

        panelExterno.add(panelForm);
        return panelExterno;
    }

    private JPanel crearPanelFooter() {
        JPanel panelFooter = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFooter.setBackground(COLOR_FONDO);
        panelFooter.setBorder(new EmptyBorder(5, 10, 15, 10));

        JLabel labelCita = new JLabel("\"Confía en el corazón de las cartas\" — Yugi Muto");
        labelCita.setFont(new Font("Arial", Font.ITALIC, 12));
        labelCita.setForeground(COLOR_DORADO_OSCURO);
        panelFooter.add(labelCita);

        return panelFooter;
    }

    // crea un JTextField con el estilo oscuro del juego
    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(20);
        campo.setBackground(new Color(30, 30, 60));
        campo.setForeground(COLOR_TEXTO_CLARO);
        campo.setCaretColor(COLOR_DORADO);
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_DORADO, 1),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));

        // efecto placeholder — cuando esta vacio y sin foco muestra el hint
        campo.setForeground(new Color(100, 100, 140));
        campo.setText(placeholder);

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO_CLARO);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (campo.getText().trim().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(new Color(100, 100, 140));
                }
            }
        });

        return campo;
    }

    private JButton crearBotonEstilizado(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setForeground(COLOR_DORADO);
        boton.setBackground(COLOR_BOTON_FONDO);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE_DORADO, 2),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(COLOR_BOTON_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(COLOR_BOTON_FONDO);
            }
        });

        return boton;
    }

    private void validarYArrancarDuelo() {

        String nombre1 = campoDuelista1.getText().trim();
        String nombre2 = campoDuelista2.getText().trim();

        boolean nombre1EsPlaceholder = nombre1.equals("Nombre del Duelista 1...");
        boolean nombre2EsPlaceholder = nombre2.equals("Nombre del Duelista 2...");

        if (nombre1.isEmpty() || nombre1EsPlaceholder) {
            labelMensajeError.setText("¡Debes ingresar el nombre del Duelista 1!");
            campoDuelista1.requestFocus();
            return;
        }

        if (nombre2.isEmpty() || nombre2EsPlaceholder) {
            labelMensajeError.setText("¡Debes ingresar el nombre del Duelista 2!");
            campoDuelista2.requestFocus();
            return;
        }

        if (nombre1.equalsIgnoreCase(nombre2)) {
            labelMensajeError.setText("¡Los duelistas no pueden tener el mismo nombre!");
            return;
        }

        labelMensajeError.setText(" ");

        this.setVisible(false);
        VentanaDuelo ventanaDuelo = new VentanaDuelo(nombre1, nombre2);
        ventanaDuelo.setVisible(true);
        this.dispose(); // liberar la pantalla de inicio
    }
}
