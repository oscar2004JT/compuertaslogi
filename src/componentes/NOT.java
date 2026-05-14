package componentes;

import interfaz.CloneAnd;
import interfaz.ComponenteRenderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import interfaz.CloneNot;

public class NOT extends Compuerta {

    private Pin pinEntrada;
    private Pin pinSalida;

    public NOT(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, 1, renderer);

        pinEntrada = new Pin(x - 15, y + alto / 2, false);
        pinSalida = new Pin(x + ancho + 15, y + alto / 2, true);

        agregarPin(pinEntrada);
        agregarPin(pinSalida);
    }

    @Override
    protected void actualizarPines() {
    if (renderer != null) {
        renderer.actualizarPines();
    }
}

    @Override
    public void evaluar() {
        pinSalida.setValor(!pinEntrada.getValor());
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

    @Override
    public Pin getPinSalida() {
        return pinSalida;
    }
    
    public Pin getPinEntrada() {
    return this.pinEntrada;
    }

    public void setPinEntrada(Pin pin) {
        this.pinEntrada = pin;
    }

    public void setPinSalida(Pin pin) {
        this.pinSalida = pin;
    }
    

   @Override
    public Componenetes Clone() {
        CloneNot  clonador = new CloneNot();
        return clonador.clonar(this);
    }
    
}
