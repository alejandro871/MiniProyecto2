package efectos;


public class DanioInstantaneo implements Efecto { 

    private int danio;

    
    public DanioInstantaneo(int danio) { 
        this.danio = danio;
    }

    @Override
    public void activar(Contexto ctx) {
        System.out.println("  [Daño Instantaneo] Infligiendo " + danio
                + " de daño a " + ctx.getJugadorEnemigo().getNombre() + "!");
        ctx.getJugadorEnemigo().recibirDanio(danio);
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