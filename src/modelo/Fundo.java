package modelo;

//Clase que representa un fundo agrícola
public class Fundo {
 private final String nombre;
 private String ubicacion;

 // Constructor que inicializa los atributos
 public Fundo(String nombre, String ubicacion) {
     this.nombre = nombre;
     this.ubicacion = ubicacion;
 }

 // Retorna el nombre del fundo
 public String getNombre() { return nombre; }

 // Retorna la capacidad diaria
 public String getUbicacion() { return ubicacion; }

 
 // Representación en texto del objeto (solo el nombre)
 @Override
 public String toString() { return nombre; }
}
