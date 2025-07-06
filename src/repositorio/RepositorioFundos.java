package repositorio;

import java.util.*;
import modelo.Fundo;

public class RepositorioFundos {
    private final List<Fundo> lista = new ArrayList<>();

    public void agregar(Fundo f) {
        lista.add(new Fundo(f.getID(), f.getNombre(), f.getUbicacion()));
    }

    public List<Fundo> todos() {
        return new ArrayList<>(lista);
    }

    public Optional<Fundo> buscarPorNombre(String n) {
        return lista.stream()
                .filter(f -> f.getNombre().equalsIgnoreCase(n))
                .findFirst();
    }

    //Agregar este método
    public void eliminar(Fundo f) {
        lista.remove(f);
    }
}
