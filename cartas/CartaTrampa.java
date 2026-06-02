package cartas;

import efectos.Contexto;
import efectos.Efecto;

// se activa unicamente cuando el oponente ataca esta carta
public class CartaTrampa extends Carta implements Activable { 

    private Efecto efecto;
   
    private boolean yaFueActivada; 


    public CartaTrampa(String nombre, String descripcion, Efecto efecto) {
        super(nombre, descripcion);
        this.efecto = efecto;
        this.yaFueActivada = false;
    }

    @Override
    public void activar(Contexto ctx) {
        if (yaFueActivada) {
            System.out.println("La trampa " + getNombre() + " ya fue activada.");
            return;
        }
        System.out.println(">>> !TRAMPA ACTIVADA!: " + getNombre() + " - " + getDescripcion());
        efecto.activar(ctx);
        yaFueActivada = true;
    }


    public boolean fueActivada() {
        return yaFueActivada;
    }


    @Override
    public String getTipo() {
        return "TRAMPA";
    }

    @Override
    public String toString() {
        return "[TRAMPA] " + getNombre() + ": " + getDescripcion();
    }
}
