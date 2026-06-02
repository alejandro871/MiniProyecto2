package efectos;

import cartas.Monstruo;
import jugadores.Jugador;
import juego.Juego;


public class Contexto {

    private Jugador jugadorActivo;
    private Jugador jugadorEnemigo;

    private Monstruo monstruoPropio;
    private Monstruo monstruoEnemigo;

    private boolean ataqueAnulado;
    private Juego juego;

    public Contexto(Jugador jugadorActivo, Jugador jugadorEnemigo) { 
        this.jugadorActivo = jugadorActivo;
        this.jugadorEnemigo = jugadorEnemigo;
        this.monstruoPropio = null;
        this.monstruoEnemigo = null;
        this.ataqueAnulado = false;
        this.juego = null;
    }

    public Contexto(Jugador jugadorActivo, Jugador jugadorEnemigo, Monstruo monstruoPropio, Monstruo monstruoEnemigo) { 
        this.jugadorActivo = jugadorActivo;
        this.jugadorEnemigo = jugadorEnemigo;
        this.monstruoPropio = monstruoPropio;
        this.monstruoEnemigo = monstruoEnemigo;
        this.ataqueAnulado = false;
        this.juego = null;
    }

    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    public Jugador getJugadorEnemigo() {
        return jugadorEnemigo;
    }

    public Monstruo getMonstruoPropio() {
        return monstruoPropio;
    }

    public Monstruo getMonstruoEnemigo() {
        return monstruoEnemigo;
    }

    public void setMonstruoPropio(Monstruo m) {
        this.monstruoPropio = m;
    }

    public void setMonstruoEnemigo(Monstruo m) {
        this.monstruoEnemigo = m;
    }

    public void cancelarAtaque() {
        this.ataqueAnulado = true;  // utilizado por efectos de trampa para impedir que el combate continue
    }

    public boolean isAtaqueAnulado() {
        return ataqueAnulado;
    }

    public void setJuego(Juego j) {
        this.juego = j;
    }

    public Juego getJuego() {
        return juego;
    }
}