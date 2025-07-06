package modelo;

public class Persona extends Entidad {
    private String area;

    public Persona(String dni, String nombreCompleto, String area) {
        super(dni, nombreCompleto);
        this.area = area;
    }

    public String getDni() { return id; } // Alias para mayor claridad
    public String getNombreCompleto() { return nombre; } // Alias

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }


    public String toString() {
        return nombre + " (" + id + ")";
    }
}
