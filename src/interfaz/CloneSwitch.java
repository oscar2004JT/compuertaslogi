package interfaz;

import componentes.Componenetes;
import componentes.Switch;
import componentes.Pin;

public class CloneSwitch implements Cloneable {

    @Override
    public Componenetes clonar(Componenetes original) {
        Switch originalSwitch = (Switch) original;

        // Crear nueva instancia con valores básicos
        Switch copia = new Switch(
            originalSwitch.getX(),
            originalSwitch.getY(),
            originalSwitch.getAncho(),
            originalSwitch.getAlto(),
            1, // Siempre 1 salida en un Switch
            null // El renderer se asigna después
        );

        // Clonar el pin de salida
        copia.setPinSalida(originalSwitch.getPinSalida().clone());

        // Conservar estado de activación
        copia.setActivado(originalSwitch.getActivado());

        // Reconstruir los pines
        copia.limpiarPines();
        copia.agregarPin(copia.getPinSalida());

        // Asignar renderer nuevo al clon
        copia.setRenderer(new RendererSwitch(copia));

        return copia;
    }
}
