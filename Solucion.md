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

## 2. **OCP** (Open/Closed Principle)

