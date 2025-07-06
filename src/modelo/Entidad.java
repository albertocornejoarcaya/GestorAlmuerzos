package modelo;

public abstract class Entidad {
    protected String id;
    protected String nombre;

    public Entidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return nombre + " (" + id + ")";
    }
}
