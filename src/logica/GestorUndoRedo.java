package logica;

import excepcion.ExcepcionPilaVacia;
import java.util.Deque;
import java.util.LinkedList;

public class GestorUndoRedo {
    private final Deque<Accion> pilaUndo = new LinkedList<>();
    private final Deque<Accion> pilaRedo = new LinkedList<>();

    private static class Accion {
        final Runnable hacer;
        final Runnable deshacer;

        Accion(Runnable hacer, Runnable deshacer) {
            this.hacer = hacer;
            this.deshacer = deshacer;
        }
    }

    // Método principal para ejecutar y registrar acciones
    public void ejecutar(Runnable hacer, Runnable deshacer) {
        hacer.run();
        pilaUndo.push(new Accion(hacer, deshacer));
        pilaRedo.clear();
    }

    // Método para solo registrar acciones sin ejecutarlas
    public void guardarAccion(Runnable hacer, Runnable deshacer) {
        pilaUndo.push(new Accion(hacer, deshacer));
        pilaRedo.clear();
    }

    public void deshacer() throws ExcepcionPilaVacia {
        if (pilaUndo.isEmpty()) throw new ExcepcionPilaVacia("Nada que deshacer");
        Accion accion = pilaUndo.pop();
        accion.deshacer.run();
        pilaRedo.push(accion);
    }

    public void rehacer() throws ExcepcionPilaVacia {
        if (pilaRedo.isEmpty()) throw new ExcepcionPilaVacia("Nada que rehacer");
        Accion accion = pilaRedo.pop();
        accion.hacer.run();
        pilaUndo.push(accion);
    }

    public boolean sePuedeDeshacer() {
        return !pilaUndo.isEmpty();
    }

    public boolean sePuedeRehacer() {
        return !pilaRedo.isEmpty();
    }
}
