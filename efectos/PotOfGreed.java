package efectos;

public class PotOfGreed implements Efecto {

    @Override
    public void activar(Contexto ctx) {
        System.out.println("  [Pot of Greed] Robando 2 cartas...");

        // robarCarta devuelve true si pudo sacar una carta y false si el mazo estaba vacio
        boolean robo1 = ctx.getJugadorActivo().robarCarta();
        boolean robo2 = ctx.getJugadorActivo().robarCarta();

        // si alguno de los robos falla significa que el mazo se termino durante el efecto
        if (!robo1 || !robo2) {
            System.out.println("  [Pot of Greed] El mazo se agoto durante el efecto.");
        }
    }

    @Override
    public boolean necesitaMonstruoPropio() {
        return false; // este efecto no necesita seleccionar monstruos
    }

    @Override
    public boolean necesitaMonstruoEnemigo() {
        return false;
    }
}