package efectos;

public interface Efecto {

    void activar(Contexto ctx);

    boolean necesitaMonstruoPropio();

    boolean necesitaMonstruoEnemigo();
}