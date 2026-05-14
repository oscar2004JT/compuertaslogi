package interfaz;

import componentes.Componenetes;
import componentes.XOR;
import componentes.Pin;
import java.util.ArrayList;
import java.util.List;

public class CloneXor implements Cloneable {

    @Override
    public Componenetes clonar(Componenetes original) {
        XOR originalXor = (XOR) original;

        // Crear nueva instancia con mismos parámetros básicos
        XOR copia = new XOR(
            originalXor.getX(),
            originalXor.getY(),
            originalXor.getAncho(),
            originalXor.getAlto(),
            originalXor.getPinesEntrada().size(),
            null // renderer se setea después
        );

        // Clonar entradas
        List<Pin> entradasClonadas = new ArrayList<>();
        for (Pin entrada : originalXor.getPinesEntrada()) {
            entradasClonadas.add(entrada.clone());
        }
        copia.setPinesEntrada(entradasClonadas);

        // Clonar salida
        copia.setPinSalida(originalXor.getPinSalida().clone());

        // Reconstruir lista de pines
        copia.limpiarPines();
        for (Pin entrada : entradasClonadas) {
            copia.agregarPin(entrada);
        }
        copia.agregarPin(copia.getPinSalida());

        // Asignar renderer a la nueva copia
        copia.setRenderer(new RendererXor(copia));

        return copia;
    }
}
