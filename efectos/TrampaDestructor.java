package efectos;

import cartas.Monstruo;

public class TrampaDestructor implements Efecto {

    @Override
    public void activar(Contexto ctx) {

        Monstruo atacante = ctx.getMonstruoEnemigo();

        if (atacante == null) { 
            System.out.println("  [Trampa Destructor] No hay monstruo atacante identificado.");
            return;
        }

        System.out.println("  [Trampa Destructor] " + atacante.getNombre()
                + " es destruido antes de poder atacar!");

       
        ctx.getJugadorEnemigo().eliminarMonstruo(atacante); 
       
        ctx.cancelarAtaque(); // como el atacante ya no existe el combate no puede continuar
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; 
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}