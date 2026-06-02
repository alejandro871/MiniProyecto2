package juego;

import cartas.Carta;
import cartas.Monstruo;
import cartas.CartaMagica;
import cartas.CartaTrampa;
import efectos.*;
import jugadores.Jugador;

import java.util.ArrayList;
import java.util.Collections;

// creamos mazo y repartimos cartas
public class Mazo {

    // creamos y retornamos el mazo completo de 50 cartas
    public static ArrayList<Carta> crearMazo() {
        ArrayList<Carta> mazo = new ArrayList<>();

        // MONSTRUOS (30 cartas)

        // Monstruos de nivel bajo (1-4, sin sacrificio) 
        mazo.add(new Monstruo("Dragon Blanco", 2200, 2000, 2, "Un dragon enorme que inspira respeto apenas aparece"));
        mazo.add(new Monstruo("Guerrero", 900, 1000, 3, "Luchador valiente que nunca retrocede"));
        mazo.add(new Monstruo("Mago", 1500, 1400, 3, "Mago que siempre tiene un truco bajo la manga"));
        mazo.add(new Monstruo("Bestia", 2000, 1800, 1, "Criatura salvaje que vive lejos de la civilizacion"));
        mazo.add(new Monstruo("Titan", 2100, 1900, 7, "Gigante tan antiguo que nadie recuerda su origen"));
        mazo.add(new Monstruo("Caballero", 1100, 900, 2, "Guerrero honorable dispuesto a proteger a los suyos"));
        mazo.add(new Monstruo("Zombie", 800, 900, 1, "Parece derrotado pero siempre encuentra la forma de volver"));
        mazo.add(new Monstruo("Vampiro", 2600, 1000, 9, "Se alimenta de la energia de quienes enfrenta"));
        mazo.add(new Monstruo("Pitbull", 2800, 3000, 12, "Perro fuerte y decidido que no le teme a nada"));
        mazo.add(new Monstruo("Mesini", 2800, 2500, 10, "Masa de piedra viviente creada para la batalla"));
        mazo.add(new Monstruo("Esqueleto", 1000, 1200, 3, "Guerrero de huesos que sigue luchando aun despues de caer"));
        mazo.add(new Monstruo("Tarantula", 1100, 900, 4, "Araña enorme que espera pacientemente a su presa"));
        mazo.add(new Monstruo("Gigante", 1800, 2500, 9, "Su tamaño intimida incluso a los monstruos mas fuertes"));
        mazo.add(new Monstruo("Ciclope", 2800, 2000, 2, "Criatura de un solo ojo y una fuerza impresionante"));
        mazo.add(new Monstruo("Piraña", 700, 1000, 1, "Pequeña pero muy peligrosa cuando encuentra una oportunidad"));
        mazo.add(new Monstruo("Principe", 800, 800, 1, "Joven noble que sueña con convertirse en rey"));
        mazo.add(new Monstruo("Principe Oscuro", 1200, 1000, 2, "Heredero de un reino envuelto en misterios"));
        mazo.add(new Monstruo("Rey Esqueleto", 2500, 2900, 2, "Gobernante de un ejercito que nunca descansa"));
        mazo.add(new Monstruo("Caballero Dorado", 1700, 800, 3, "Su armadura brillante destaca en cualquier batalla"));
        mazo.add(new Monstruo("Golem Oscuro", 2300, 2200, 8, "Criatura nacida de roca y sombras"));
        mazo.add(new Monstruo("Leon", 2100, 2500, 8, "Protector feroz de su territorio"));
        mazo.add(new Monstruo("Mago Oscuro", 1500, 1200, 3, "Hechicero que domina poderes poco conocidos"));
        mazo.add(new Monstruo("Mago Electrico", 1000, 1100, 1, "Controla pequeñas descargas para sorprender a sus rivales"));
        mazo.add(new Monstruo("Furia Nocturna", 2600, 2800, 11, "Bestia que se vuelve mas peligrosa cuando cae la noche"));
        mazo.add(new Monstruo("Duende", 2500, 2000, 7, "Ser travieso que siempre parece estar planeando algo"));
        mazo.add(new Monstruo("Anguila", 1400, 1400, 4, "Habitante de las profundidades con energia electrica"));
        mazo.add(new Monstruo("Lombriz Sangrienta", 1900, 2000, 4, "Criatura extraña capaz de sobrevivir en cualquier lugar"));
        mazo.add(new Monstruo("Araña Aguja", 2200, 2000, 3, "Sus patas afiladas son tan peligrosas como sus colmillos"));
        mazo.add(new Monstruo("Sativa", 2200, 2800, 10, "Perrita fiel y acompañante de toda la vida"));
        mazo.add(new Monstruo("Golem", 2000, 2500, 3, "Guardian de piedra que protege a sus aliados"));

        // CARTAS MAGICAS (10 cartas)

        mazo.add(new CartaMagica(
                "Pot of Greed",
                "Roba 2 cartas adicionales del mazo",
                new PotOfGreed()));

        mazo.add(new CartaMagica(
                "Curacion Divina",
                "Recupera 1500 LP",
                new EfectoCuracion(1500)));

        mazo.add(new CartaMagica(
                "Orden de Destruccion",
                "Destruye un monstruo enemigo que elijas",
                new DestruirMonstruo()));

        mazo.add(new CartaMagica(
                "Rayo Oscuro",
                "Inflige 800 puntos de danio directo al oponente",
                new DanioInstantaneo(800)));

        mazo.add(new CartaMagica(
                "Espada de Rafflesia",
                "Aumenta el ATK de un monstruo propio en 700",
                new AumentoAtaque(700)));

        mazo.add(new CartaMagica(
                "Escudo de la Victoria",
                "Aumenta la DEF de un monstruo propio en 900",
                new Escudo(900)));

        mazo.add(new CartaMagica(
                "Berserker Soul",
                "Duplica el ATK de un monstruo propio esta batalla",
                new DobleAtaque()));

        mazo.add(new CartaMagica(
                "Grieta de Poder",
                "Reduce el ATK de un monstruo enemigo en 600",
                new EfectoDebilidad(600)));

        mazo.add(new CartaMagica(
                "Drenaje de Vida",
                "Drena 600 LP del oponente y te los transfiere",
                new EfectoDrenaje(600)));

        mazo.add(new CartaMagica(
                "Pacto de la Sabiduria",
                "Roba 1 carta y recupera 300 LP",
                new RoboDefinitivo()));

        // CARTAS TRAMPA (10 cartas) 

        mazo.add(new CartaTrampa(
                "Trampa Mortal",
                "Destruye el monstruo atacante y cancela el ataque",
                new TrampaDestructor()));

        mazo.add(new CartaTrampa(
                "Muro de Defensa",
                "Aumenta la DEF de tu primer monstruo en campo en 800",
                new TrampaDefensa(800)));

        mazo.add(new CartaTrampa(
                "Espejo de Anulacion",
                "Bloquea completamente el ataque enemigo",
                new TrampaBloqueo()));

        mazo.add(new CartaTrampa(
                "Contraataque Espejo",
                "Refleja 700 puntos de danio al jugador que ataco",
                new TrampaDevolucion(700)));

        mazo.add(new CartaTrampa(
                "Ladron de Almas",
                "Descarta 1 carta aleatoria de la mano del oponente",
                new TrampaRobo()));

        mazo.add(new CartaTrampa(
                "Lagrima del Fénix",
                "Al ser atacado, recupera 600 LP",
                new TrampaCuracion(600)));

        mazo.add(new CartaTrampa(
                "Veneno Oscuro",
                "Reduce el ATK del monstruo atacante en 600",
                new TrampaDebilidad(600)));

        mazo.add(new CartaTrampa(
                "Giro del Destino",
                "Voltea el monstruo atacante a posicion de defensa",
                new TrampaVolteada()));

        mazo.add(new CartaTrampa(
                "Llamada del Cementerio",
                "Recupera la ultima carta del cementerio a tu mano",
                new TrampaRecuperacion()));

        mazo.add(new CartaTrampa(
                "Fortaleza Impenetrable",
                "Duplica la DEF de tu primer monstruo en campo",
                new TrampaDoble()));

        //el mazo SIEMPRE debe tener exactamente 50 cartas
        System.out.println("Mazo creado con " + mazo.size() + " cartas "
                + "(deberian ser 50: 30 monstruos, 10 magicas, 10 trampas).");

        return mazo;
    }

    public static void repartir(Jugador j1, Jugador j2) {
        ArrayList<Carta> mazo = crearMazo();

        // revolvemos para que las cartas sean aleatorias
        Collections.shuffle(mazo);

        // Las primeras 25 cartas van al jugador 1
        for (int i = 0; i < 25; i++) {
            j1.agregarCarta(mazo.get(i));
        }

        // Las siguientes 25 cartas van al jugador 2
        for (int i = 25; i < 50; i++) {
            j2.agregarCarta(mazo.get(i));
        }

        // Cada jugador toma su mano inicial de 5 cartas
        j1.tomarManoInicial();
        j2.tomarManoInicial();

        System.out.println("Cartas repartidas: 25 para " + j1.getNombre()
                + " y 25 para " + j2.getNombre()
                + ". Cada uno tiene 5 en mano y 20 en mazo.");
    }
}
