package componentes;

import java.io.Serializable;
import java.util.ArrayList;
import logica.Cable;

/**
 * Circuito maneja la evaluación de componentes y la propagación de señales por cables.
 */
public class Circuito implements Serializable {
    private ArrayList<Componenetes> componentes = new ArrayList<>();
    private ArrayList<Cable> cables = new ArrayList<>();

    public void agregarComponente(Componenetes c) {
        componentes.add(c);
    }

    public void eliminarComponente(Componenetes c) {
        componentes.remove(c);
    }

    public void agregarCable(Cable c) {
        cables.add(c);
    }

    public void eliminarCable(Cable c) {
        cables.remove(c);
    }

    public ArrayList<Componenetes> getComponentes() {
        return componentes;
    }

    public ArrayList<Cable> getCables() {
        return cables;
    }

    /**
     * Evaluar todo el circuito: componentes y propagación por cables.
     * Debe llamarse periódicamente (p.ej. desde un Timer).
     */
    public void evaluar() {
        // 1) Cada componente actualiza su pin de salida
        for (Componenetes comp : componentes) {
            comp.evaluar();
        }
        // 2) Propagar valor de pinInicio a cada cable
        for (Cable c : cables) {
            // ahora cable.actualizarValor recibe boolean
            c.actualizarValor(c.pinInicio.getValor());
        }
        // 3) Asignar valor del cable al pinFin
        for (Cable c : cables) {
            if (c.pinFin != null) {
                // getValor() ya devuelve boolean
                c.pinFin.setValor(c.getValor());
            }
        }
    }
}
