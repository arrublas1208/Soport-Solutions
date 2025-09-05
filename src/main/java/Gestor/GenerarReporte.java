/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package gestor;

/**
 *
 * @author camper
 */

public void generarReportes(String formato) {
if (formato.equalsIgnoreCase("TEXTO")) {
String contenidoReporte = "--- Reporte del Campeonato (TEXTO) ---\n";
contenidoReporte += "EQUIPOS:\n";
for (Equipo equipo : equipos) {
contenidoReporte += "- " + equipo.getNombre() + "\n";
}
contenidoReporte += "ÁRBITROS:\n";
for (Arbitro arbitro : arbitros) {
contenidoReporte += "- " + arbitro.getNombre() + "\n";
}
System.out.println(contenidoReporte);
} else if (formato.equalsIgnoreCase("HTML")) {
String contenidoHtml = "<html><body>\n";
contenidoHtml += " <h1>Reporte del Campeonato</h1>\n";
contenidoHtml += " <h2>Equipos</h2>\n <ul>\n";
for (Equipo equipo : equipos) {
contenidoHtml += " <li>" + equipo.getNombre() + "</li>\n";
}
contenidoHtml += " </ul>\n <h2>Árbitros</h2>\n <ul>\n";
for (Arbitro arbitro : arbitros) {
contenidoHtml += " <li>" + arbitro.getNombre() + "</li>\n";

}
contenidoHtml += " </ul>\n</body></html>";
System.out.println(contenidoHtml);
}
}