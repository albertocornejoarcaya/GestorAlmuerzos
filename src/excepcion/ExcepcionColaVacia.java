package excepcion;

// Clase personalizada que extiende de Exception para manejar colas vacías
public class ExcepcionColaVacia extends Exception {

    // Constructor que recibe un mensaje y lo pasa al constructor de Exception
    public ExcepcionColaVacia(String m) {
        super(m);
    }
}
