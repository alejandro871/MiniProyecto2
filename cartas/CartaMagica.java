package cartas;

import efectos.Contexto;
import efectos.Efecto;


public class CartaMagica extends Carta implements Activable {

    private Efecto efecto;

    public CartaMagica(String nombre, String descripcion, Efecto efecto) {
        super(nombre, descripcion);
        this.efecto = efecto;
    }

   
    @Override
    public void activar(Contexto ctx) {
        System.out.println(">>> Se activa la magia: " + getNombre() + " - " + getDescripcion());
        efecto.activar(ctx);  // muestra el mensaje y ejecuta el efecto con el contexto
    }

    
    public boolean necesitaMonstruoPropio() {
        return efecto.necesitaMonstruoPropio(); // pregunta al efecto si necesita mountruo para ser implementado
    }

    
    public boolean necesitaMonstruoEnemigo() {
        return efecto.necesitaMonstruoEnemigo(); // indica si este efecto necesita un monstruo enemigo como objetivo
    }

    @Override
    public String getTipo() {
        return "MAGICA";
    }

    @Override
    public String toString() {
        return "[MAGICA] " + getNombre() + ": " + getDescripcion();
    }
}
