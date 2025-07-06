package logica;

import modelo.*;
import repositorio.*;
import excepcion.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class GestorSolicitudes {

    private final RepositorioSolicitudesAlmuerzo repo;
    private final int capacidadMaxPorFundo = 50;

    public GestorSolicitudes(RepositorioSolicitudesAlmuerzo r){ this.repo = r; }

    /* -------- Sobrecarga -------- */
    public void registrar(Persona p, Menus m, Fundo f) throws ExcepcionCapacidadExcedida {
        registrar(p,m,f, LocalDate.now());              // Delega al método más completo
    }
    public void registrar(Persona p, Menus m, Fundo f, LocalDate fecha) throws ExcepcionCapacidadExcedida {
        int ocupadas = (int) repo.todos().stream()
                .filter(s -> s.getFecha().equals(fecha) && s.getFundo().equals(f)).count();
        if(ocupadas >= capacidadMaxPorFundo)
            throw new ExcepcionCapacidadExcedida("Capacidad diaria llena para el fundo");
        
        repo.agregar(new SolicitudAlmuerzo(p,f,m,fecha));
       
    }

    /* -------- Agrupación con HashMap -------- */
    public Map<String,Integer> resumenPorFundo(LocalDate fecha){
        Map<String,Integer> mapa = new HashMap<>();     // clave=fundo, valor=cantidad
        for(SolicitudAlmuerzo s: repo.todos()){
            if(s.getFecha().equals(fecha))
                mapa.merge(s.getFundo().getNombre(), 1, Integer::sum);    // O(1) promedio
        }
        return mapa;
    }
}
