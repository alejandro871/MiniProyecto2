package efectos;

import cartas.Monstruo;

public class TrampaDebilidad implements Efecto {

    private int reduccion; 

    public TrampaDebilidad(int reduccion) {
        this.reduccion = reduccion;
    }

    @Override
    public void activar(Contexto ctx) {

        Monstruo atacante = ctx.getMonstruoEnemigo();

        if (atacante == null) { 
            System.out.println("  [Trampa Debilidad] No hay monstruo atacante identificado.");
            return;
        }

        int atkAnterior = atacante.getAtk(); 

        atacante.setAtk(atkAnterior - reduccion);

        System.out.println("  [Trampa Debilidad] El ATK de " + atacante.getNombre()
                + " bajo de " + atkAnterior + " a " + atacante.getAtk() + ".");
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