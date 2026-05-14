package componentes;

import interfaz.CloneAnd;
import interfaz.ComponenteRenderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import interfaz.CloneLed;

public class Led extends Componenetes {
    private boolean encendido;
    private Pin pinEntrada;

    public Led(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, numEntradas, renderer);
        this.encendido = false;

        pinEntrada = new Pin(x - 5, y + alto / 2, false); 
        agregarPin(pinEntrada); 
    }

    @Override
   protected void actualizarPines() {
    if (renderer != null) {
        renderer.actualizarPines();
    }
}


    public void setEncendido(boolean estado) {
        this.encendido = estado;
    }

    public boolean isEncendido() {
        return encendido;
    }

    @Override
    protected void evaluar() {
        encendido = pinEntrada.getValor();
    }

    @Override
    public List<Pin> getPinesEntrada() {
    List<Pin> lista = new ArrayList<>();
    lista.add(pinEntrada);
    return lista;
}

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getAncho() {
        return ancho;
    }

    @Override
    public int getAlto() {
        return alto;
    }
    
    public void setPinEntrada(Pin pinEntrada) {
    this.pinEntrada = pinEntrada;
    }


   @Override
    public Componenetes Clone() {
        CloneLed clonador = new CloneLed();
        return clonador.clonar(this);

    }


}
