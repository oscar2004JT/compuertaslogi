package componentes;

import interfaz.CloneXor;
import interfaz.ComponenteRenderer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import interfaz.CloneSwitch;

public class Switch extends Componenetes {
    private boolean activado;
    private Pin pinSalida;

    public Switch(int x, int y, int ancho, int alto, int numeroSalida, ComponenteRenderer renderer) {
        super(x, y, ancho, alto, numeroSalida, renderer);
        this.activado = false;

        pinSalida = new Pin(x + (ancho / 2) + 5, y + alto / 2, true);
        agregarPin(pinSalida); 
    }

    @Override
    protected void actualizarPines() {
        renderer.actualizarPines();
    }
    
    public void setActivado(boolean estado) {
        this.activado = estado;
    }

    public boolean getActivado() {
        return activado;
    }

    @Override
    protected void evaluar() {
        pinSalida.setValor(activado);
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
       CloneSwitch clonador = new CloneSwitch();
       return clonador.clonar(this);

    }

    @Override
    public List<Pin> getPinesEntrada() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
}
