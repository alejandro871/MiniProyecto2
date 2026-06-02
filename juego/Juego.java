package juego;

import jugadores.Jugador;
import efectos.EfectoTemporalAtk;

import java.util.ArrayList;
import java.util.Random;


public class Juego {

    private Jugador jugador1;
    private Jugador jugador2;

    private Jugador jugadorActual;
    private Jugador jugadorEnemigo;

    private boolean primerTurnoPartida;

    private ArrayList<EfectoTemporalAtk> efectosTemporalesActivos;

    public Juego(Jugador j1, Jugador j2) {
        this.jugador1 = j1;
        this.jugador2 = j2;
        this.efectosTemporalesActivos = new ArrayList<>();

        Random rand = new Random();

        if (rand.nextBoolean()) {
            this.jugadorActual = j1;
            this.jugadorEnemigo = j2;
        } else {
            this.jugadorActual = j2;
            this.jugadorEnemigo = j1;
        }

        this.primerTurnoPartida = true;

        System.out.println("El azar ha decidido: " + jugadorActual.getNombre()
                + " va primero. ¡Que comiece el duelo!");
    }

    public void mostrarEstado() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("  " + jugador1.getNombre()
                + " | LP: " + jugador1.getVida()
                + " | Mazo: " + jugador1.getCartasMazo()
                + " | Mano: " + jugador1.getMano().size());
        jugador1.mostrarCampo();

        System.out.println("------------------------------------------------------------");

        System.out.println("  " + jugador2.getNombre()
                + " | LP: " + jugador2.getVida()
                + " | Mazo: " + jugador2.getCartasMazo()
                + " | Mano: " + jugador2.getMano().size());
        jugador2.mostrarCampo();

        System.out.println("============================================================");
        System.out.println();
    }

    public boolean faseRobo() {
        System.out.println();
        System.out.println("[ Fase de Robo ]");

        // si robarCarta devuelve false significa que el jugador perdio por mazo vacio
        return jugadorActual.robarCarta();
    }

    public void faseFinal() {
        System.out.println();
        System.out.println("[ Fase Final ]");

        // revierte todos los efectos temporales activos de este turno
        for (EfectoTemporalAtk ef : efectosTemporalesActivos) {
            ef.revertir();
        }

        efectosTemporalesActivos.clear(); 

        jugadorActual.reiniciarTurno(); 

        primerTurnoPartida = false; 

        cambiarTurno();

        System.out.println("Turno terminado.");
    }

    public void cambiarTurno() {

        if (jugadorActual == jugador1) {
            jugadorActual = jugador2;
            jugadorEnemigo = jugador1;
        } else {
            jugadorActual = jugador1;
            jugadorEnemigo = jugador2;
        }
    }

    public boolean hayGanador() {
        return jugador1.estaEliminado() || jugador2.estaEliminado();
    }

    public String getNombreGanador() {

        if (jugador1.estaEliminado()) {
            return jugador2.getNombre();
        }

        return jugador1.getNombre();
    }

    public Jugador getGanador() {

        if (jugador1.estaEliminado()) {
            return jugador2;
        }

        return jugador1;
    }

    public void registrarEfectoTemporal(EfectoTemporalAtk efecto) {

        efectosTemporalesActivos.add(efecto);

        System.out.println("  [Juego] Efecto temporal registrado, expira al final del turno.");
    }

    public Jugador getJugadorActual() {
        return jugadorActual;
    }

    public Jugador getJugadorEnemigo() {
        return jugadorEnemigo;
    }

    public boolean esPrimerTurno() {
        return primerTurnoPartida;
    }

    public Jugador getJugador1() {
        return jugador1;
    }

    public Jugador getJugador2() {
        return jugador2;
    }
}