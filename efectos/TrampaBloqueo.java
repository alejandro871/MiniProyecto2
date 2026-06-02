package efectos;

public class TrampaBloqueo implements Efecto {

    @Override
    public void activar(Contexto ctx) {

       
        ctx.cancelarAtaque();

        String nombreAtacante = (ctx.getMonstruoEnemigo() != null)
                ? ctx.getMonstruoEnemigo().getNombre()
                : "el monstruo atacante";

        System.out.println("  [Trampa Bloqueo] El ataque de "
                + nombreAtacante + " fue completamente bloqueado!");
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; // la trampa se activa durante el ataque, no necesita objetivo propio
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}