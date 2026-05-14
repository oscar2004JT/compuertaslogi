package interfaz;

import componentes.Componenetes;
import componentes.NOT;
import componentes.Pin;

public class CloneNot implements Cloneable {

    @Override
    public Componenetes clonar(Componenetes original) {
        NOT originalNot = (NOT) original;

        // Crear nueva instancia con los mismos parámetros
        NOT copia = new NOT(
            originalNot.getX(),
            originalNot.getY(),
            originalNot.getAncho(),
            originalNot.getAlto(),
            original.getPinesEntrada().size(),
            null // el renderer se setea después
        );

        // Clonar pines
        copia.setPinEntrada(originalNot.getPinEntrada().clone());
        copia.setPinSalida(originalNot.getPinSalida().clone());

        // Limpiar y volver a agregar pines clonados
        copia.limpiarPines();
        copia.agregarPin(copia.getPinEntrada());
        copia.agregarPin(copia.getPinSalida());

        // Asignar renderer nuevo a la copia
        copia.setRenderer(new RendererNot(copia));

        return copia;
    }
}
