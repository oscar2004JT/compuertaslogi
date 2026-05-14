package interfaz;

import componentes.Componenetes;
import componentes.Led;
import componentes.Pin;

public class CloneLed implements Cloneable {

    @Override
    public Componenetes clonar(Componenetes original) {
        Led originalLed = (Led) original;

        // Crear nueva instancia de Led con parámetros básicos
        Led copia = new Led(
            originalLed.getX(),
            originalLed.getY(),
            originalLed.getAncho(),
            originalLed.getAlto(),
            1, // siempre una entrada
            null // el renderer se asigna después
        );

        // Clonar el pin de entrada
        copia.setPinEntrada(originalLed.getPinesEntrada().get(0).clone());

        // Limpiar y volver a agregar los pines
        copia.limpiarPines();
        copia.agregarPin(copia.getPinesEntrada().get(0));

        // Clonar estado del LED
        copia.setEncendido(originalLed.isEncendido());

        // Asignar renderer
        copia.setRenderer(new RendererLed(copia));

        return copia;
    }
}
