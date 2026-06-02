package cartas;

public class Monstruo extends Carta {

 
    private int atkBase;
    private int defBase;
    private int nivel;
    private int atkActual;
    private int defActual;
    private boolean enPosicionAtaque; // true = modo ataque false = modo defensa
    private boolean puedeAtacar;  

    
    public Monstruo(String nombre, int atk, int def, int nivel, String descripcion) {
        super(nombre, descripcion);
        this.atkBase = atk;
        this.defBase = def;
        this.nivel = nivel;
        this.atkActual = atk;
        this.defActual = def;
        this.enPosicionAtaque = true;// por defecto se invoca en modo ataque
        this.puedeAtacar = true; // por defecto puede atacar al ser invocado
    }

   
    public int getAtk() {
        return atkActual;
    }

    public int getDef() {
        return defActual;
    }

    public int getAtkBase() {
        return atkBase;
    }

    public int getDefBase() {
        return defBase;
    }

    public int getNivel() {
        return nivel;    
    }

    public boolean isEnPosicionAtaque() {
        return enPosicionAtaque;
    }

    public boolean puedeAtacar() {
        return puedeAtacar;
    }

    public void setAtk(int nuevoAtk) {
        this.atkActual = Math.max(0, nuevoAtk);  
    }
 
    public void setDef(int nuevaDef) {
        this.defActual = Math.max(0, nuevaDef);// la DEF actual, nunca puede quedar negativa
    }

    public void resetAtk() {
        this.atkActual = atkBase; // sin importar el valor del ATK lo restaura al valor base
    }

    public void resetDef() {
        this.defActual = defBase; // al igual que restATK el ATK, restaura la DEF a su valor base
    }

    public void setPuedeAtacar(boolean puedeAtacar) {
        this.puedeAtacar = puedeAtacar;
    }

    public void marcarComoAtacado() {
        this.puedeAtacar = false; // solo se puede atacar 1 vez por turno
    }

    public void reiniciarParaTurno() {
        this.puedeAtacar = true;
    }

    public void cambiarPosicion() {
        enPosicionAtaque = !enPosicionAtaque;
        String modo = enPosicionAtaque ? "ATAQUE" : "DEFENSA";
        System.out.println(">>> " + getNombre() + " cambio a modo " + modo + ".");
    }

    // retorna true si este monstruo necesita un sacrificio para ser invocado
    //los monstruos de nivel mayor a 4 requieren sacrificio
    public boolean necesitaSacrificio() {
        return nivel > 4;
    }

    @Override
    public String getTipo() {
        return "MONSTRUO";
    }

    @Override
    public String toString() {
    
        String modo = enPosicionAtaque ? "[ATQ]" : "[DEF]";
        if (enPosicionAtaque) {
            return modo + " " + getNombre() + " | Nivel:" + nivel
                    + " | ATK:" + atkActual + " DEF:" + defActual;
        } else {
            return modo + " " + getNombre() + " | Nivel:" + nivel
                    + " | DEF:" + defActual + " ATK:" + atkActual;
        }
    }
}
