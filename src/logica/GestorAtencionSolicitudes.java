package logica;

import modelo.SolicitudAlmuerzo;
import excepcion.ExcepcionColaVacia;

/**
 * Gestiona la atención de solicitudes de almuerzo.
 */
public class GestorAtencionSolicitudes {

    private final ColaAtencion<SolicitudAlmuerzo> cola = new ColaAtencion<>();

    /**
     * Encola una solicitud.
     */
    public void encolar(SolicitudAlmuerzo solicitud) {
        cola.encolar(solicitud);
    }

    /**
     * Atiende la siguiente solicitud en la cola.
     */
    public SolicitudAlmuerzo atender() throws ExcepcionColaVacia {
        return cola.atender();
    }

    /**
     * Devuelve el número de solicitudes pendientes en la cola.
     */
    public int pendientes() {
        return cola.tamanio();
    }
}
