package cartas;

public abstract class Carta {

    private  String nombre;
    private  String descripcion;

    public Carta(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

   
    public String getDescripcion() {
        return descripcion;  
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return nombre;
    }
}
