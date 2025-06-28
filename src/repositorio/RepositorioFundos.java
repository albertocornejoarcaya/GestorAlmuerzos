package repositorio;

import modelo.Fundo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioFundos {
    private final List<Fundo> fundos = new ArrayList<>();

    public void agregar(Fundo fundo) { fundos.add(fundo); }
    public List<Fundo> obtenerTodos() { return new ArrayList<>(fundos); }
    public Optional<Fundo> buscarPorNombre(String nombre) {
        for (Fundo f : fundos) {
            if (f.getNombre().equalsIgnoreCase(nombre)) { return Optional.of(f); }
        }
        return Optional.empty();
    }
}
