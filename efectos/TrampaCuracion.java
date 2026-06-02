package efectos;

public class TrampaCuracion implements Efecto {

    private int cantidad;
    public TrampaCuracion(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public void activar(Contexto ctx) {

        // en las trampas el jugadorActivo corresponde al dueño de la trampa
        ctx.getJugadorActivo().curarLP(cantidad);

        System.out.println("  [Trampa Curacion] " + ctx.getJugadorActivo().getNombre()
                + " recupera " + cantidad + " LP al ser atacado! LP: "
                + ctx.getJugadorActivo().getVida());
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