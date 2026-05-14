package componentes;

import interfaz.CloneAnd;
import interfaz.ComponenteRenderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import interfaz.CloneXor;


public class XOR extends Compuerta {
    private ArrayList<Pin> entradas;
    private Pin pinSalida;

    public XOR(int x, int y, int ancho, int alto, int numEntradas, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, Math.max(2, Math.min(4, numEntradas)),renderer); // Asegura 2 a 4 entradas
        
        entradas = new ArrayList<>();

        for (int i = 0; i < this.numEntradas; i++) {
            int posY = y + (i + 1) * alto / (this.numEntradas + 1);
            Pin entrada = new Pin(x - 4, posY, false);
            entradas.add(entrada);
            agregarPin(entrada);
        }

        pinSalida = new Pin(x + ancho + 15, y + alto / 2, true);
        agregarPin(pinSalida);
    }

    @Override
    public void actualizarPines() {
        renderer.actualizarPines();
    }

    @Override
    public void evaluar() {
        int contadorTrue = 0;
        for (Pin entrada : entradas) {
            if (entrada.getValor()) {
                contadorTrue++;
            }
        }
        pinSalida.setValor(contadorTrue % 2 == 1); // Impar = true
    }

    @Override
    public List<Pin> getPinesEntrada() {
        return entradas;
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
    
    public void setPinesEntrada(List<Pin> entradas) {
    this.entradas = new ArrayList<>(entradas);
    }   

    public void setPinSalida(Pin pinSalida) {
        this.pinSalida = pinSalida;
    }

    @Override
   public Componenetes Clone() {
       CloneXor clonador = new CloneXor();
       return clonador.clonar(this);
   }

    
    
}
