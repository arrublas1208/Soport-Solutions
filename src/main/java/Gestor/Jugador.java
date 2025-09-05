/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

/**
 *
 * @author camper
 */
// Objetivo cumplir con el principio SRP 

class Jugador {
private final String nombre;
private final String posicion; // Posibles valores: "Portero", "Delantero","Defensa"
public Jugador(String nombre, String posicion) { this.nombre = nombre;
this.posicion = posicion; }
public String getNombre() { return nombre; }
public String getPosicion() { return this.posicion; }
}
