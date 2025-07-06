package modelo;


import java.time.LocalDate;
public class SolicitudAlmuerzo {
    private final Persona persona;
    private Fundo fundo;
    private Menus menu;
    private LocalDate fecha;
    private final LocalDate fechaCreacion;

    public SolicitudAlmuerzo(Persona persona, Fundo fundo, Menus menu, LocalDate fecha) {
        this.persona = persona;
        this.fundo = fundo;
        this.menu = menu;
        this.fecha = fecha;
        this.fechaCreacion = LocalDate.now();
    }

    public Persona getPersona() { return persona; }
    public Fundo getFundo() { return fundo; }
    public void setFundo(Fundo fundo) { this.fundo = fundo; }
    public Menus getMenu() { return menu; }
    public void setMenu(Menus menu) { this.menu = menu; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public String toString() {
        return fecha + " – " + persona + " – " + menu + " – " + fundo;
    }
}