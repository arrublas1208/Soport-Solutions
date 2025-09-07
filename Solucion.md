# Principios SOLID



## 👥 Miembros del Quiz

**Integrantes:**

- Carlos Cisneros
- Juan Jose Arrubla









## 1. SRP (Single Responsibility Principle)

Aplicaremos el primer principio en las clases `Jugador`, `Arbitro` y `Equipo` validando que solo cumplan una sola responsabilidad.

### 🔧 Código Anterior

```java
import java.util.ArrayList;
import java.util.List;

// --- Clases de Entidad (simuladas para el ejercicio) ---
class Equipo {
    private String nombre;
    private List<Jugador> jugadores = new ArrayList<>();
    public Equipo(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    public void agregarJugador(Jugador j) { this.jugadores.add(j); }
    public List<Jugador> getJugadores() { return this.jugadores; }
}

class Jugador {
    private String nombre;
    private String posicion; // Posibles valores: "Portero", "Delantero", "Defensa"
    public Jugador(String nombre, String posicion) { 
        this.nombre = nombre;
        this.posicion = posicion; 
    }
    public String getNombre() { return nombre; }
    public String getPosicion() { return this.posicion; }
}

class Arbitro {
    private String nombre;
    public Arbitro(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
}
```



### ✅ Resultado

#### **Clase Arbitro**

java

```
package gestor;

/**
 * @author camper
 * Objetivo: cumplir con el principio SRP
 */
class Arbitro {
    private final String nombre;
    
    public Arbitro(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
}
```



#### **Clase Jugador**

java

```
package gestor;

/**
 * @author camper
 * Objetivo: cumplir con el principio SRP
 */
class Jugador {
    private final String nombre;
    private final String posicion; // Posibles valores: "Portero", "Delantero", "Defensa"
    
    public Jugador(String nombre, String posicion) { 
        this.nombre = nombre;
        this.posicion = posicion; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public String getPosicion() { 
        return this.posicion; 
    }
}
```



#### **Clase Equipo**

java

```
import gestor.Jugador;
import java.util.ArrayList;
import java.util.List;

/**
 * @author camper
 * Objetivo: cumplir con el principio SRP
 */
class Equipo {
    private final String nombre;
    private final List<Jugador> jugadores = new ArrayList<>();
    
    public Equipo(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void agregarJugador(Jugador j) { 
        this.jugadores.add(j); 
    }
    
    public List<Jugador> getJugadores() { 
        return this.jugadores; 
    }
}
```



------

# 2. OCP (Open/Closed Principle)

Aplicaremos este principio en las funcionalidades de **cálculo de bonificaciones** y **generación de reportes**, ya que en el código original se usaban múltiples condicionales `if/else` que obligaban a modificar la clase cada vez que aparecía un nuevo tipo de jugador o un nuevo formato de reporte.

---

## 🔧 Código Anterior (Violación OCP)

```java
// Dentro de la clase GestorCampeonato

// Cálculo de bonificaciones
for (Jugador jugador : equipo.getJugadores()) {
    if (jugador.getPosicion().equals("Delantero")) {
        System.out.println("Calculando bonificación alta para Delantero: " + jugador.getNombre());
    } else if (jugador.getPosicion().equals("Portero")) {
        System.out.println("Calculando bonificación estándar para Portero: " + jugador.getNombre());
    } else {
        System.out.println("Calculando bonificación base para: " + jugador.getNombre());
    }
}

// Generación de reportes
if (formato.equalsIgnoreCase("TEXTO")) {
    // Genera el reporte en texto
} else if (formato.equalsIgnoreCase("HTML")) {
    // Genera el reporte en HTML
}
```

---

## ❌ Problema

Cada vez que agregamos una nueva posición (ej. `"Mediocampista"`) o un nuevo formato de reporte (ej. `JSON`), debemos modificar el código existente, **rompiendo el principio OCP**.

---

## ✅ Resultado (Cumpliendo OCP)

### 📌 Bonificaciones

```java
// Contrato para cualquier tipo de bonificación
interface BonificacionStrategy {
    void calcular(Jugador jugador);
}

// Implementaciones concretas
class BonificacionDelantero implements BonificacionStrategy {
    @Override
    public void calcular(Jugador jugador) {
        System.out.println("Calculando bonificación ALTA para Delantero: " + jugador.getNombre());
    }
}

class BonificacionPortero implements BonificacionStrategy {
    @Override
    public void calcular(Jugador jugador) {
        System.out.println("Calculando bonificación ESTÁNDAR para Portero: " + jugador.getNombre());
    }
}

class BonificacionDefensa implements BonificacionStrategy {
    @Override
    public void calcular(Jugador jugador) {
        System.out.println("Calculando bonificación BASE para Defensa: " + jugador.getNombre());
    }
}
```

**Uso en GestorCampeonato:**

```java
for (Jugador jugador : equipo.getJugadores()) {
    BonificacionStrategy estrategia;
    switch (jugador.getPosicion()) {
        case "Delantero": estrategia = new BonificacionDelantero(); break;
        case "Portero": estrategia = new BonificacionPortero(); break;
        default: estrategia = new BonificacionDefensa(); break;
    }
    estrategia.calcular(jugador);
}
```

✔ Ahora si agregamos `BonificacionMediocampista`, solo creamos una nueva clase que implemente `BonificacionStrategy`.

---

### 📌 Reportes

```java
// Contrato para cualquier tipo de reporte
interface Reporte {
    void generar(List<Equipo> equipos, List<Arbitro> arbitros);
}

// Implementación en texto
class ReporteTexto implements Reporte {
    @Override
    public void generar(List<Equipo> equipos, List<Arbitro> arbitros) {
        String reporte = "--- Reporte del Campeonato (TEXTO) ---\nEQUIPOS:\n";
        for (Equipo e : equipos) reporte += "- " + e.getNombre() + "\n";
        reporte += "ÁRBITROS:\n";
        for (Arbitro a : arbitros) reporte += "- " + a.getNombre() + "\n";
        System.out.println(reporte);
    }
}

// Implementación en HTML
class ReporteHtml implements Reporte {
    @Override
    public void generar(List<Equipo> equipos, List<Arbitro> arbitros) {
        String html = "<html><body>\n<h1>Reporte del Campeonato</h1>\n<h2>Equipos</h2><ul>\n";
        for (Equipo e : equipos) html += "<li>" + e.getNombre() + "</li>\n";
        html += "</ul>\n<h2>Árbitros</h2><ul>\n";
        for (Arbitro a : arbitros) html += "<li>" + a.getNombre() + "</li>\n";
        html += "</ul></body></html>";
        System.out.println(html);
    }
}
```

**Uso en GestorCampeonato:**

```java
Reporte reporte = new ReporteTexto(); 
reporte.generar(equipos, arbitros);

Reporte reporteHtml = new ReporteHtml();
reporteHtml.generar(equipos, arbitros);
```


# 3. LSP (Liskov Substitution Principle)

## 📌 Violación en el código original

En la versión original, la clase `Arbitro` está bien para un escenario simple, pero si en el futuro se añadieran más comportamientos (por ejemplo, *viajar al extranjero*), todos los árbitros estarían obligados a implementarlos, aunque no todos pudieran hacerlo.  
Esto violaría **LSP**, porque una subclase (ej. `ArbitroNacional`) no podría sustituir al padre sin lanzar errores (`UnsupportedOperationException`).

---

## 🔧 Código Anterior

```java
class Arbitro {
    private String nombre;
    public Arbitro(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
}
```

---

## ✅ Refactorización

Creamos una clase base abstracta (`ArbitroBase`) con el contrato mínimo y dejamos la clase `Arbitro` como implementación concreta, asegurando que pueda sustituirse sin romper el sistema.  
Además, separamos capacidades opcionales en interfaces (ejemplo: `ArbitroInternacionalCapaz`) para que solo quien realmente pueda implementarlas lo haga.

```java
// Clase base abstracta con contrato mínimo
abstract class ArbitroBase {
    private final String nombre;

    protected ArbitroBase(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    // Contrato mínimo: todos los árbitros deben poder dirigir partidos
    public abstract void dirigirPartido();
}

// Clase concreta usada actualmente en el sistema
class Arbitro extends ArbitroBase {
    public Arbitro(String nombre) { super(nombre); }

    @Override
    public void dirigirPartido() {
        // Implementación mínima.
        // No imprimimos nada para mantener la salida idéntica al programa original.
    }
}

// Interfaz opcional para árbitros con capacidad de viajar
interface ArbitroInternacionalCapaz {
    void viajarAlExtranjero();
}
```

---

## 🎯 Justificación

- `ArbitroBase` define un contrato claro y mínimo que todas las subclases pueden cumplir.  
- `Arbitro` mantiene la misma funcionalidad que en el código original, por lo que la salida por consola sigue siendo idéntica.  
- Las capacidades opcionales (como viajar) se definen en interfaces aparte, evitando forzar métodos no aplicables a todas las subclases.  
- Ahora cualquier subclase de `ArbitroBase` puede sustituir a otra sin alterar el correcto funcionamiento del sistema.  

# 4. ISP (Interface Segregation Principle)

Cuando aplicamos **OCP**, ya introdujimos algo muy parecido:

- Creamos una **interfaz `Reporte`**.  
- Definimos implementaciones concretas como `ReporteTexto` y `ReporteHtml`.  

Eso ya es una mezcla de **OCP** (*cerrado a modificaciones, abierto a extensiones*) y **ISP** (*interfaces pequeñas y específicas*).  

De esta manera, cada implementación cumple solo con lo que realmente necesita, evitando interfaces demasiado grandes o genéricas que obliguen a implementar métodos innecesarios.



# 5. Principio de Inversión de Dependencias (DIP)

## 🔴 Violación en el código original

En el código inicial, la clase `GestorCampeonato` dependía directamente de las implementaciones concretas de reportes (texto y HTML).

```java
public void generarReportes(String formato) {
    if (formato.equalsIgnoreCase("TEXTO")) {
        // genera reporte en texto
    } else if (formato.equalsIgnoreCase("HTML")) {
        // genera reporte en HTML
    }
}
```

Esto viola el **DIP**, porque:

- `GestorCampeonato` es un módulo de **alto nivel** (contiene la lógica principal del campeonato).  
- Está acoplado a módulos de **bajo nivel** (`ReporteTexto`, `ReporteHtml`).  
- Si agregamos un nuevo formato (ej: `JSON`), tendríamos que modificar `GestorCampeonato`.  

---

## 🟢 Solución aplicando DIP

Creamos una **abstracción común (`Reporte`)** y hacemos que `GestorCampeonato` dependa de esta, no de implementaciones concretas.  
Luego, usamos **inyección de dependencias** para pasarle el tipo de reporte al momento de construir el gestor.

---

## ✅ Código refactorizado

### Abstracción

```java
interface Reporte {
    void generar(List<Equipo> equipos, List<Arbitro> arbitros);
}
```

### Implementaciones concretas

```java
class ReporteTexto implements Reporte {
    @Override
    public void generar(List<Equipo> equipos, List<Arbitro> arbitros) {
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
    }
}

class ReporteHtml implements Reporte {
    @Override
    public void generar(List<Equipo> equipos, List<Arbitro> arbitros) {
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
```

### Uso en `GestorCampeonato`

```java
public class GestorCampeonato {
    private List<Equipo> equipos = new ArrayList<>();
    private List<Arbitro> arbitros = new ArrayList<>();
    private Reporte reporte; // depende de la abstracción

    public GestorCampeonato(Reporte reporte) {
        this.reporte = reporte; // inyección de dependencias
    }

    public void registrarParticipantes() {
        Equipo equipoA = new Equipo("Los Ganadores");
        equipoA.agregarJugador(new Jugador("Juan Pérez", "Delantero"));
        equipoA.agregarJugador(new Jugador("Pedro Pan", "Portero"));
        equipos.add(equipoA);
        System.out.println("Equipo 'Los Ganadores' registrado.");

        Equipo equipoB = new Equipo("Los Retadores");
        equipoB.agregarJugador(new Jugador("Alicia Smith", "Defensa"));
        equipos.add(equipoB);
        System.out.println("Equipo 'Los Retadores' registrado.");

        arbitros.add(new Arbitro("Miguel Díaz"));
        System.out.println("Árbitro 'Miguel Díaz' contratado.");
    }

    public void calcularBonificaciones() {
        System.out.println("\n--- Calculando Bonificaciones de Jugadores ---");
        for (Equipo equipo : equipos) {
            for (Jugador jugador : equipo.getJugadores()) {
                if (jugador.getPosicion().equals("Delantero")) {
                    System.out.println("Calculando bonificación alta para Delantero: " + jugador.getNombre());
                } else if (jugador.getPosicion().equals("Portero")) {
                    System.out.println("Calculando bonificación estándar para Portero: " + jugador.getNombre());
                } else {
                    System.out.println("Calculando bonificación base para: " + jugador.getNombre());
                }
            }
        }
    }

    public void generarReporte() {
        reporte.generar(equipos, arbitros); // usa la abstracción
    }

    public static void main(String[] args) {
        // Inyectamos el reporte deseado
        GestorCampeonato gestorTexto = new GestorCampeonato(new ReporteTexto());
        gestorTexto.registrarParticipantes();
        gestorTexto.calcularBonificaciones();
        System.out.println("\n--- Generando Reporte en Texto ---");
        gestorTexto.generarReporte();

        System.out.println("\n--- Generando Reporte en HTML ---");
        GestorCampeonato gestorHtml = new GestorCampeonato(new ReporteHtml());
        gestorHtml.registrarParticipantes();
        gestorHtml.calcularBonificaciones();
        gestorHtml.generarReporte();
    }
}
```

---

## 🎯 Beneficios obtenidos

- `GestorCampeonato` ya no depende de implementaciones concretas, solo de la **abstracción `Reporte`**.  
- Podemos agregar nuevos formatos (`ReporteJSON`, `ReportePDF`, etc.) **sin modificar** `GestorCampeonato`.  
- El sistema queda más flexible, mantenible y escalable.








