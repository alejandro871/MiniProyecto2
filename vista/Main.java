package vista;

import cartas.Carta;
import cartas.CartaMagica;
import cartas.CartaTrampa;
import cartas.Monstruo;
import efectos.Contexto;
import juego.Juego;
import juego.Mazo;
import jugadores.Jugador;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // se comparte entre todos los menus para leer datos del usuario
    private static final Scanner scanner = new Scanner(System.in);

    private static Juego juego;


    public static void main(String[] args) {

        mostrarBienvenida();

        System.out.print("Nombre del Duelista 1: ");
        String nombre1 = leerTexto();

        System.out.print("Nombre del Duelista 2: ");
        String nombre2 = leerTexto();

        System.out.println();
        System.out.println("  Duelo: " + nombre1 + " VS " + nombre2);
        System.out.println("  Preparando mazos...");
        System.out.println();

        Jugador j1 = new Jugador(nombre1);
        Jugador j2 = new Jugador(nombre2);

        Mazo.repartir(j1, j2);

        juego = new Juego(j1, j2);

        pausar();

        int turnosJugados = 0;

        while (!juego.hayGanador()) {

            Jugador actual = juego.getJugadorActual();
            Jugador enemigo = juego.getJugadorEnemigo();

            System.out.println();
            System.out.println("╔══════════════════════════════════════════════════╗");
            System.out.println("  TURNO " + (turnosJugados + 1) + "  |  Es el turno de: " + actual.getNombre());
            System.out.println("╚══════════════════════════════════════════════════╝");

            juego.mostrarEstado();

            pausar();

            boolean puedeContinuar = juego.faseRobo();

            if (!puedeContinuar) {
                break;
            }

            if (juego.hayGanador()) {
                break;
            }

            ejecutarFasePrincipal(actual, enemigo);

            if (juego.hayGanador()) {
                break;
            }

            ejecutarFaseBatalla(actual, enemigo);

            if (juego.hayGanador()) {
                break;
            }

            juego.faseFinal();

            turnosJugados++;
        }

        mostrarPantallaFinal(turnosJugados);
    }

private static void ejecutarFasePrincipal(Jugador actual, Jugador enemigo) {

    System.out.println();
    System.out.println("[ Fase Principal - Turno de " + actual.getNombre() + " ]");

    if (actual.getMano().isEmpty()) {
        System.out.println("  Tu mano esta vacia. No puedes jugar cartas.");
        return;
    }

    System.out.println("  ¿Que deseas hacer?");
    System.out.println("  [1] Jugar una carta de la mano");
    System.out.println("  [2] Pasar (no jugar carta este turno)");

    int opcion = leerEntero(1, 2);

    if (opcion == 2) {
        System.out.println("  " + actual.getNombre() + " decide no jugar carta");
        return;
    }

    actual.mostrarMano();

    System.out.println("  [0] Cancelar");

    int indice = leerEntero(0, actual.getMano().size());

    if (indice == 0) {
        System.out.println("  Accion cancelada");
        return;
    }

    Carta cartaElegida = actual.getMano().get(indice - 1);

    if (cartaElegida instanceof Monstruo) {

        jugarMonstruoMenu((Monstruo) cartaElegida, actual);

    } else if (cartaElegida instanceof CartaMagica) {

        jugarMagiaMenu((CartaMagica) cartaElegida, actual, enemigo);

    } else if (cartaElegida instanceof CartaTrampa) {

        colocarTrampaDesdeMenu((CartaTrampa) cartaElegida, actual);
    }
}


private static void jugarMonstruoMenu(Monstruo monstruo, Jugador actual) {

    if (monstruo.necesitaSacrificio()) {

        System.out.println();
        System.out.println("  " + monstruo.getNombre() + " es nivel " + monstruo.getNivel()
                + " y necesita un sacrificio.");

        if (actual.getCampo().isEmpty()) {
            System.out.println("  No tienes monstruos en campo para sacrificar. No puedes invocarlo");
            return;
        }

        System.out.println("  Elige el monstruo a sacrificar:");

        actual.mostrarCampo();

        System.out.println("  [0] Cancelar");

        int indiceSac = leerEntero(0, actual.getCampo().size());

        if (indiceSac == 0) {
            System.out.println("  Invocacion cancelada");
            return;
        }

        Monstruo sacrificio = actual.getCampo().get(indiceSac - 1);

        actual.invocarMonstruo(monstruo, sacrificio);

    } else {

        actual.invocarMonstruo(monstruo);
    }
}

private static void jugarMagiaMenu(CartaMagica carta, Jugador actual, Jugador enemigo) {

    System.out.println();

    Contexto ctx = new Contexto(actual, enemigo);

    ctx.setJuego(juego);

    if (carta.necesitaMonstruoPropio()) {

        if (actual.getCampo().isEmpty()) {
            System.out.println("  Esta magia necesita un monstruo propio en campo");
            return;
        }
                   System.out.println("  Elige el monstruo propio objetivo:");

            actual.mostrarCampo();

            System.out.println("  [0] Cancelar");

            int idx = leerEntero(0, actual.getCampo().size());

            if (idx == 0) {
                return;
            }

            ctx.setMonstruoPropio(actual.getCampo().get(idx - 1));
        }

        if (carta.necesitaMonstruoEnemigo()) {

            if (enemigo.getCampo().isEmpty()) {
                System.out.println("  Esta magia necesita un monstruo enemigo en campo.");
                return;
            }

            System.out.println("  Elige el monstruo enemigo objetivo:");

            enemigo.mostrarCampo();

            System.out.println("  [0] Cancelar");

            int idx = leerEntero(0, enemigo.getCampo().size());

            if (idx == 0) {
                return;
            }

            ctx.setMonstruoEnemigo(enemigo.getCampo().get(idx - 1));
        }

        boolean exito = actual.jugarMagia(carta);

        if (exito) {
            carta.activar(ctx);
        }
    }

    private static void colocarTrampaDesdeMenu(CartaTrampa trampa, Jugador actual) {

        System.out.println();

        actual.colocarTrampa(trampa);
    }















    
    private static void ejecutarFaseBatalla(Jugador actual, Jugador enemigo) {

        System.out.println();
        System.out.println("[ Fase de Batalla - " + actual.getNombre() + " ]");


        if (juego.esPrimerTurno()) {
            System.out.println("  (Primer turno: no se puede atacar. ¡Seria injusto!)");
            return;
        }

        if (actual.getCampo().isEmpty()) {
            System.out.println("  No tienes monstruos en campo para atacar.");
            return;
        }

        // al menos un monstruo que pueda atacar
        boolean hayAtacantes = false;

        for (Monstruo m : actual.getCampo()) {

            if (m.puedeAtacar()) {
                hayAtacantes = true;
                break;
            }
        }

        if (!hayAtacantes) {
            System.out.println("  Todos tus monstruos ya atacaron este turno.");
            return;
        }

        while (true) {

            System.out.println();
            System.out.println("  ¿Deseas declarar un ataque?");
            System.out.println("  [1] Si, atacar con un monstruo");
            System.out.println("  [2] No, terminar fase de batalla");

            int opcion = leerEntero(1, 2);

            if (opcion == 2) {
                System.out.println("  " + actual.getNombre() + " decide no atacar mas.");
                break;
            }

            System.out.println();
            System.out.println("  Elige tu monstruo atacante:");

            ArrayList<Monstruo> campo = actual.getCampo();

            for (int i = 0; i < campo.size(); i++) {

                String puedeAtacar = campo.get(i).puedeAtacar()
                        ? ""
                        : " (ya ataco)";

                System.out.println("  [" + (i + 1) + "] "
                        + campo.get(i)
                        + puedeAtacar);
            }

            System.out.println("  [0] Cancelar ataque");

            int idxAtacante = leerEntero(0, campo.size());

            if (idxAtacante == 0) {
                System.out.println("  Ataque cancelado.");
                break;
            }

            Monstruo atacante = campo.get(idxAtacante - 1);
            
                      if (!atacante.puedeAtacar()) {
                System.out.println("  " + atacante.getNombre() + " ya ataco este turno.");
                continue;
            }

            Contexto ctxTrampa = null;

            if (enemigo.tieneTrampas()) {
                System.out.println();
                System.out.println("  !! El oponente activa sus trampas !!");

                ctxTrampa = enemigo.activarTrampas(actual, atacante);
            }

            // algunas trampas pueden cancelar completamente el ataque
            if (ctxTrampa != null && ctxTrampa.isAtaqueAnulado()) {

                System.out.println("  El ataque fue cancelado por una trampa!");

                atacante.marcarComoAtacado();

                if (juego.hayGanador()) {
                    break;
                }

                continue;
            }

            Monstruo defensor = null;

            if (!enemigo.getCampo().isEmpty()) {

                System.out.println();
                System.out.println("  Campo de " + enemigo.getNombre() + ":");

                ArrayList<Monstruo> campoEnemigo = enemigo.getCampo();

                for (int i = 0; i < campoEnemigo.size(); i++) {
                    System.out.println("  [" + (i + 1) + "] " + campoEnemigo.get(i));
                }

                System.out.println("  [0] Cancelar ataque");

                int idxDefensor = leerEntero(0, campoEnemigo.size());

                if (idxDefensor == 0) {
                    System.out.println("  Ataque cancelado.");
                    continue;
                }

                defensor = campoEnemigo.get(idxDefensor - 1);

            } else {

                System.out.println("  El oponente no tiene monstruos. ¡Ataque directo!");
            }

            actual.atacarConMonstruo(atacante, enemigo, defensor);

            if (juego.hayGanador()) {
                break;
            }

            boolean quedanAtacantes = false;

            for (Monstruo m : actual.getCampo()) {

                if (m.puedeAtacar()) {
                    quedanAtacantes = true;
                    break;
                }
            }

            if (!quedanAtacantes) {
                System.out.println("  Todos tus monstruos ya atacaron.");
                break;
            }
        }
    }


    private static void mostrarPantallaFinal(int turnosJugados) {

        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("  ¡FIN DEL DUELO!");
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();

        if (juego.hayGanador()) {

            String ganador = juego.getNombreGanador();

            Jugador perdedor = juego.getGanador() == juego.getJugador1()
                    ? juego.getJugador2()
                    : juego.getJugador1();

            System.out.println("  ★ ¡¡ " + ganador + " GANA EL DUELO !! ★");

            System.out.println();
            System.out.println("  \"Confía en el corazón de las cartas.\" — Yugi Muto");
            System.out.println();

            System.out.println("  Estado final:");

            System.out.println("  → " + juego.getGanador().getNombre()
                    + " termina con " + juego.getGanador().getVida() + " LP");

            System.out.println("  → " + perdedor.getNombre()
                    + " termina con " + perdedor.getVida() + " LP");
        }

        System.out.println("  Turnos jugados: " + turnosJugados);
        System.out.println();
    }


    private static void mostrarBienvenida() {
           System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("    YU-GI-OH! SIMULATOR  ");
        System.out.println("  Mini Proyecto 2 — Programación Orientada a Eventos");
        System.out.println("  Java 21  |  Consola");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.println();

        System.out.println("  Reglas del duelo:");
        System.out.println("  - 50 cartas: 30 monstruos, 10 magicas, 10 trampas");
        System.out.println("  - Cada jugador inicia con 8000 LP y 5 cartas en mano");
        System.out.println("  - Se roba 1 carta por turno. Solo 1 carta se puede jugar");
        System.out.println("  - Monstruos de nivel > 4 requieren sacrificio");
        System.out.println("  - Pierdes si tus LP llegan a 0 o tu mazo se agota");

        System.out.println();
    }

    private static int leerEntero(int min, int max) {

        while (true) {

            System.out.print("  Opcion (" + min + "-" + max + "): ");

            try {

                String input = scanner.nextLine().trim();
                int valor = Integer.parseInt(input);

                if (valor >= min && valor <= max) {
                    return valor;
                }

                System.out.println("  Por favor ingresa un numero entre "
                        + min + " y " + max + ".");

            } catch (NumberFormatException e) {

                System.out.println("  Entrada invalida. Ingresa un numero.");
            }
        }
    }

    private static String leerTexto() {

        while (true) {

            String texto = scanner.nextLine().trim();

            if (!texto.isEmpty()) {
                return texto;
            }

            System.out.print("  El nombre no puede estar vacio. Intentalo de nuevo: ");
        }
    }

    private static void pausar() {

        System.out.println("  [ Presiona Enter para continuar... ]");

        scanner.nextLine();
    }
}