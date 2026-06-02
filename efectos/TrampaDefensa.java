package efectos;

import cartas.Monstruo;

public class TrampaDefensa implements Efecto {

    private int aumento; 
    public TrampaDefensa(int aumento) {
        this.aumento = aumento;
    }

    @Override
    public void activar(Contexto ctx) {

        // aqui llega el monstruo que esta siendo atacado
        Monstruo defensor = ctx.getMonstruoPropio();

        if (defensor == null) {

            if (!ctx.getJugadorActivo().getCampo().isEmpty()) {
                defensor = ctx.getJugadorActivo().getCampo().get(0); 
            } else {
                System.out.println("  [Trampa Defensa] No hay monstruo propio en campo.");
                return; // no hay a quien aplicar el efecto
            }
        }

        int defAnterior = defensor.getDef(); 
        defensor.setDef(defAnterior + aumento);

        System.out.println("  [Trampa Defensa] La DEF de " + defensor.getNombre()
                + " subio de " + defAnterior + " a " + defensor.getDef() + ".");
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