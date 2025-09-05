/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestor;

/**
 *
 * @author camper
 */
public interface RegistroParticipante {
    
}
public void registrarParticipantes() {
// Registrar equipos
Equipo equipoA = new Equipo("Los Ganadores");
equipoA.agregarJugador(new Jugador("Juan Pérez", "Delantero"));
equipoA.agregarJugador(new Jugador("Pedro Pan", "Portero"));
equipos.add(equipoA);
System.out.println("Equipo 'Los Ganadores' registrado.");
Equipo equipoB = new Equipo("Los Retadores");
equipoB.agregarJugador(new Jugador("Alicia Smith", "Defensa"));

equipos.add(equipoB);
System.out.println("Equipo 'Los Retadores' registrado.");
// Contratar árbitros
arbitros.add(new Arbitro("Miguel Díaz"));
System.out.println("Árbitro 'Miguel Díaz' contratado.");
}