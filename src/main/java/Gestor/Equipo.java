package gestor;


import gestor.Jugador;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author camper
 */

// Objetivo cumplir con el principio SRP 

class Equipo {
private final String nombre;
private final List<Jugador> jugadores = new ArrayList<>();
public Equipo(String nombre) { this.nombre = nombre; }
public String getNombre() { return nombre; }
public void agregarJugador(Jugador j) { this.jugadores.add(j); }
public List<Jugador> getJugadores() { return this.jugadores; }

}
