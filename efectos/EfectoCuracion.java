package efectos;

public class EfectoCuracion implements Efecto {

    private int cantidad;

    public EfectoCuracion(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public void activar(Contexto ctx) {
        ctx.getJugadorActivo().curarLP(cantidad);

        System.out.println("  [Curacion] " + ctx.getJugadorActivo().getNombre()
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