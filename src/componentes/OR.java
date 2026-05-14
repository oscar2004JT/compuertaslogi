package componentes;

import interfaz.ComponenteRenderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import interfaz.CloneOr;

public class OR extends Compuerta implements Cloneable{
    private ArrayList<Pin> entradas;
    private Pin pinSalida;

    public OR(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, Math.max(2, Math.min(4, numEntradas)),renderer); // Limita a 2-4 entradas

        entradas = new ArrayList<>();

        // Crear los pines de entrada y agregarlos con composición
        for (int i = 0; i < this.numEntradas; i++) {
            int posY = y + (i + 1) * alto / (this.numEntradas + 1);
            Pin entrada = new Pin(x - 4, posY, false);
            entradas.add(entrada);
            agregarPin(entrada);
        }

        // Crear el pin de salida y agregarlo
        pinSalida = new Pin(x + ancho + 15, y + alto / 2, true);
        agregarPin(pinSalida);
    }

    @Override
    public void actualizarPines() {
        renderer.actualizarPines();
    }

    @Override
    public void evaluar() {
        boolean salida = false;
        for (Pin entrada : entradas) {
            if (entrada.getValor()) {
                salida = true;
                break;
            }
        }
        pinSalida.setValor(salida);
    }

    @Override
    public List<Pin> getPinesEntrada() {
        return entradas;
    }
    public void setPinesEntrada(List<Pin> entradas) {
        this.entradas = new ArrayList<>(entradas);
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
     public void setPinSalida(Pin pinSalida) {
     this.pinSalida = pinSalida;
    }

    @Override
    public Componenetes Clone() {
        CloneOr clonador = new CloneOr();
        return clonador.clonar(this);
    }
   
}
