package efectos;

public class EfectoDrenaje implements Efecto {

    
    private int cantidad;

    public EfectoDrenaje(int cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public void activar(Contexto ctx) {
        System.out.println("  [Drenaje] Drenando " + cantidad + " LP de "
                + ctx.getJugadorEnemigo().getNombre() + "...");

        ctx.getJugadorEnemigo().recibirDanio(cantidad);

        ctx.getJugadorActivo().curarLP(cantidad);
        System.out.println("  [Drenaje] " + ctx.getJugadorActivo().getNombre()
                + " recupera " + cantidad + " LP. LP actual: "
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
