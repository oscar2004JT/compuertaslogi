package interfaz;

import componentes.AND;
import componentes.Componenetes;
import componentes.Pin;
import java.util.ArrayList;
import java.util.List;

public class CloneAnd implements Cloneable{

    @Override
    public Componenetes clonar(Componenetes original) {   // Crear una nueva instancia de AND con parámetros copiados
        AND copia = new AND(
            original.getX(),
            original.getY(),
            original.getAncho(),
            original.getAlto(),
            original.getPinesEntrada().size(),
            null // el renderer se setea después
        );

        // Clonar los pines de entrada
        List<Pin> nuevosPinesEntrada = new ArrayList<Pin>();
        for (Pin pinOriginal : original.getPinesEntrada()) {
            nuevosPinesEntrada.add(pinOriginal.clone());
        }
        copia.getPinesEntrada().clear();
        copia.getPinesEntrada().addAll(nuevosPinesEntrada);

        // Clonar el pin de salida
        copia.setPinSalida(original.getPinSalida().clone());
        

        // Limpiar y volver a agregar los pines
        copia.limpiarPines();
        for (Pin p : nuevosPinesEntrada) {
            copia.agregarPin(p);
        }
        copia.agregarPin(copia.getPinSalida());

        // Asignar renderer
        copia.setRenderer(new RendererAnd(copia));

        return copia;}
}
