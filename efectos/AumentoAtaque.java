package efectos;

import cartas.Monstruo;

public class AumentoAtaque implements Efecto {

    
    private int aumento;

    public AumentoAtaque(int aumento) {
        this.aumento = aumento;
    }

    @Override
    public void activar(Contexto ctx) {
        Monstruo objetivo = ctx.getMonstruoPropio(); 

        if (objetivo == null) {
            System.out.println("  [Aumento Ataque] No hay monstruo propio seleccionado");
            return;
        }

        int atkAnterior = objetivo.getAtk();
        objetivo.setAtk(atkAnterior + aumento);
        System.out.println("  [Aumento Ataque] El ATK de " + objetivo.getNombre()
                + " subio de " + atkAnterior + " a " + objetivo.getAtk() + ".");
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
