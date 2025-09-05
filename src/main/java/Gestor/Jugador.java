/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestor;

/**
 *
 * @author Usuario
 */
class Jugador {
    private final String nombre;
    private final String posicion; // "Portero", "Delantero", "Defensa", etc.
    public Jugador(String nombre, String posicion) { this.nombre = nombre; this.posicion = posicion; }
    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
}