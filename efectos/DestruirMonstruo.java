package efectos;

import cartas.Monstruo;

public class DestruirMonstruo implements Efecto {

    @Override
    public void activar(Contexto ctx) {
        Monstruo objetivo = ctx.getMonstruoEnemigo();

        if (objetivo == null) {  
            System.out.println("  [Destruir Monstruo] No hay monstruo enemigo seleccionado.");
            return;
        }

   
        if (!ctx.getJugadorEnemigo().getCampo().contains(objetivo)) {  
            System.out.println("  [Destruir Monstruo] El monstruo ya no esta en el campo.");
            return;
        }

        System.out.println("  [Destruir Monstruo] " + objetivo.getNombre() + " fue destruido!");
        ctx.getJugadorEnemigo().eliminarMonstruo(objetivo);
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false;
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return true;
    }
}