package jugadores;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import cartas.Carta;
import cartas.Monstruo;
import cartas.CartaMagica;
import cartas.CartaTrampa;
import efectos.Contexto;

public class Jugador {

    private static final int vidaMaxima = 8000; 

    private String nombre;  

    private int vida;

    private ArrayList<Carta> mano; 

    private ArrayList<Carta> mazo;

    private ArrayList<Monstruo> campo; 
    private ArrayList<Carta> cementerio;  

    private ArrayList<CartaTrampa> trampas;

    private boolean cartaJugadaEsteTurno;

    public Jugador(String nombre) { 
        this.nombre = nombre;
        this.vida = vidaMaxima;
        this.mano = new ArrayList<>();
        this.mazo = new ArrayList<>();
        this.campo = new ArrayList<>();
        this.cementerio = new ArrayList<>();
        this.trampas = new ArrayList<>();
        this.cartaJugadaEsteTurno = false;
    }

    public void agregarCarta(Carta carta) {
        mazo.add(carta);
    }

    public void tomarManoInicial() {  
        for (int i = 0; i < 5; i++) {
            if (!mazo.isEmpty()) {
                mano.add(mazo.remove(0));
            }
        }
        System.out.println(nombre + " toma su mano inicial de 5 cartas.");
    }

    public boolean robarCarta() {
        if (mazo.isEmpty()) {
            System.out.println("*** " + nombre + " intenta robar pero su mazo esta VACIO. DERROTA! ***");
            this.vida = 0; 
            return false;
        }
        Carta cartaRobada = mazo.remove(0);
        mano.add(cartaRobada);
        System.out.println(nombre + " roba: " + cartaRobada.getNombre()
                + "  |  Cartas en mazo: " + mazo.size());
        return true;
    }

public void recibirDanio(int danio) {
    int danioReal = Math.max(0, danio);
    setVida(this.vida - danioReal);

    System.out.println("  " + nombre + " recibe " + danioReal
            + " puntos de danio.  LP restantes: " + vida);
}

public void curarLP(int cantidad) {
    int cantReal = Math.max(0, cantidad);
    setVida(this.vida + cantReal);
}

public boolean estaEliminado() {
    return vida <= 0;
}


public boolean yaJugoCartaEsteTurno() {
    return cartaJugadaEsteTurno;
}

public boolean invocarMonstruo(Monstruo monstruo, Monstruo sacrificio) {
        if (!mano.contains(monstruo)) {
            System.out.println("  Error: ese monstruo no esta en tu mano.");
            return false;
        }

        if (cartaJugadaEsteTurno) {
            System.out.println("  Ya jugaste una carta este turno. No puedes invocar.");
            return false;
        }

        if (monstruo.necesitaSacrificio()) {

            if (sacrificio == null) {
                System.out.println("  " + monstruo.getNombre() + " es nivel " + monstruo.getNivel()
                        + " y necesita un sacrificio para ser invocado.");
                return false;
            }

            if (!campo.contains(sacrificio)) {
                System.out.println("  El monstruo de sacrificio no esta en tu campo.");
                return false;
            }

            System.out.println("  " + sacrificio.getNombre() + " es sacrificado para invocar a "
                    + monstruo.getNombre() + "!");
            eliminarMonstruo(sacrificio);
        }

        mano.remove(monstruo);
        campo.add(monstruo);

        cartaJugadaEsteTurno = true;

        System.out.println("★ " + nombre + " invoca: " + monstruo.getNombre()
                + "  ATK:" + monstruo.getAtk() + "  DEF:" + monstruo.getDef()
                + "  Nivel:" + monstruo.getNivel());

        return true;
    }

   // versión sin sacrificio
    public boolean invocarMonstruo(Monstruo monstruo) {
        return invocarMonstruo(monstruo, null);
    }

    public boolean jugarMagia(CartaMagica carta) {

        if (cartaJugadaEsteTurno) {
            System.out.println("  Ya jugaste una carta este turno.");
            return false;
        }

        if (!mano.contains(carta)) {
            System.out.println("  Esa carta magica no esta en tu mano.");
            return false;
        }

        mano.remove(carta);
        cementerio.add(carta);

        cartaJugadaEsteTurno = true;

        return true;
    }

public boolean colocarTrampa(CartaTrampa trampa) {

        if (cartaJugadaEsteTurno) {
            System.out.println("  Ya jugaste una carta este turno.");
            return false;
        }

        if (!mano.contains(trampa)) {
            System.out.println("  Esa trampa no esta en tu mano.");
            return false;
        }

        mano.remove(trampa);
        trampas.add(trampa);

        cartaJugadaEsteTurno = true;

        System.out.println("  " + nombre + " coloca una trampa boca abajo.");

        return true;
    }

    // logica combate entre 2 mountruos o ataque directo
    public boolean atacarConMonstruo(Monstruo atacante, Jugador enemigo, Monstruo defensor) {

    if (!campo.contains(atacante)) {
        System.out.println("  Ese monstruo no esta en tu campo.");
        return false;
    }

    if (!atacante.puedeAtacar()) {
        System.out.println("  " + atacante.getNombre() + " ya ataco este turno.");
        return false;
    }

    atacante.marcarComoAtacado(); 

    if (enemigo.getCampo().isEmpty()) {
        System.out.println("  ⚔ Ataque directo! " + atacante.getNombre()
                + " ataca a " + enemigo.getNombre()
                + " por " + atacante.getAtk() + " puntos!");

        enemigo.recibirDanio(atacante.getAtk());

        return true;
    }

    // valida que el monstruo objetivo aun este en el campo rival
    if (defensor == null || !enemigo.getCampo().contains(defensor)) {
        System.out.println("  El defensor no esta en el campo enemigo.");
        return false;
    }

    System.out.println("  ⚔ " + atacante.getNombre() + " (ATK:" + atacante.getAtk()
            + ") ataca a " + defensor.getNombre()
            + " (" + (defensor.isEnPosicionAtaque()
                    ? "ATK:" + defensor.getAtk()
                    : "DEF:" + defensor.getDef()) + ")");

    
    if (defensor.isEnPosicionAtaque()) {

        int diferencia = atacante.getAtk() - defensor.getAtk();

        if (diferencia > 0) {

            System.out.println("  " + defensor.getNombre() + " es destruido! "
                    + enemigo.getNombre() + " pierde " + diferencia + " LP.");

            enemigo.eliminarMonstruo(defensor);
            enemigo.recibirDanio(diferencia);

        } else if (diferencia < 0) {

            // el defensor gana y la diferencia se convierte en daño para el atacante
            System.out.println("  " + atacante.getNombre() + " es destruido! "
                    + nombre + " pierde " + (-diferencia) + " LP.");

            this.eliminarMonstruo(atacante);
            this.recibirDanio(-diferencia);

        } else {

            System.out.println("  Empate! Ambos monstruos se destruyen.");

            this.eliminarMonstruo(atacante);
            enemigo.eliminarMonstruo(defensor);
        }
        } else {

            int diferencia = atacante.getAtk() - defensor.getDef();

            if (diferencia > 0) {

                System.out.println("  " + defensor.getNombre() + " en defensa es destruido!");

                enemigo.eliminarMonstruo(defensor);


            } else if (diferencia < 0) {

                System.out.println("  La defensa de " + defensor.getNombre()
                        + " resiste el ataque. " + nombre + " pierde " + (-diferencia) + " LP.");

                this.recibirDanio(-diferencia);

            } else {

                System.out.println("  El ataque no surte efecto (empate con la DEF).");
            }
        }

        return true;
    }


public Contexto activarTrampas(Jugador atacante, Monstruo monstruoAtacante) {

    Contexto ctx = new Contexto(this, atacante, null, monstruoAtacante);

    if (trampas.isEmpty()) {
        return ctx;
    }

    System.out.println("\nTrampas disponibles:");

    ArrayList<CartaTrampa> disponibles = new ArrayList<>();

    int contador = 1;

    for (CartaTrampa trampa : trampas) {

        if (!trampa.fueActivada()) {

            System.out.println(contador + ". " + trampa.getNombre());

            disponibles.add(trampa);

            contador++;
        }
    }

    if (disponibles.isEmpty()) {
        return ctx;
    }

    Scanner scanner = new Scanner(System.in);

    System.out.println("Seleccione una trampa (0 para no activar ninguna): ");

    int opcion = scanner.nextInt();

    if (opcion <= 0 || opcion > disponibles.size()) {
        return ctx;
    }

    CartaTrampa seleccionada = disponibles.get(opcion - 1);

    seleccionada.activar(ctx);

    trampas.remove(seleccionada);

    cementerio.add(seleccionada);

    return ctx;
}

public void eliminarMonstruo(Monstruo monstruo) {

        if (campo.remove(monstruo)) {

            cementerio.add(monstruo);

            System.out.println("  " + monstruo.getNombre()
                    + " fue enviado al cementerio de " + nombre + ".");
        }
    }

public void reiniciarTurno() {

        cartaJugadaEsteTurno = false;

        for (Monstruo m : campo) {
            m.reiniciarParaTurno();
        }
    }



    
public void mostrarMano() {

    System.out.println("--- Mano de " + nombre + " (" + mano.size() + " cartas) ---");

    if (mano.isEmpty()) {
        System.out.println("  (mano vacia)");
        return;
    }

    for (int i = 0; i < mano.size(); i++) {
        System.out.println("  [" + (i + 1) + "] " + mano.get(i));
    }
}

public void mostrarCampo() {

    System.out.println("Campo de " + nombre + ":");

    if (campo.isEmpty()) {
        System.out.println("  (sin monstruos)");
    } else {

        for (int i = 0; i < campo.size(); i++) {

            String estado = campo.get(i).puedeAtacar() ? "" : " [YA ATACO]";

            System.out.println("  [" + (i + 1) + "] " + campo.get(i) + estado);
        }
    }

    if (!trampas.isEmpty()) {
        System.out.println("  Trampas boca abajo: " + trampas.size());
    }
}

public void mostrarCementerio() {

    System.out.println("Cementerio de " + nombre + " (" + cementerio.size() + " cartas):");

    for (Carta c : cementerio) {
        System.out.println("  - " + c.getNombre());
    }
}


public String getNombre() {
    return nombre;
}

public int getVida() {
    return vida;
}

public void setVida(int nuevaVida) {
    this.vida = Math.max(0, Math.min(vidaMaxima, nuevaVida));
}

public ArrayList<Carta> getMano() {
    return mano;
}

public ArrayList<Monstruo> getCampo() {
    return campo;
}

public ArrayList<Carta> getCementerio() {
    return cementerio;
}

public void agregarAMano(Carta carta) {

    if (carta != null) {

        mano.add(carta);
        }
    }

public Carta recuperarUltimaCartaDelCementerio() {

        if (cementerio.isEmpty()) {
            return null;

        }

        return cementerio.remove(cementerio.size() - 1);
    }

public Carta descartarCartaAleatoria() {

    if (mano.isEmpty()) {

        return null;
        }

        Random rand = new Random();

        int indice = rand.nextInt(mano.size());

        Carta descartada = mano.remove(indice);

        cementerio.add(descartada);

        return descartada;
    }


public void agregarAlCementerio(Carta carta) {

    if (carta != null) {

        cementerio.add(carta);
        }
    }

public ArrayList<CartaTrampa> getTrampas() {
    return trampas;
}

public int getCartasMazo() {
    return mazo.size();
}

public boolean tieneTrampas() {
    return !trampas.isEmpty();
}

}