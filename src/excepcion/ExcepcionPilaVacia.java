package excepcion;

// Antes extendía Exception (verificada)
public class ExcepcionPilaVacia extends RuntimeException {
    public ExcepcionPilaVacia(String mensaje) {
        super(mensaje);
    }
}
