package componentes;

import interfaz.ComponenteRenderer;
import java.awt.Graphics;

public abstract class Compuerta extends Componenetes implements Cloneable{
    
    public Compuerta(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, numEntradas, renderer);
    }
    
    @Override
    public abstract Componenetes Clone();

}
