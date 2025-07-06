package repositorio;

import modelo.Persona;
import java.util.*;

public class RepositorioPersona {
    private final List<Persona> personas = new ArrayList<>();

    // Método 1: agregar una persona
    public void agregar(Persona persona) {
        personas.add(persona);
    }

    // Método 2 (sobrecargado): agregar por datos sueltos
    public void agregar(String dni, String nombre, String area) {
        personas.add(new Persona(dni, nombre, area));
    }

    public List<Persona> obtenerTodas() {
        return new ArrayList<>(personas);
    }

    //Metodo para buscar po DNI
    public Optional<Persona> buscarPorDni(String dni) {
        return personas.stream().filter(p -> p.getDni().equals(dni)).findFirst();
    }

    //Metodo para eliminar una persona
    // Método 3 (sobrecargado): eliminar por DNI directamente
    public void eliminar(String dni)
    {
        personas.removeIf(p -> p.getDni().equals(dni));
    }

    public void eliminar(Persona persona) {
        personas.remove(persona);
    }

}
