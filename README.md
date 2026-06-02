# 🃏 Mini Proyecto 2 - Yu-Gi-Oh! (Versión Consola)

## Integrantes

* Alejandro Jaramillo

---

## Descripción

Este proyecto corresponde al Mini Proyecto 2 de Programación Orientada a Objetos.

La idea fue desarrollar una versión simplificada de Yu-Gi-Oh! utilizando Java y aplicando conceptos vistos durante el semestre como herencia, encapsulamiento, clases abstractas, interfaces y polimorfismo.

El juego funciona completamente por consola y permite que dos jugadores se enfrenten utilizando monstruos, cartas mágicas y cartas trampa.

---

## ¿Qué se puede hacer?

 Robar cartas

 Invocar monstruos

 Utilizar cartas mágicas

 Colocar trampas

 Atacar monstruos enemigos

 Realizar ataques directos

 Sacrificar monstruos para invocar cartas de nivel alto

 Ganar por reducción de LP o por agotamiento del mazo

---

## Cartas implementadas

Actualmente el juego cuenta con:

* 30 Monstruos
* 10 Cartas Mágicas
* 10 Cartas Trampa

Total:

**50 cartas**

Como todo buen duelo serio entre amigos que no quieren estudiar para parciales.

---

## Conceptos de POO utilizados

### Herencia

Las clases:

* Monstruo
* CartaMagica
* CartaTrampa

heredan de la clase abstracta Carta.

### Encapsulamiento

Los atributos se manejan mediante getters y setters.

### Interfaces

Se utiliza la interfaz Activable para representar cartas que pueden ejecutar efectos.

### Polimorfismo

Las cartas se manejan mediante referencias generales y cada una ejecuta comportamientos distintos según su tipo.

### Clases abstractas

Carta se implementó como una clase abstracta para representar cualquier carta del juego.

---

## Reglas principales

* Cada jugador inicia con 8000 LP.
* Cada jugador roba 5 cartas al comenzar.
* Se roba 1 carta al inicio de cada turno.
* Solo se puede jugar una carta por turno.
* Los monstruos de nivel mayor a 4 requieren sacrificio.
* En el primer turno no se puede atacar.
* Un monstruo solo puede atacar una vez por turno.
* Si un jugador se queda sin LP pierde.
* Si un jugador intenta robar y no tiene cartas disponibles también pierde.

