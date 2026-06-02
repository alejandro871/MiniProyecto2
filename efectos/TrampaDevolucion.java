package efectos;

public class TrampaDevolucion implements Efecto {

    private int danioReflejado; 

    public TrampaDevolucion(int danioReflejado) {
        this.danioReflejado = danioReflejado;
    }

    @Override
    public void activar(Contexto ctx) {
        System.out.println("  [Trampa Devolucion] El ataque es reflejado! "
                + ctx.getJugadorEnemigo().getNombre() + " recibe "
                + danioReflejado + " de daño!");

        // en el contexto de una trampa el jugadorEnemigo es quien realizo el ataque
        ctx.getJugadorEnemigo().recibirDanio(danioReflejado);


    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; // no necesita seleccionar ningun objetivo
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}