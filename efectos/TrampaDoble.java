package efectos;

import cartas.Monstruo;

public class TrampaDoble implements Efecto {

    @Override
    public void activar(Contexto ctx) {

        Monstruo defensor = ctx.getMonstruoPropio();

        if (defensor == null) {

            if (!ctx.getJugadorActivo().getCampo().isEmpty()) {
                defensor = ctx.getJugadorActivo().getCampo().get(0); // toma el primer monstruo disponible
            } else {
                System.out.println("  [Trampa Doble] No hay monstruos propios en campo.");
                return; 
            }
        }

        int defAnterior = defensor.getDef(); 
        defensor.setDef(defAnterior * 2);

        System.out.println("  [Trampa Doble] La DEF de " + defensor.getNombre()
                + " se duplico: " + defAnterior + " -> " + defensor.getDef() + "!");
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