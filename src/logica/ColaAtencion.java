package logica;

import excepcion.ExcepcionColaVacia;
import java.util.*;

public class ColaAtencion<T> {
    private final LinkedList<T> elementos = new LinkedList<>();

    public void encolar(T elemento) {
        elementos.addLast(elemento);
    }

    // Sobrecarga: encolar una lista de elementos
    public void encolar(List<T> lista) {
        elementos.addAll(lista);
    }

    public T atender() throws ExcepcionColaVacia {
        if (estaVacia()) {
            throw new ExcepcionColaVacia("No hay elementos para atender.");
        }
        return elementos.removeFirst();
    }

    public boolean estaVacia() {
        return elementos.isEmpty();
    }

    public int tamanio() {
        return elementos.size();
    }

    public List<T> listar() {
        return new ArrayList<>(elementos);
    }
}
