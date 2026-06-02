package efectos;

import cartas.Monstruo;

public class Escudo implements Efecto {

    private int aumento; 

    public Escudo(int aumento) {
        this.aumento = aumento;
    }

    @Override
    public void activar(Contexto ctx) {
        Monstruo objetivo = ctx.getMonstruoPropio();

        if (objetivo == null) { 
            System.out.println("  [Escudo] No hay monstruo propio seleccionado.");
            return;
        }

        int defAnterior = objetivo.getDef(); 

        objetivo.setDef(defAnterior + aumento);

        System.out.println("  [Escudo] La DEF de " + objetivo.getNombre()
                + " subio de " + defAnterior + " a " + objetivo.getDef() + ".");
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