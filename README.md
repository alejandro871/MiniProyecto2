# 🃏 Mini Proyecto 2 — Yu-Gi-Oh! Simulador GUI

## Integrantes

* Alejandro Jaramillo

---

## Descripción

Simulador de duelo de Yu-Gi-Oh! desarrollado en Java 21 con interfaz gráfica usando **Swing**.
Permite que dos jugadores se enfrenten usando monstruos, cartas mágicas y cartas trampa,
todo a través de una interfaz visual con fondo oscuro temático.

---

## Instrucciones de ejecución

### Requisitos
- Java 21 (JDK)
- No se requieren librerías externas

## Cómo se juega

1. Al iniciar aparece la **Pantalla de Inicio**: ingresa los nombres de los dos duelistas
2. El azar decide quién empieza
3. Cada turno:
   - Presiona **" Robar Carta"** al inicio del turno
   - Haz clic en una carta de tu mano (panel inferior) para jugarla
   - Usa el botón **" Atacar"** para declarar ataques con tus monstruos
   - Cuando el enemigo ataca, verás un diálogo preguntándote si quieres activar trampas
   - Presiona **" Terminar Turno"** para pasar al siguiente jugador
4. Gana quien reduzca los LP del rival a 0 o lo haga quedarse sin cartas en el mazo

---

## Cartas implementadas (50 en total)

### 30 Monstruos
Dragon Blanco, Guerrero, Mago, Bestia, Titan, Caballero, Zombie, Vampiro,
Pitbull, Mesini, Esqueleto, Tarantula, Gigante, Ciclope, Piraña, Principe,
Principe Oscuro, Rey Esqueleto, Caballero Dorado, Golem Oscuro, Leon,
Mago Oscuro, Mago Electrico, Furia Nocturna, Duende, Anguila, Lombriz Sangrienta,
Araña Aguja, Sativa, Golem

### 10 Cartas Mágicas
Pot of Greed, Curacion Divina, Orden de Destruccion, Rayo Oscuro,
Espada de Rafflesia, Escudo de la Victoria, Berserker Soul, Grieta de Poder,
Drenaje de Vida, Pacto de la Sabiduria

### 10 Cartas Trampa
Trampa Mortal, Muro de Defensa, Espejo de Anulacion, Contraataque Espejo,
Ladron de Almas, Lagrima del Fénix, Veneno Oscuro, Giro del Destino,
Llamada del Cementerio, Fortaleza Impenetrable

---

## Conceptos OOP implementados

| Concepto | Dónde se aplica |
|---|---|
| **Clases abstractas** | `Carta` — no se puede instanciar directamente |
| **Herencia** | `Monstruo`, `CartaMagica`, `CartaTrampa` extienden `Carta` |
| **Interfaces** | `Activable` (cartas con efectos), `Efecto` (lógica de efectos) |
| **Encapsulamiento** | Atributos privados con getters/setters en todas las clases |
| **Polimorfismo** | `Activable.activar(ctx)` — cada carta ejecuta su propio efecto |

---
