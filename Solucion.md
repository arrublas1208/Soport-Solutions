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


# Código Final Completo

A continuación, todo el código refactorizado dentro del paquete **`com.campeonato.gestor`**.

## App.java

```java
package com.campeonato.gestor;

public class App {
    public static void main(String[] args) {
        GestorCampeonato gestor = new GestorCampeonato();
        gestor.registrarParticipantes();
        gestor.calcularBonificaciones();

        System.out.println("\n--- Reportes ---");
        gestor.generarReportes("TEXTO");

        System.out.println("\n--- más Reportes ---");
        gestor.generarReportes("HTML");
    }
}
```

## GestorCampeonato.java

```java
package com.campeonato.gestor;

import java.util.*;

public class GestorCampeonato {
    private ServicioRegistro servicioRegistro;
    private CalculoBonificacion calculoBonificacion;
    private GeneradorReporte generadorReporte;
    private List<Equipo> equipos;
    private List<Arbitro> arbitros;

    public GestorCampeonato() {
        this.servicioRegistro = new ServicioRegistro();
        this.calculoBonificacion = new CalculoBonificacion();
        this.generadorReporte = new GeneradorReporte();
        this.equipos = new ArrayList<>();
        this.arbitros = new ArrayList<>();
    }

    public void registrarParticipantes() {
        this.equipos = servicioRegistro.registrarEquipos();
        this.arbitros = servicioRegistro.registrarArbitros();
    }

    public void calcularBonificaciones() {
        for (Equipo equipo : equipos) {
            for (Jugador jugador : equipo.getJugadores()) {
                BonificacionStrategy estrategia = BonificacionFactory.obtenerEstrategia(jugador.getPosicion());
                calculoBonificacion.aplicarBonificacion(jugador, estrategia);
            }
        }
    }

    public void generarReportes(String tipo) {
        Reporte reporte;
        if ("TEXTO".equals(tipo)) {
            reporte = new ReporteTexto();
        } else {
            reporte = new ReporteHtml();
        }
        generadorReporte.generar(reporte, equipos, arbitros);
    }
}
```

## ServicioRegistro.java

```java
package com.campeonato.gestor;

import java.util.*;

public class ServicioRegistro {
    public List<Equipo> registrarEquipos() {
        List<Equipo> equipos = new ArrayList<>();

        Equipo e1 = new Equipo("Dragones");
        e1.agregarJugador(new Jugador("Carlos", "Delantero"));
        e1.agregarJugador(new Jugador("Luis", "Portero"));
        equipos.add(e1);

        Equipo e2 = new Equipo("Tigres");
        e2.agregarJugador(new Jugador("Ana", "Defensa"));
        e2.agregarJugador(new Jugador("Marta", "Delantero"));
        equipos.add(e2);

        return equipos;
    }

    public List<Arbitro> registrarArbitros() {
        List<Arbitro> arbitros = new ArrayList<>();
        arbitros.add(new Arbitro("Pedro"));
        arbitros.add(new Arbitro("Lucía"));
        return arbitros;
    }
}
```

## CalculoBonificacion.java

```java
package com.campeonato.gestor;

public class CalculoBonificacion {
    public void aplicarBonificacion(Jugador jugador, BonificacionStrategy estrategia) {
        double bonificacion = estrategia.calcular(jugador);
        jugador.setBonificacion(bonificacion);
    }
}
```

## GeneradorReporte.java

```java
package com.campeonato.gestor;

import java.util.*;

public class GeneradorReporte {
    public void generar(Reporte reporte, List<Equipo> equipos, List<Arbitro> arbitros) {
        reporte.generar(equipos, arbitros);
    }
}
```

## Entidades

### Equipo.java

```java
package com.campeonato.gestor;

import java.util.*;

public class Equipo {
    private String nombre;
    private List<Jugador> jugadores;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.jugadores = new ArrayList<>();
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public String getNombre() { return nombre; }
    public List<Jugador> getJugadores() { return jugadores; }
}
```

### Jugador.java

```java
package com.campeonato.gestor;

public class Jugador {
    private String nombre;
    private String posicion;
    private double bonificacion;

    public Jugador(String nombre, String posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
    }

    public String getNombre() { return nombre; }
    public String getPosicion() { return posicion; }
    public double getBonificacion() { return bonificacion; }
    public void setBonificacion(double bonificacion) { this.bonificacion = bonificacion; }
}
```

### Arbitro.java

```java
package com.campeonato.gestor;

public class Arbitro {
    private String nombre;

    public Arbitro(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
}
```

## Estrategias de Bonificación

### BonificacionStrategy.java

```java
package com.campeonato.gestor;

public interface BonificacionStrategy {
    double calcular(Jugador jugador);
}
```

### BonificacionDelantero.java

```java
package com.campeonato.gestor;

public class BonificacionDelantero implements BonificacionStrategy {
    public double calcular(Jugador jugador) {
        return 100.0;
    }
}
```

### BonificacionPortero.java

```java
package com.campeonato.gestor;

public class BonificacionPortero implements BonificacionStrategy {
    public double calcular(Jugador jugador) {
        return 80.0;
    }
}
```

### BonificacionDefensa.java

```java
package com.campeonato.gestor;

public class BonificacionDefensa implements BonificacionStrategy {
    public double calcular(Jugador jugador) {
        return 70.0;
    }
}
```

### BonificacionBase.java

```java
package com.campeonato.gestor;

public class BonificacionBase implements BonificacionStrategy {
    public double calcular(Jugador jugador) {
        return 50.0;
    }
}
```

### BonificacionFactory.java

```java
package com.campeonato.gestor;

public class BonificacionFactory {
    public static BonificacionStrategy obtenerEstrategia(String posicion) {
        switch (posicion) {
            case "Delantero": return new BonificacionDelantero();
            case "Portero": return new BonificacionPortero();
            case "Defensa": return new BonificacionDefensa();
            default: return new BonificacionBase();
        }
    }
}
```

## Reportes

### Reporte.java

```java
package com.campeonato.gestor;

import java.util.List;

public interface Reporte {
    void generar(List<Equipo> equipos, List<Arbitro> arbitros);
}
```

### ReporteTexto.java

```java
package com.campeonato.gestor;

import java.util.List;

public class ReporteTexto implements Reporte {
    public void generar(List<Equipo> equipos, List<Arbitro> arbitros) {
        System.out.println("Reporte en TEXTO:");
        for (Equipo e : equipos) {
            System.out.println("Equipo: " + e.getNombre());
            for (Jugador j : e.getJugadores()) {
                System.out.println("- " + j.getNombre() + " (" + j.getPosicion() + ") Bonificación: " + j.getBonificacion());
            }
        }
        for (Arbitro a : arbitros) {
            System.out.println("Arbitro: " + a.getNombre());
        }
    }
}
```

### ReporteHtml.java

```java
package com.campeonato.gestor;

import java.util.List;

public class ReporteHtml implements Reporte {
    public void generar(List<Equipo> equipos, List<Arbitro> arbitros) {
        System.out.println("<html><body>");
        System.out.println("<h1>Reporte en HTML</h1>");
        for (Equipo e : equipos) {
            System.out.println("<h2>Equipo: " + e.getNombre() + "</h2>");
            for (Jugador j : e.getJugadores()) {
                System.out.println("<p>" + j.getNombre() + " - " + j.getPosicion() + " Bonificación: " + j.getBonificacion() + "</p>");
            }
        }
        for (Arbitro a : arbitros) {
            System.out.println("<p>Arbitro: " + a.getNombre() + "</p>");
        }
        System.out.println("</body></html>");
    }
}
```

---

# ✅ Conclusión

- Se aplicaron los **5 principios SOLID** correctamente.  
- El diseño ahora es **modular, extensible y mantenible**.  






