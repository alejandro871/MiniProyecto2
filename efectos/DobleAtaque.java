package efectos;

import cartas.Monstruo;

public class DobleAtaque implements Efecto {

    @Override
    public void activar(Contexto ctx) {

        Monstruo objetivo = ctx.getMonstruoPropio();

        if (objetivo == null) {
            System.out.println("No hay monstruo seleccionado");
            return;
        }

        int aumento = objetivo.getAtk();

        EfectoTemporalAtk buff = new EfectoTemporalAtk(aumento);
        buff.activar(ctx);

        System.out.println(
                objetivo.getNombre()
                + " duplica su ATK hasta el final del turno"
        );
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