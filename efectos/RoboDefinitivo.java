package efectos;

public class RoboDefinitivo implements Efecto {

    private static final int LP_CURACION = 300; 

    @Override
    public void activar(Contexto ctx) {
        System.out.println("  [Robo Definitivo] Robando 1 carta y curando " + LP_CURACION + " LP...");

        boolean robada = ctx.getJugadorActivo().robarCarta();

        if (!robada) { 
            System.out.println("  [Robo Definitivo] El mazo esta vacio, no se pudo robar");
        }

        ctx.getJugadorActivo().curarLP(LP_CURACION);

        System.out.println("  [Robo Definitivo] LP actual de "
                + ctx.getJugadorActivo().getNombre() + ": "
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