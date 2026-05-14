package componentes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Pin implements Serializable, Cloneable {
    public int x;
    public int y;
    public boolean esSalida; // true para pin de salida, false para pin de entrada
    private boolean valor; // estado del pin: false=0, true=1

    public Pin(int x, int y, boolean esSalida) {
        this.x = x;
        this.y = y;
        this.esSalida = esSalida;
        this.valor = false; // Inicialmente 0
    }
    
    public boolean estaSobre(int mouseX, int mouseY) {
        double dx = x - mouseX;
        double dy = y - mouseY;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        return distancia < 6;
    }
    
    public void dibujar(Graphics2D g2) {
        Color fill = valor ? new Color(144, 238, 144) : Color.BLUE;
        g2.setColor(fill);
        g2.fillOval(x - 3, y - 3, 6, 6);
    }

    public void dibujarResaltado(Graphics2D g2) {
        g2.setColor(new Color(0, 128, 0));
        g2.drawOval(x - 6, y - 6, 12, 12);
    }

    // Métodos para manejar el valor del pin
    public boolean getValor() {
        return valor;
    }

    public void setValor(boolean valor) {
        this.valor = valor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    @Override
    public Pin clone() {
        try {
            return (Pin) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    
}
