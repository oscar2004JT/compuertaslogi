package componentes;

import interfaz.ComponenteRenderer;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Componenetes implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    public int x;
    public int y;
    public int ancho;
    public int alto;
    protected int numEntradas;
    protected ComponenteRenderer renderer;

    private final List<Pin> pins = new ArrayList<>();

    public Componenetes(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.numEntradas = numEntradas;
        this.renderer= renderer;
    }
    
    public void limpiarPines() {
    pins.clear();
}

    public void agregarPin(Pin pin) {
        pins.add(pin);
    }

    protected void eliminarPin(Pin pin) {
        pins.remove(pin);
    }

    public List<Pin> getPins() {
        return Collections.unmodifiableList(pins); // Inmutable desde fuera
    }

    public Pin obtenerPinSiEstáSobre(int mouseX, int mouseY) {
        for (Pin pin : pins) {
            if (pin.estaSobre(mouseX, mouseY)) {
                return pin;
            }
        }
        return null;
    }

    public Pin getPinSalida() {
        for (Pin pin : pins) {
            if (pin.esSalida) {
                return pin;
            }
        }
        return null;
    }
    
    public Pin getPinEntrada(int index) {
        // Filtramos los pines que NO son de salida (entradas)
        List<Pin> pinesEntrada = new ArrayList<>();
        for (Pin pin : pins) {
            if (!pin.esSalida) {
                pinesEntrada.add(pin);
            }
        }
        if (index < 0 || index >= pinesEntrada.size()) return null;
        return pinesEntrada.get(index);
    }

    public List<Pin> getPinsEntrada() {
        List<Pin> pinesEntrada = new ArrayList<>();
        for (Pin pin : pins) {
            if (!pin.esSalida) {
                pinesEntrada.add(pin);
            }
        }
        return Collections.unmodifiableList(pinesEntrada);
    }
    
        public void setRenderer(ComponenteRenderer renderer) {
        this.renderer = renderer;
    }

    public ComponenteRenderer getRenderer() {
        return renderer;
    }
    
    public void dibujar(Graphics g) {
    if (renderer != null) {
        renderer.dibujar(g);
    } else {
        System.err.println("Renderer no asignado para el componente en (" + x + ", " + y + ")");
    }
    }
    ;

    
    public abstract Componenetes Clone();
    public abstract List<Pin> getPinesEntrada();
    public abstract int getX();
    public abstract int getY();
    public abstract int getAncho();
    public abstract int getAlto();
    protected abstract void actualizarPines();
    protected abstract void evaluar();
}
