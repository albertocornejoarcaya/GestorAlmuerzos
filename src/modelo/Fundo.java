package modelo;

/**
 * Clase que representa un Fundo.
 */
public class Fundo extends Entidad {
	 private String ubicacion; 

    // Constructor completo (útil para inicialización rápida)
    public Fundo(String id, String nombre, String ubicacion) {
    	super(id, nombre.toUpperCase()); // usamos el nombre como id y nombre
    	this.ubicacion = ubicacion.toUpperCase();
    }

    // Getters
    public String getID() {
        return id;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public String getNombre() {
        return nombre;
    }
    
    //Setters
    public void setID(String id) {
        this.id = id;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion.toUpperCase();
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    
    // Para imprimir en listas
    @Override
    public String toString() {
        return id + " - " + nombre + " (" + ubicacion + ")";
    }

    // Comparación por ID (para id búsqueda)
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Fundo)) return false;
        Fundo otro = (Fundo) obj;
        return this.id == otro.id;
    }
}
