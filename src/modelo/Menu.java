package modelo;

//Clase que representa un menú de almuerzo ofrecido
public class Menu {
 private final int id;

 private String descripcion;
 private TipoMenu tipo;
 private double precio;
 private boolean activo;

 // Constructor que inicializa el menú como activo por defecto
 public Menu(int id, String descripcion, TipoMenu tipo, double precio) {
     this.id = id;
     this.descripcion = descripcion;
     this.tipo = tipo;
     this.precio = precio;
     this.activo = true;
 }

 // Retorna el ID del menú
 public int getId() { return id; }

 // Retorna la descripción del menú
 public String getDescripcion() { return descripcion; }

 // Modifica la descripción
 public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

 // Retorna el tipo de menú
 public TipoMenu getTipo() { return tipo; }

 // Modifica el tipo de menú
 public void setTipo(TipoMenu tipo) { this.tipo = tipo; }

 // Retorna el precio del menú
 public double getPrecio() { return precio; }

 // Modifica el precio
 public void setPrecio(double precio) { this.precio = precio; }

 // Retorna si el menú está activo
 public boolean isActivo() { return activo; }

 // Método para desactivar el menú
 public void desactivar() { this.activo = false; }

 // Representación en texto del objeto (ej. #1 Arroz con Pollo (ECONOMICO))
 @Override
 public String toString() {
     return "#" + id + " " + descripcion + " (" + tipo + ")";
 }
}
