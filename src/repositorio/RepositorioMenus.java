package repositorio;

import modelo.Menus;
import modelo.TipoMenu;
import java.util.*;

public class RepositorioMenus {
    private final List<Menus> menus = new ArrayList<>();

    public void agregar(Menus menu) {
        menus.add(menu);
    }

    // Sobrecarga: agregar menú por campos sueltos
    public void agregar(String id, String descripcion, TipoMenu tipo, double precio) {
        menus.add(new Menus(id, descripcion, tipo, precio));
    }

    public Optional<Menus> buscarPorId(String id) {
        return menus.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public List<Menus> obtenerTodos() {
        return new ArrayList<>(menus);
    }

    public void eliminar(Menus menu) {
        menus.remove(menu);
    }

    // Sobrecarga: eliminar por ID
    public void eliminar(String id) {
        menus.removeIf(m -> m.getId().equals(id));
    }
    
    public List<Menus> todos() {
        return new ArrayList<>(menus);
    }
}
