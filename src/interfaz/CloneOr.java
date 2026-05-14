package interfaz;

import componentes.Componenetes;
import componentes.OR;
import componentes.Pin;
import java.util.ArrayList;
import java.util.List;

public class CloneOr implements Cloneable {

    @Override
    public Componenetes clonar(Componenetes original) {
        OR originalOr = (OR) original;

        // Crear copia con los mismos parámetros básicos
        OR copia = new OR(
            originalOr.getX(),
            originalOr.getY(),
            originalOr.getAncho(),
            originalOr.getAlto(),
            originalOr.getPinesEntrada().size(),
            null // Renderer se asigna después
        );

        // Clonar los pines de entrada
        List<Pin> entradasClonadas = new ArrayList<>();
        for (Pin entrada : originalOr.getPinesEntrada()) {
            entradasClonadas.add(entrada.clone());
        }
        copia.setPinesEntrada(entradasClonadas);

        // Clonar pin de salida
        copia.setPinSalida(originalOr.getPinSalida().clone());

        // Reconstruir lista de pines
        copia.limpiarPines();
        for (Pin entrada : entradasClonadas) {
            copia.agregarPin(entrada);
        }
        copia.agregarPin(copia.getPinSalida());

        // Asignar renderer
        copia.setRenderer(new RendererOr(copia));

        return copia;
    }
}
