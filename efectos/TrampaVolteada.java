package efectos;

import cartas.Monstruo;

// cambia al atacante a defensa y detiene el ataque actual
public class TrampaVolteada implements Efecto {

    @Override
    public void activar(Contexto ctx) {

        // en las trampas el monstruo que ataca llega como monstruoEnemigo
        Monstruo atacante = ctx.getMonstruoEnemigo();

        if (atacante == null) { // evita errores si no se pudo identificar el atacante
            System.out.println("  [Trampa Volteada] No hay monstruo atacante identificado.");
            return;
        }

        // solo se puede cambiar de posicion si estaba atacando
        if (atacante.isEnPosicionAtaque()) {

            atacante.cambiarPosicion(); // lo pasa de ataque a defensa

            System.out.println("  [Trampa Volteada] " + atacante.getNombre()
                    + " fue volteado a posicion de DEFENSA!");
        } else {

            // si ya estaba en defensa no hay nada que cambiar
            System.out.println("  [Trampa Volteada] " + atacante.getNombre()
                    + " ya estaba en defensa. La trampa no surte efecto.");
        }

        // al cambiar de posicion el combate no debe continuar
        ctx.cancelarAtaque();
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; // usa el monstruo que esta atacando actualmente
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}