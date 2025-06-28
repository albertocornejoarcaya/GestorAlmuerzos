package modelo;

public class Persona {

    private final String dni;
    private String nombreCompleto;
    private String area;

    // Constructor que inicializa todos los atributos
    public Persona(String dni, String nombreCompleto, String area) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.area = area;
    }

    // Retorna el DNI
    public String getDni() {
        return dni;
    }

    // Retorna el nombre completo
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    // Modifica el nombre completo
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    // Retorna el área
    public String getArea() {
        return area;
    }

    // Modifica el área
    public void setArea(String area) {
        this.area = area;
    }

    // Representación en texto del objeto Persona
    public String toString() {
        return nombreCompleto + " (" + dni + ")";
    }
}
