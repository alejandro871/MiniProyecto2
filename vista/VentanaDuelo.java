package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import cartas.Carta;
import cartas.CartaMagica;
import cartas.CartaTrampa;
import cartas.Monstruo;
import efectos.Contexto;
import juego.Juego;
import juego.Mazo;
import jugadores.Jugador;

public class VentanaDuelo extends JFrame {

    
    private static final Color FONDO_OSCURO          = new Color(8, 8, 25);
    private static final Color FONDO_PANEL           = new Color(15, 15, 40);
    private static final Color FONDO_PANEL_ENEMIGO   = new Color(30, 8, 8);
    private static final Color FONDO_PANEL_JUGADOR   = new Color(8, 20, 8);
    private static final Color COLOR_DORADO          = new Color(255, 215, 0);
    private static final Color COLOR_DORADO_OSCURO   = new Color(160, 120, 0);
    private static final Color COLOR_TEXTO           = new Color(210, 210, 240);
    private static final Color COLOR_LP_NORMAL       = new Color(50, 200, 50);
    private static final Color COLOR_LP_BAJO         = new Color(220, 50, 50);
    private static final Color COLOR_LP_MEDIO        = new Color(220, 180, 0);
    private static final Color COLOR_CARTA_MONSTRUO  = new Color(20, 40, 80);
    private static final Color COLOR_CARTA_MAGIA     = new Color(10, 60, 30);
    private static final Color COLOR_CARTA_TRAMPA    = new Color(50, 10, 50);
    private static final Color COLOR_BORDE           = new Color(80, 60, 10);
    private static final Color COLOR_LOG_FONDO       = new Color(5, 5, 20);

    private Juego juegoActual;
    private Jugador jugador1;
    private Jugador jugador2;
    private boolean yaRoboEsteTurno = false;
    private JLabel labelNombreJ1;
    private JLabel labelLpJ1;
    private JLabel labelNombreJ2;
    private JLabel labelLpJ2;
    private JLabel labelTurnoActual;
    private JLabel labelFase;
    private JPanel panelCampoOponente;
    private JPanel panelCampoJugador;
    private JLabel labelTrampasOponente;
    private JPanel panelManoJugador;
    private JTextArea areaLog;
    private JButton botonRobarCarta;
    private JButton botonTerminarTurno;
    private JLabel labelMazoJ1;
    private JLabel labelMazoJ2;

    public VentanaDuelo(String nombreDuelista1, String nombreDuelista2) {
        super("Yu-Gi-Oh! Duelo — " + nombreDuelista1 + " VS " + nombreDuelista2);

        jugador1 = new Jugador(nombreDuelista1);
        jugador2 = new Jugador(nombreDuelista2);
        Mazo.repartir(jugador1, jugador2);

        juegoActual = new Juego(jugador1, jugador2);

        inicializarVentana();
        construirUI();
        actualizarTodaLaUI();

        registrarEnLog("════════════════════════════════════════");
        registrarEnLog("   ¡¡ DUELO INICIADO !! ");
        registrarEnLog("  " + jugador1.getNombre() + " VS " + jugador2.getNombre());
        registrarEnLog("  El azar decide... ¡" + juegoActual.getJugadorActual().getNombre() + " va primero!");
        registrarEnLog("════════════════════════════════════════");
        registrarEnLog("  Fase: INICIO DEL TURNO");
        registrarEnLog("  → Presiona 'Robar Carta' para comenzar.");
    }

    private void inicializarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 780);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);
        getContentPane().setBackground(FONDO_OSCURO);
        setLayout(new BorderLayout(4, 4));
    }

    private void construirUI() {
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);

        JSplitPane splitCentral = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitCentral.setBackground(FONDO_OSCURO);
        splitCentral.setBorder(null);
        splitCentral.setDividerSize(4);
        splitCentral.setDividerLocation(700);

        JPanel panelCampoBatalla = crearPanelCampoBatalla();
        splitCentral.setLeftComponent(panelCampoBatalla);

        JPanel panelDerechoCompleto = crearPanelDerechoLog();
        splitCentral.setRightComponent(panelDerechoCompleto);

        add(splitCentral, BorderLayout.CENTER);

        JPanel panelInferior = crearPanelMano();
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(12, 12, 35));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_DORADO_OSCURO),
            new EmptyBorder(8, 12, 8, 12)
        ));

        JPanel panelInfoJ1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        panelInfoJ1.setBackground(new Color(12, 12, 35));
        labelNombreJ1 = new JLabel("Jugador 1");
        labelNombreJ1.setFont(new Font("Arial", Font.BOLD, 14));
        labelNombreJ1.setForeground(COLOR_DORADO);
        labelLpJ1 = new JLabel("LP: 8000");
        labelLpJ1.setFont(new Font("Arial", Font.BOLD, 16));
        labelLpJ1.setForeground(COLOR_LP_NORMAL);
        labelMazoJ1 = new JLabel("Mazo: 20");
        labelMazoJ1.setFont(new Font("Arial", Font.PLAIN, 12));
        labelMazoJ1.setForeground(COLOR_TEXTO);
        panelInfoJ1.add(labelNombreJ1);
        panelInfoJ1.add(new JSeparator(JSeparator.VERTICAL) {{ setPreferredSize(new Dimension(2, 20)); setForeground(COLOR_DORADO_OSCURO); }});
        panelInfoJ1.add(labelLpJ1);
        panelInfoJ1.add(labelMazoJ1);

        JPanel panelCentroTurno = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        panelCentroTurno.setBackground(new Color(12, 12, 35));
        labelTurnoActual = new JLabel("TURNO DE: ...");
        labelTurnoActual.setFont(new Font("Impact", Font.PLAIN, 18));
        labelTurnoActual.setForeground(new Color(255, 255, 180));
        labelFase = new JLabel("");
        labelFase.setFont(new Font("Arial", Font.ITALIC, 13));
        labelFase.setForeground(new Color(180, 180, 220));
        panelCentroTurno.add(labelTurnoActual);
        panelCentroTurno.add(labelFase);

        JPanel panelInfoJ2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panelInfoJ2.setBackground(new Color(12, 12, 35));
        labelNombreJ2 = new JLabel("Jugador 2");
        labelNombreJ2.setFont(new Font("Arial", Font.BOLD, 14));
        labelNombreJ2.setForeground(new Color(200, 100, 100));
        labelLpJ2 = new JLabel("LP: 8000");
        labelLpJ2.setFont(new Font("Arial", Font.BOLD, 16));
        labelLpJ2.setForeground(COLOR_LP_NORMAL);
        labelMazoJ2 = new JLabel("Mazo: 20");
        labelMazoJ2.setFont(new Font("Arial", Font.PLAIN, 12));
        labelMazoJ2.setForeground(COLOR_TEXTO);
        panelInfoJ2.add(labelMazoJ2);
        panelInfoJ2.add(new JSeparator(JSeparator.VERTICAL) {{ setPreferredSize(new Dimension(2, 20)); setForeground(COLOR_DORADO_OSCURO); }});
        panelInfoJ2.add(labelLpJ2);
        panelInfoJ2.add(labelNombreJ2);

        panel.add(panelInfoJ1, BorderLayout.WEST);
        panel.add(panelCentroTurno, BorderLayout.CENTER);
        panel.add(panelInfoJ2, BorderLayout.EAST);

        return panel;
    }

    private JPanel crearPanelCampoBatalla() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 6));
        panel.setBackground(FONDO_OSCURO);
        panel.setBorder(new EmptyBorder(6, 6, 6, 3));

        JPanel zonaOponente = new JPanel(new BorderLayout(0, 4));
        zonaOponente.setBackground(FONDO_PANEL_ENEMIGO);
        zonaOponente.setBorder(crearBordeTitulado(" Campo del Oponente", new Color(200, 80, 80)));

        labelTrampasOponente = new JLabel("  Trampas ocultas: 0  ", SwingConstants.RIGHT);
        labelTrampasOponente.setFont(new Font("Arial", Font.ITALIC, 11));
        labelTrampasOponente.setForeground(new Color(180, 100, 180));
        labelTrampasOponente.setBorder(new EmptyBorder(2, 0, 2, 6));

        panelCampoOponente = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        panelCampoOponente.setBackground(FONDO_PANEL_ENEMIGO);
        panelCampoOponente.setPreferredSize(new Dimension(0, 120));

        JLabel placeholderOponente = new JLabel("(Campo vacío)");
        placeholderOponente.setForeground(new Color(100, 60, 60));
        placeholderOponente.setFont(new Font("Arial", Font.ITALIC, 12));
        panelCampoOponente.add(placeholderOponente);

        zonaOponente.add(labelTrampasOponente, BorderLayout.NORTH);
        zonaOponente.add(panelCampoOponente, BorderLayout.CENTER);

        JPanel zonaJugador = new JPanel(new BorderLayout(0, 4));
        zonaJugador.setBackground(FONDO_PANEL_JUGADOR);
        zonaJugador.setBorder(crearBordeTitulado(" Mi Campo", new Color(80, 200, 80)));

        panelCampoJugador = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        panelCampoJugador.setBackground(FONDO_PANEL_JUGADOR);
        panelCampoJugador.setPreferredSize(new Dimension(0, 120));

        JLabel placeholderMiCampo = new JLabel("(Campo vacío)");
        placeholderMiCampo.setForeground(new Color(60, 100, 60));
        placeholderMiCampo.setFont(new Font("Arial", Font.ITALIC, 12));
        panelCampoJugador.add(placeholderMiCampo);

        zonaJugador.add(panelCampoJugador, BorderLayout.CENTER);

        panel.add(zonaOponente);
        panel.add(zonaJugador);

        return panel;
    }



    private JPanel crearPanelDerechoLog() {
        JPanel panel = new JPanel(new BorderLayout(0, 6));
        panel.setBackground(FONDO_OSCURO);
        panel.setBorder(new EmptyBorder(6, 3, 0, 6));

        // area de log
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setBackground(COLOR_LOG_FONDO);
        areaLog.setForeground(new Color(180, 220, 180));
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        areaLog.setLineWrap(true);
        areaLog.setWrapStyleWord(true);
        areaLog.setBorder(new EmptyBorder(6, 6, 6, 6));

        JScrollPane scrollLog = new JScrollPane(areaLog);
        scrollLog.setBorder(crearBordeTitulado(" Log del Duelo", COLOR_DORADO_OSCURO));
        scrollLog.setPreferredSize(new Dimension(0, 300));
        scrollLog.getVerticalScrollBar().setBackground(FONDO_PANEL);

        JPanel panelBotones = crearPanelBotonesAccion();

        panel.add(scrollLog, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }



    private JPanel crearPanelBotonesAccion() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 6, 6));
        panel.setBackground(FONDO_OSCURO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            crearBordeTitulado("Acciones", COLOR_DORADO_OSCURO),
            new EmptyBorder(4, 4, 4, 4)
        ));

        botonRobarCarta = crearBotonAccion(" Robar Carta", new Color(0, 80, 120));
        botonRobarCarta.addActionListener(e -> accionRobarCarta());

        JButton botonAtacarDirecto = crearBotonAccion(" Atacar", new Color(100, 20, 20));
        botonAtacarDirecto.addActionListener(e -> mostrarMenuAtaque());

        JButton botonVerCementerio = crearBotonAccion(" Cementerio", new Color(40, 40, 40));
        botonVerCementerio.addActionListener(e -> mostrarCementerio());

        botonTerminarTurno = crearBotonAccion(" Terminar Turno", new Color(60, 40, 0));
        botonTerminarTurno.addActionListener(e -> accionTerminarTurno());

        panel.add(botonRobarCarta);
        panel.add(botonAtacarDirecto);
        panel.add(botonVerCementerio);
        panel.add(botonTerminarTurno);

        return panel;
    }


    private JPanel crearPanelMano() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(FONDO_OSCURO);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(2, 0, 0, 0, COLOR_DORADO_OSCURO),
            new EmptyBorder(4, 6, 6, 6)
        ));

        JLabel labelTituloMano = new JLabel("   MI MANO  ");
        labelTituloMano.setFont(new Font("Arial", Font.BOLD, 12));
        labelTituloMano.setForeground(COLOR_DORADO);
        panel.add(labelTituloMano, BorderLayout.WEST);

        panelManoJugador = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 4));
        panelManoJugador.setBackground(FONDO_OSCURO);
        panelManoJugador.setPreferredSize(new Dimension(0, 105));

        JScrollPane scrollMano = new JScrollPane(panelManoJugador,
            JScrollPane.VERTICAL_SCROLLBAR_NEVER,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollMano.setBorder(null);
        scrollMano.setBackground(FONDO_OSCURO);
        panel.add(scrollMano, BorderLayout.CENTER);

        return panel;
    }


    private void accionRobarCarta() {
        if (yaRoboEsteTurno) {
            JOptionPane.showMessageDialog(this,
                "Ya robaste una carta este turno.",
                "Acción no disponible", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Jugador actual = juegoActual.getJugadorActual();
        registrarEnLog("");
        registrarEnLog("[ " + actual.getNombre() + " — Fase de Robo ]");

        boolean puedeRobar = juegoActual.faseRobo();

        if (!puedeRobar) {
            // el jugador perdio por mazo vacio
            actualizarTodaLaUI();
            verificarFinDelJuego();
            return;
        }

        ArrayList<Carta> manoActual = actual.getMano();
        if (!manoActual.isEmpty()) {
            Carta robada = manoActual.get(manoActual.size() - 1);
            registrarEnLog("  → Robaste: " + robada.getNombre() + " [" + robada.getTipo() + "]");
        }

        yaRoboEsteTurno = true;
        botonRobarCarta.setEnabled(false);

        labelFase.setText("[Fase Principal]");
        actualizarTodaLaUI();
    }

    private void accionJugarCartaDeMano(Carta carta) {
        Jugador actual = juegoActual.getJugadorActual();
        Jugador enemigo = juegoActual.getJugadorEnemigo();

        if (actual.yaJugoCartaEsteTurno()) {
            JOptionPane.showMessageDialog(this,
                "Ya jugaste una carta este turno.\nSolo se puede jugar 1 carta por turno.",
                "Turno limitado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!yaRoboEsteTurno) {
            JOptionPane.showMessageDialog(this,
                "Debes robar tu carta primero (Fase de Robo).",
                "Acción no disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // segun el tipo de carta, diferente accion
        if (carta instanceof Monstruo) {
            accionInvocarMonstruo((Monstruo) carta, actual);
        } else if (carta instanceof CartaMagica) {
            accionActivarMagia((CartaMagica) carta, actual, enemigo);
        } else if (carta instanceof CartaTrampa) {
            accionColocarTrampa((CartaTrampa) carta, actual);
        }

        actualizarTodaLaUI();
        verificarFinDelJuego();
    }

    private void accionInvocarMonstruo(Monstruo monstruo, Jugador actual) {
        registrarEnLog("");
        registrarEnLog("[ Invocación: " + monstruo.getNombre() + " ]");

        if (monstruo.necesitaSacrificio()) {
            // necesita sacrificio — mostrar campo
            if (actual.getCampo().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    monstruo.getNombre() + " es Nivel " + monstruo.getNivel() + " y necesita un sacrificio.\n"
                    + "¡No tienes monstruos en campo para sacrificar!",
                    "Sacrificio requerido", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // dialogo para elegir el sacrificio
            String[] opcionesCampo = new String[actual.getCampo().size()];
            for (int i = 0; i < actual.getCampo().size(); i++) {
                Monstruo m = actual.getCampo().get(i);
                opcionesCampo[i] = m.getNombre() + " (ATK:" + m.getAtk() + " / DEF:" + m.getDef() + ")";
            }

            String seleccionadoStr = (String) JOptionPane.showInputDialog(
                this,
                monstruo.getNombre() + " es Nivel " + monstruo.getNivel() + ".\n"
                    + "Selecciona el monstruo a SACRIFICAR:",
                "Sacrificio necesario",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesCampo,
                opcionesCampo[0]);

            if (seleccionadoStr == null) {
                registrarEnLog("  → Invocación cancelada.");
                return;
            }

            int indSacrificio = -1;
            for (int i = 0; i < opcionesCampo.length; i++) {
                if (opcionesCampo[i].equals(seleccionadoStr)) {
                    indSacrificio = i;
                    break;
                }
            }

            if (indSacrificio < 0) return;

            Monstruo sacrificio = actual.getCampo().get(indSacrificio);
            boolean exito = actual.invocarMonstruo(monstruo, sacrificio);

            if (exito) {
                registrarEnLog("   " + sacrificio.getNombre() + " fue sacrificado.");
                registrarEnLog("   ¡" + monstruo.getNombre() + " invocado!");
                registrarEnLog("    ATK:" + monstruo.getAtk() + " / DEF:" + monstruo.getDef()
                    + " / Nivel:" + monstruo.getNivel());
            }
        } else {
            // invocacion normal sin sacrificio
            boolean exito = actual.invocarMonstruo(monstruo);
            if (exito) {
                registrarEnLog("   ¡" + monstruo.getNombre() + " invocado al campo!");
                registrarEnLog("    ATK:" + monstruo.getAtk() + " / DEF:" + monstruo.getDef());
            }
        }
    }

    private void accionActivarMagia(CartaMagica carta, Jugador actual, Jugador enemigo) {
        registrarEnLog("");
        registrarEnLog("[ Carta Mágica: " + carta.getNombre() + " ]");
        registrarEnLog("  Efecto: " + carta.getDescripcion());

        Contexto ctx = new Contexto(actual, enemigo);
        ctx.setJuego(juegoActual);

        // si necesita monstruo propio
        if (carta.necesitaMonstruoPropio()) {
            if (actual.getCampo().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Esta magia necesita un monstruo propio en campo.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opciones = new String[actual.getCampo().size()];
            for (int i = 0; i < actual.getCampo().size(); i++) {
                Monstruo m = actual.getCampo().get(i);
                opciones[i] = m.getNombre() + " (ATK:" + m.getAtk() + ")";
            }

            String elegido = (String) JOptionPane.showInputDialog(
                this, "Elige tu monstruo objetivo:",
                carta.getNombre(), JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);

            if (elegido == null) { registrarEnLog("  -> Cancelado."); return; }

            for (int i = 0; i < opciones.length; i++) {
                if (opciones[i].equals(elegido)) {
                    ctx.setMonstruoPropio(actual.getCampo().get(i));
                    break;
                }
            }
        }

        // si necesita monstruo enemigo
        if (carta.necesitaMonstruoEnemigo()) {
            if (enemigo.getCampo().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Esta magia necesita un monstruo enemigo en campo.",
                    "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] opcionesEnemigo = new String[enemigo.getCampo().size()];
            for (int i = 0; i < enemigo.getCampo().size(); i++) {
                Monstruo m = enemigo.getCampo().get(i);
                opcionesEnemigo[i] = m.getNombre() + " (ATK:" + m.getAtk() + ")";
            }

            String elegidoEnemigo = (String) JOptionPane.showInputDialog(
                this, "Elige el monstruo enemigo objetivo:",
                carta.getNombre(), JOptionPane.QUESTION_MESSAGE,
                null, opcionesEnemigo, opcionesEnemigo[0]);

            if (elegidoEnemigo == null) { registrarEnLog("  → Cancelado."); return; }

            for (int i = 0; i < opcionesEnemigo.length; i++) {
                if (opcionesEnemigo[i].equals(elegidoEnemigo)) {
                    ctx.setMonstruoEnemigo(enemigo.getCampo().get(i));
                    break;
                }
            }
        }

        boolean exito = actual.jugarMagia(carta);
        if (exito) {
            carta.activar(ctx);
            registrarEnLog("   ¡Magia activada!");
        }
    }

    private void accionColocarTrampa(CartaTrampa trampa, Jugador actual) {
        registrarEnLog("");
        registrarEnLog("[ Colocar Trampa: ??? ]");

        boolean exito = actual.colocarTrampa(trampa);
        if (exito) {
            registrarEnLog("   Trampa colocada boca abajo.");
            registrarEnLog("  (El oponente no sabe qué es)");
        }
    }

    private void mostrarMenuAtaque() {
        Jugador actual = juegoActual.getJugadorActual();
        Jugador enemigo = juegoActual.getJugadorEnemigo();

        if (!yaRoboEsteTurno) {
            JOptionPane.showMessageDialog(this, "Primero debes robar tu carta.", "Acción", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (juegoActual.esPrimerTurno()) {
            JOptionPane.showMessageDialog(this,
                "¡En el primer turno no se puede atacar!\n(Sería injusto para quien empezó último.)",
                "Sin ataque", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (actual.getCampo().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tienes monstruos en campo para atacar.",
                "Sin monstruos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<Monstruo> atacantesDisponibles = new ArrayList<>();
        for (Monstruo m : actual.getCampo()) {
            if (m.puedeAtacar()) atacantesDisponibles.add(m);
        }

        if (atacantesDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos tus monstruos ya atacaron este turno.",
                "Sin atacantes", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] opcionesAtacante = new String[atacantesDisponibles.size()];
        for (int i = 0; i < atacantesDisponibles.size(); i++) {
            Monstruo m = atacantesDisponibles.get(i);
            opcionesAtacante[i] = m.getNombre() + "  (ATK:" + m.getAtk() + " / Niv:" + m.getNivel() + ")";
        }

        String elegidoAtacante = (String) JOptionPane.showInputDialog(
            this,
            "Selecciona tu monstruo atacante:",
            " Declarar Ataque",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcionesAtacante,
            opcionesAtacante[0]);

        if (elegidoAtacante == null) return;

        Monstruo atacante = null;
        for (int i = 0; i < opcionesAtacante.length; i++) {
            if (opcionesAtacante[i].equals(elegidoAtacante)) {
                atacante = atacantesDisponibles.get(i);
                break;
            }
        }
        if (atacante == null) return;

        registrarEnLog("");
        registrarEnLog("[ Fase de Batalla ]");
        registrarEnLog("  " + actual.getNombre() + " ataca con: " + atacante.getNombre());

        Contexto ctxTrampa = null;
        if (enemigo.tieneTrampas()) {

            ctxTrampa = procesarTrampasEnemigas(enemigo, actual, atacante);
        }

        if (ctxTrampa != null && ctxTrampa.isAtaqueAnulado()) {
            registrarEnLog("   ¡El ataque fue CANCELADO por una trampa!");
            atacante.marcarComoAtacado();
            actualizarTodaLaUI();
            verificarFinDelJuego();
            return;
        }

        // el atacante sigue vivo?
        if (!actual.getCampo().contains(atacante)) {
            registrarEnLog("   El monstruo atacante fue destruido por una trampa.");
            actualizarTodaLaUI();
            verificarFinDelJuego();
            return;
        }

        Monstruo defensor = null;
        if (!enemigo.getCampo().isEmpty()) {
            String[] opcionesDefensor = new String[enemigo.getCampo().size()];
            for (int i = 0; i < enemigo.getCampo().size(); i++) {
                Monstruo m = enemigo.getCampo().get(i);
                String posicion = m.isEnPosicionAtaque() ? "ATQ:" + m.getAtk() : "DEF:" + m.getDef();
                opcionesDefensor[i] = m.getNombre() + "  (" + posicion + " / Niv:" + m.getNivel() + ")";
            }

            String elegidoDefensor = (String) JOptionPane.showInputDialog(
                this,
                "Selecciona el monstruo a atacar:",
                " Elegir objetivo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opcionesDefensor,
                opcionesDefensor[0]);

            if (elegidoDefensor == null) return;

            for (int i = 0; i < opcionesDefensor.length; i++) {
                if (opcionesDefensor[i].equals(elegidoDefensor)) {
                    defensor = enemigo.getCampo().get(i);
                    break;
                }
            }
        } else {
            int confirm = JOptionPane.showConfirmDialog(this,
                "El oponente no tiene monstruos. ¿Atacas directamente?\n"
                + attackante(atacante),
                "¡Ataque Directo!",
                JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            registrarEnLog("   ¡ATAQUE DIRECTO! → " + atacante.getAtk() + " daño a " + enemigo.getNombre());
        }

        int lpEnemigoAntes = enemigo.getVida();
        int lpPropioAntes  = actual.getVida();

        actual.atacarConMonstruo(atacante, enemigo, defensor);

        // reportar el resultado en el log
        if (enemigo.getVida() < lpEnemigoAntes) {
            int danoRecibido = lpEnemigoAntes - enemigo.getVida();
            registrarEnLog("  → " + enemigo.getNombre() + " pierde " + danoRecibido + " LP");
            registrarEnLog("    LP restantes: " + enemigo.getVida());
        }
        if (actual.getVida() < lpPropioAntes) {
            int danoRecibido = lpPropioAntes - actual.getVida();
            registrarEnLog("  → " + actual.getNombre() + " pierde " + danoRecibido + " LP (daño de rebote)");
        }

        actualizarTodaLaUI();
        verificarFinDelJuego();
    }

    private Contexto procesarTrampasEnemigas(Jugador duenioTrampa, Jugador atacante, Monstruo monstruoAtacante) {
        ArrayList<CartaTrampa> trampasDisponibles = new ArrayList<>();
        for (CartaTrampa t : duenioTrampa.getTrampas()) {
            if (!t.fueActivada()) trampasDisponibles.add(t);
        }

        if (trampasDisponibles.isEmpty()) return null;

        String[] opcionesTrampas = new String[trampasDisponibles.size() + 1];
        opcionesTrampas[0] = "No activar ninguna";
        for (int i = 0; i < trampasDisponibles.size(); i++) {
            opcionesTrampas[i + 1] = trampasDisponibles.get(i).getNombre()
                + " — " + trampasDisponibles.get(i).getDescripcion();
        }

        JOptionPane.showMessageDialog(this,
            " Es el momento de " + duenioTrampa.getNombre() + " para activar trampas.",
            "Fase de Trampas", JOptionPane.INFORMATION_MESSAGE);

        String elegida = (String) JOptionPane.showInputDialog(
            this,
            duenioTrampa.getNombre() + " tiene " + trampasDisponibles.size() + " trampa(s) disponible(s).\n"
                + "¿Deseas activar alguna?",
            "¡¡ TRAMPA !!",
            JOptionPane.WARNING_MESSAGE,
            null,
            opcionesTrampas,
            opcionesTrampas[0]);

        if (elegida == null || elegida.equals(opcionesTrampas[0])) return null;

        CartaTrampa trampaElegida = null;
        for (int i = 0; i < trampasDisponibles.size(); i++) {
            if (opcionesTrampas[i + 1].equals(elegida)) {
                trampaElegida = trampasDisponibles.get(i);
                break;
            }
        }

        if (trampaElegida == null) return null;

        Contexto ctx = new Contexto(duenioTrampa, atacante, null, monstruoAtacante);
        ctx.setJuego(juegoActual);
        trampaElegida.activar(ctx);

        // sacar la trampa del campo y mandarla al cementerio
        duenioTrampa.getTrampas().remove(trampaElegida);
        duenioTrampa.agregarAlCementerio(trampaElegida);

        registrarEnLog("   ¡TRAMPA ACTIVADA! -> " + trampaElegida.getNombre());
        registrarEnLog("    " + trampaElegida.getDescripcion());

        return ctx;
    }

    private String attackante(Monstruo m) {
        return m.getNombre() + " · ATK: " + m.getAtk();
    }

    private void mostrarCementerio() {
        Jugador actual = juegoActual.getJugadorActual();
        Jugador enemigo = juegoActual.getJugadorEnemigo();

        StringBuilder sb = new StringBuilder();
        sb.append("=== Cementerio de ").append(actual.getNombre()).append(" ===\n");
        if (actual.getCementerio().isEmpty()) {
            sb.append("  (vacío)\n");
        } else {
            for (Carta c : actual.getCementerio()) {
                sb.append("  · ").append(c.getNombre()).append(" [").append(c.getTipo()).append("]\n");
            }
        }

        sb.append("\n=== Cementerio de ").append(enemigo.getNombre()).append(" ===\n");
        if (enemigo.getCementerio().isEmpty()) {
            sb.append("  (vacío)\n");
        } else {
            for (Carta c : enemigo.getCementerio()) {
                sb.append("  · ").append(c.getNombre()).append(" [").append(c.getTipo()).append("]\n");
            }
        }

        JTextArea areaTexto = new JTextArea(sb.toString());
        areaTexto.setEditable(false);
        areaTexto.setBackground(COLOR_LOG_FONDO);
        areaTexto.setForeground(COLOR_TEXTO);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaTexto);
        scroll.setPreferredSize(new Dimension(350, 250));

        JOptionPane.showMessageDialog(this, scroll, " Cementerio", JOptionPane.PLAIN_MESSAGE);
    }

    private void accionTerminarTurno() {
        if (!yaRoboEsteTurno) {
            int confirmar = JOptionPane.showConfirmDialog(this,
                "¡No has robado tu carta este turno!\n¿Seguro que quieres terminar el turno?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmar != JOptionPane.YES_OPTION) return;
        }

        registrarEnLog("");
        registrarEnLog("[ Fase Final — " + juegoActual.getJugadorActual().getNombre() + " ]");
        registrarEnLog("  Turno terminado.");
        registrarEnLog("────────────────────────────────────────");

        juegoActual.faseFinal();// esto cambia el turno internamente
        yaRoboEsteTurno = false;// reset para el nuevo turno

        botonRobarCarta.setEnabled(true);
        labelFase.setText("[Inicio del Turno]");

        String nombreNuevo = juegoActual.getJugadorActual().getNombre();
        registrarEnLog("");
        registrarEnLog("   Ahora es el turno de: " + nombreNuevo);
        registrarEnLog("  → Presiona 'Robar Carta' para continuar.");

        actualizarTodaLaUI();

        JOptionPane.showMessageDialog(this,
            "¡Fin del turno!\n\nAhora le toca a: " + nombreNuevo,
            "Cambio de Turno", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarTodaLaUI() {
        actualizarPanelLP();
        actualizarCampoOponente();
        actualizarCampoJugador();
        actualizarManoJugador();
        labelTurnoActual.setText("TURNO: " + juegoActual.getJugadorActual().getNombre().toUpperCase());
    }

    private void actualizarPanelLP() {
        // jugador 1
        labelNombreJ1.setText(jugador1.getNombre());
        labelLpJ1.setText("LP: " + jugador1.getVida());
        labelLpJ1.setForeground(getColorLP(jugador1.getVida()));
        labelMazoJ1.setText("  Mazo: " + jugador1.getCartasMazo());

        // jugador 2
        labelNombreJ2.setText(jugador2.getNombre());
        labelLpJ2.setText("LP: " + jugador2.getVida());
        labelLpJ2.setForeground(getColorLP(jugador2.getVida()));
        labelMazoJ2.setText("Mazo: " + jugador2.getCartasMazo() + "  ");
    }

    private Color getColorLP(int lp) {
        if (lp > 4000) return COLOR_LP_NORMAL;
        if (lp > 2000) return COLOR_LP_MEDIO;
        return COLOR_LP_BAJO;
    }

    private void actualizarCampoOponente() {
        Jugador enemigo = juegoActual.getJugadorEnemigo();
        panelCampoOponente.removeAll();

        if (enemigo.getCampo().isEmpty()) {
            JLabel vacio = new JLabel("(Campo vacío)");
            vacio.setForeground(new Color(100, 60, 60));
            vacio.setFont(new Font("Arial", Font.ITALIC, 12));
            panelCampoOponente.add(vacio);
        } else {
            for (Monstruo m : enemigo.getCampo()) {
                JButton btnCarta = crearBotonCartaMonstruo(m, false);
                // los monstruos del oponente son clickeables para ver info
                btnCarta.addActionListener(e -> mostrarInfoMonstruo(m));
                panelCampoOponente.add(btnCarta);
            }
        }

        labelTrampasOponente.setText("  Trampas ocultas: " + enemigo.getTrampas().size() + "  ");

        panelCampoOponente.revalidate();
        panelCampoOponente.repaint();
    }

    private void actualizarCampoJugador() {
        Jugador actual = juegoActual.getJugadorActual();
        panelCampoJugador.removeAll();

        if (actual.getCampo().isEmpty()) {
            JLabel vacio = new JLabel("(Campo vacío)");
            vacio.setForeground(new Color(60, 100, 60));
            vacio.setFont(new Font("Arial", Font.ITALIC, 12));
            panelCampoJugador.add(vacio);
        } else {
            for (Monstruo m : actual.getCampo()) {
                JButton btnCarta = crearBotonCartaMonstruo(m, true);
                btnCarta.addActionListener(e -> mostrarInfoMonstruo(m));
                panelCampoJugador.add(btnCarta);
            }
        }

        panelCampoJugador.revalidate();
        panelCampoJugador.repaint();
    }

    private void actualizarManoJugador() {
        Jugador actual = juegoActual.getJugadorActual();
        panelManoJugador.removeAll();

        if (actual.getMano().isEmpty()) {
            JLabel vacio = new JLabel("(Sin cartas en mano)");
            vacio.setForeground(new Color(100, 100, 100));
            vacio.setFont(new Font("Arial", Font.ITALIC, 11));
            panelManoJugador.add(vacio);
        } else {
            for (Carta c : actual.getMano()) {
                JButton btnCartaMano = crearBotonCartaMano(c);
                // copia de la referencia para el lambda
                Carta cartaRef = c;
                btnCartaMano.addActionListener(e -> accionJugarCartaDeMano(cartaRef));
                panelManoJugador.add(btnCartaMano);
            }
        }

        panelManoJugador.revalidate();
        panelManoJugador.repaint();
    }



    // crea un boton para una carta de monstruo en el campo
    private JButton crearBotonCartaMonstruo(Monstruo m, boolean esMio) {
        String modo = m.isEnPosicionAtaque() ? "ATQ" : "DEF";
        String yaAtaco = (!esMio || m.puedeAtacar()) ? "" : "\n[ya atacó]";

        String textoBoton = "<html><center><b>" + m.getNombre() + "</b><br>"
            + "Lv." + m.getNivel() + " [" + modo + "]<br>"
            + "ATK:" + m.getAtk() + " DEF:" + m.getDef()
            + (yaAtaco.isEmpty() ? "" : "<br><font color='#FF6666'>ya atacó</font>")
            + "</center></html>";

        JButton btn = new JButton(textoBoton);
        btn.setPreferredSize(new Dimension(110, 90));
        btn.setFont(new Font("Arial", Font.PLAIN, 10));
        btn.setForeground(COLOR_TEXTO);
        btn.setBackground(COLOR_CARTA_MONSTRUO);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        if (esMio && !m.puedeAtacar()) {
            btn.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));
            btn.setBackground(new Color(15, 25, 50));
        } else if (esMio) {
            btn.setBorder(BorderFactory.createLineBorder(COLOR_LP_NORMAL, 2));
        } else {
            btn.setBorder(BorderFactory.createLineBorder(new Color(180, 60, 60), 2));
        }

        return btn;
    }

    private JButton crearBotonCartaMano(Carta c) {
        Color colorFondo;
        String infoExtra = "";

        if (c instanceof Monstruo) {
            Monstruo m = (Monstruo) c;
            colorFondo = COLOR_CARTA_MONSTRUO;
            infoExtra = "ATK:" + m.getAtk() + " DEF:" + m.getDef() + " Lv." + m.getNivel();
            if (m.necesitaSacrificio()) infoExtra += " SACR";
        } else if (c instanceof CartaMagica) {
            colorFondo = COLOR_CARTA_MAGIA;
            infoExtra = "Efecto mágico";
        } else {
            colorFondo = COLOR_CARTA_TRAMPA;
            infoExtra = "Trampa";
        }

        String textoBoton = "<html><center><b>" + c.getNombre() + "</b><br>"
            + "<font size='2'>[" + c.getTipo() + "]<br>" + infoExtra + "</font>"
            + "</center></html>";

        JButton btn = new JButton(textoBoton);
        btn.setPreferredSize(new Dimension(100, 85));
        btn.setFont(new Font("Arial", Font.PLAIN, 10));
        btn.setForeground(COLOR_TEXTO);
        btn.setBackground(colorFondo);
        btn.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 2));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setToolTipText("<html><b>" + c.getNombre() + "</b><br>" + c.getDescripcion() + "</html>");

        // hover effect
        btn.addMouseListener(new MouseAdapter() {
            Color original = colorFondo;
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorFondo.brighter());
                btn.setBorder(BorderFactory.createLineBorder(COLOR_DORADO, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(original);
                btn.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 2));
            }
        });

        return btn;
    }

    private void mostrarInfoMonstruo(Monstruo m) {
        String info = "Nombre:   " + m.getNombre() + "\n"
            + "Nivel:    " + m.getNivel() + "\n"
            + "ATK:      " + m.getAtk() + (m.getAtk() != m.getAtkBase() ? " (base:" + m.getAtkBase() + ")" : "") + "\n"
            + "DEF:      " + m.getDef() + (m.getDef() != m.getDefBase() ? " (base:" + m.getDefBase() + ")" : "") + "\n"
            + "Posición: " + (m.isEnPosicionAtaque() ? "ATAQUE" : "DEFENSA") + "\n"
            + "Descripción: " + m.getDescripcion();

        JOptionPane.showMessageDialog(this, info, "Info: " + m.getNombre(), JOptionPane.INFORMATION_MESSAGE);
    }



    private void verificarFinDelJuego() {
        if (!juegoActual.hayGanador()) return;

        String ganador = juegoActual.getNombreGanador();
        Jugador jugGanador = juegoActual.getGanador();
        Jugador jugPerdedor = jugGanador == jugador1 ? jugador2 : jugador1;

        registrarEnLog("");
        registrarEnLog("════════════════════════════════════════");
        registrarEnLog("   FIN DEL DUELO ");
        registrarEnLog("  ¡¡ " + ganador + " GANA EL DUELO !!");
        registrarEnLog("  LP finales: " + jugGanador.getNombre() + " → " + jugGanador.getVida());
        registrarEnLog("  LP finales: " + jugPerdedor.getNombre() + " → " + jugPerdedor.getVida());
        registrarEnLog("════════════════════════════════════════");

        botonRobarCarta.setEnabled(false);
        botonTerminarTurno.setEnabled(false);

        String mensajeFinal =
            "  FIN DEL DUELO  \n\n"
            + "¡¡ " + ganador.toUpperCase() + " GANA EL DUELO !!\n\n"
            + jugGanador.getNombre() + " termina con " + jugGanador.getVida() + " LP\n"
            + jugPerdedor.getNombre() + " termina con " + jugPerdedor.getVida() + " LP\n\n"
            + "\"Confía en el corazón de las cartas.\"\n— Yugi Muto";

        JOptionPane.showMessageDialog(
            this,
            mensajeFinal,
            "¡Duelo Terminado!",
            JOptionPane.INFORMATION_MESSAGE);

        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Quieres iniciar un nuevo duelo?",
            "Nuevo Duelo",
            JOptionPane.YES_NO_OPTION);

        if (respuesta == JOptionPane.YES_OPTION) {
            this.dispose();
            PantallaInicio nuevaInicio = new PantallaInicio();
            nuevaInicio.setVisible(true);
        } else {
            System.exit(0);
        }
    }


    private JButton crearBotonAccion(String texto, Color colorFondo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setForeground(COLOR_TEXTO);
        btn.setBackground(colorFondo);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(colorFondo.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorFondo);
            }
        });
        return btn;
    }

    private TitledBorder crearBordeTitulado(String titulo, Color colorTitulo) {
        TitledBorder borde = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(colorTitulo, 1),
            titulo,
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 11),
            colorTitulo
        );
        return borde;
    }

    public void registrarEnLog(String mensaje) {
        areaLog.append(mensaje + "\n");
        // scroll automatico al fondo
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }
}
