package efectos;

import cartas.Monstruo;

public class EfectoDebilidad implements Efecto {

    private int reduccion;

    public EfectoDebilidad(int reduccion) {
        this.reduccion = reduccion;
    }

    @Override
    public void activar(Contexto ctx) {
        Monstruo objetivo = ctx.getMonstruoEnemigo();

        if (objetivo == null) {
            System.out.println("  [Debilidad] No hay monstruo enemigo seleccionado.");
            return;
        }

        int atkAnterior = objetivo.getAtk();

        // evita que el atk termine con valores invalidos para que el juego sea mas logico
        objetivo.setAtk(atkAnterior - reduccion);

        System.out.println("  [Debilidad] El ATK de " + objetivo.getNombre()
                + " bajo de " + atkAnterior + " a " + objetivo.getAtk() + ".");
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