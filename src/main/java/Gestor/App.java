/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestor;

/**
 *
 * @author camper
 */
public class App {
    
}
public static void main(String[] args) {
GestorCampeonato gestor = new GestorCampeonato();
gestor.registrarParticipantes();
gestor.calcularBonificaciones();
System.out.println("\n--- Generando Reportes ---");
gestor.generarReportes("TEXTO");
System.out.println("\n--- Generando más Reportes ---");
gestor.generarReportes("HTML");
}
}