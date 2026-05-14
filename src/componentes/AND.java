package componentes;

import interfaz.CloneAnd;
import interfaz.ComponenteRenderer;
import interfaz.RendererAnd;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class AND extends Compuerta implements Cloneable {
    private List<Pin> pinesEntrada; // Lista de pines de entrada
    private Pin pinSalida;

    public AND(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, Math.max(2, Math.min(4, numEntradas)),renderer); // Limita a 2-4 entradas
        this.pinesEntrada = new ArrayList<>();
        for (int i = 0; i < numEntradas; i++) {
            int pinY = y + (i + 1) * alto / (numEntradas + 1);
            Pin entrada = new Pin(x - 4, pinY, false);
            pinesEntrada.add(entrada);
            agregarPin(entrada); // Usar método de composición
        }

        pinSalida = new Pin(x + ancho + 15, y + alto / 2, true);
        agregarPin(pinSalida); // También por composición
    }

    @Override
    public void actualizarPines() {
        renderer.actualizarPines();

    }

    @Override
    public void evaluar() {
        boolean salida = true;
        for (Pin entrada : pinesEntrada) {
            if (!entrada.getValor()) {
                salida = false;
                break;
            }
        }
        pinSalida.setValor(salida);
    }
    
    public void setPinSalida(Pin pinSalida) {
    this.pinSalida = pinSalida;
}
    
    @Override
    public List<Pin> getPinesEntrada() {
        return pinesEntrada;
    }

    @Override
    public Pin getPinSalida() {
        return pinSalida;
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
    public Componenetes Clone() {
        CloneAnd clonador = new CloneAnd();
        return clonador.clonar(this);
    }
}
    
    

