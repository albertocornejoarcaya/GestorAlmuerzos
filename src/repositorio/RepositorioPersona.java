package repositorio;
import modelo.Persona;
import java.util.*;

public class RepositorioPersona {
    private final List<Persona> personas = new ArrayList<>();

    public void agregar(Persona persona) { personas.add(persona); }
    public List<Persona> obtenerTodas() { return new ArrayList<>(personas); }
    public Optional<Persona> buscarPorDni(String dni) {
        for (Persona p : personas) {
            if (p.getDni().equals(dni)) { return Optional.of(p); }
        }
        return Optional.empty();
    }
    public void eliminar(Persona persona) { personas.remove(persona); }
}