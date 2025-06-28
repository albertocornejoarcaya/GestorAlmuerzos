package repositorio;
import modelo.SolicitudAlmuerzo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioSolicitudesAlmuerzo {
    private final List<SolicitudAlmuerzo> solicitudes = new ArrayList<>();

    public void agregar(SolicitudAlmuerzo solicitud) { solicitudes.add(solicitud); }
    public void eliminar(SolicitudAlmuerzo solicitud) { solicitudes.remove(solicitud); }
    public List<SolicitudAlmuerzo> obtenerTodas() { return new ArrayList<>(solicitudes); }

    public List<SolicitudAlmuerzo> buscarPorFechaYFundo(LocalDate fecha, String nombreFundo) {
        List<SolicitudAlmuerzo> resultado = new ArrayList<>();
        for (SolicitudAlmuerzo s : solicitudes) {
            if (s.getFecha().equals(fecha) && s.getFundo().getNombre().equalsIgnoreCase(nombreFundo)) {
                resultado.add(s);
            }
        }
        return resultado;
    }
}