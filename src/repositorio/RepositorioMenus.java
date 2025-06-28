package repositorio;
import modelo.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioMenus {
    private final List<Menu> menus = new ArrayList<>();

    public void agregar(Menu menu) { menus.add(menu); }
    public List<Menu> obtenerTodos() { return new ArrayList<>(menus); }
    public Optional<Menu> buscarPorId(int id) {
        for (Menu m : menus) {
            if (m.getId() == id) { return Optional.of(m); }
        }
        return Optional.empty();
    }
}
