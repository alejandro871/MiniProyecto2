package efectos;

import cartas.Carta;
import java.util.ArrayList;
import java.util.Random;

// obliga al rival a descartar una carta aleatoria de su mano
public class TrampaRobo implements Efecto {

    @Override
    public void activar(Contexto ctx) {

        // obtiene las cartas que tiene actualmente el rival en la mano
        ArrayList<Carta> manoEnemiga = ctx.getJugadorEnemigo().getMano();

        if (manoEnemiga.isEmpty()) { // evita intentar descartar cuando no hay cartas
            System.out.println("  [Trampa Robo] El oponente no tiene cartas en la mano.");
            return;
        }

    Carta cartaDescartada = ctx.getJugadorEnemigo().descartarCartaAleatoria();

    if (cartaDescartada == null) {
        
        return;
    }

        System.out.println("  [Trampa Robo] " + ctx.getJugadorEnemigo().getNombre()
                + " pierde '" + cartaDescartada.getNombre() + "' de su mano!");
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; // este efecto trabaja sobre la mano del rival
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}