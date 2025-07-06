package repositorio;

import modelo.SolicitudAlmuerzo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio para gestionar las solicitudes de almuerzo.
 */
public class RepositorioSolicitudesAlmuerzo {
    
    private final List<SolicitudAlmuerzo> lista = new ArrayList<>();

    /**
     * Agrega una nueva solicitud de almuerzo a la lista.
     * 
     * @param solicitud La solicitud a agregar.
     */
    public void agregar(SolicitudAlmuerzo solicitud) {
        lista.add(solicitud);
    }

    /**
     * Elimina una solicitud de almuerzo de la lista.
     * 
     * @param solicitud La solicitud a eliminar.
     */
    public void eliminar(SolicitudAlmuerzo solicitud) {
        lista.remove(solicitud);
    }

    /**
     * Devuelve una copia de todas las solicitudes registradas.
     * 
     * @return Lista de todas las solicitudes.
     */
    public List<SolicitudAlmuerzo> todos() {
        return new ArrayList<>(lista);
    }

    /**
     * Devuelve una lista de solicitudes filtradas por fecha y nombre del fundo.
     */
    public List<SolicitudAlmuerzo> porFechaFundo(LocalDate fecha, String fundo) {
        List<SolicitudAlmuerzo> resultado = new ArrayList<>();
        for (SolicitudAlmuerzo solicitud : lista) {
            if (solicitud.getFecha().equals(fecha) &&
                solicitud.getFundo().getNombre().equalsIgnoreCase(fundo)) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }
}
