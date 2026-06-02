package efectos;

import cartas.Monstruo;

public class EfectoTemporalAtk implements Efecto {

    private int aumento;
    private Monstruo monstruObjetivo;

    
    public EfectoTemporalAtk(int aumento) {
        this.aumento = aumento;
        this.monstruObjetivo = null;
    }

    @Override
    public void activar(Contexto ctx) {
        Monstruo objetivo = ctx.getMonstruoPropio();

        if (objetivo == null) {
            System.out.println("  [Poder Temporal] No hay monstruo propio seleccionado");
            return;
        }

        this.monstruObjetivo = objetivo;

        int atkAnterior = objetivo.getAtk();
        objetivo.setAtk(atkAnterior + aumento);
        System.out.println(objetivo.getNombre() + " gana " + aumento + " puntos de ATK temporalmente");

        if (ctx.getJuego() != null) {
            ctx.getJuego().registrarEfectoTemporal(this);
        }
    }

    public void revertir() {
        if (monstruObjetivo != null) {
            int atkActual = monstruObjetivo.getAtk();
            monstruObjetivo.setAtk(atkActual - aumento);
            System.out.println(monstruObjetivo.getNombre() + " vuelve a su atque original ");
            monstruObjetivo = null;
        }
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return true;
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}
