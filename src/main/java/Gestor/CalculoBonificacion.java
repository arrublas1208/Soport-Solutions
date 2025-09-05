/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestor;

/**
 *
 * @author camper
 */
public interface CalculoBonificacion {
    
}
public void calcularBonificaciones() {
System.out.println("\n--- Calculando Bonificaciones de Jugadores ---");
for (Equipo equipo : equipos) {
for (Jugador jugador : equipo.getJugadores()) {
if (jugador.getPosicion().equals("Delantero")) {
System.out.println("Calculando bonificación alta para

Delantero: " + jugador.getNombre());

} else if (jugador.getPosicion().equals("Portero")) {
System.out.println("Calculando bonificación estándar para

Portero: " + jugador.getNombre());

} else {
System.out.println("Calculando bonificación base para: " +

jugador.getNombre());
}
}
}
}