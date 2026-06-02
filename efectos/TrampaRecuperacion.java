package efectos;

import cartas.Carta;

// recupera la ultima carta enviada al cementerio y la devuelve a la mano
public class TrampaRecuperacion implements Efecto {

    @Override
    public void activar(Contexto ctx) {

        // obtiene el cementerio del jugador que activo la trampa
        java.util.ArrayList<Carta> cementerio = ctx.getJugadorActivo().getCementerio();

        if (cementerio.isEmpty()) { // evita intentar recuperar cartas cuando no hay ninguna
            System.out.println("  [Trampa Recuperacion] El cementerio esta vacio, no hay nada que recuperar.");
            return;
        }

        // toma la ultima carta agregada al cementerio
    Carta recuperada = ctx.getJugadorActivo().recuperarUltimaCartaDelCementerio();

    ctx.getJugadorActivo().agregarAMano(recuperada);

        System.out.println("  [Trampa Recuperacion] " + ctx.getJugadorActivo().getNombre()
                + " recupero '" + recuperada.getNombre() + "' del cementerio a su mano!");
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; // este efecto trabaja con el cementerio, no con monstruos
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}