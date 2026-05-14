package logica;

import componentes.Pin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.io.Serializable;

public class Cable implements Serializable {
    private int x1, y1, x2, y2;
    public Pin pinInicio;
    public Pin pinFin;
    
    private boolean valor;

    public Cable(Pin pinInicio) {
        this.pinInicio = pinInicio;
        this.x1 = pinInicio.x;
        this.y1 = pinInicio.y;
        this.x2 = pinInicio.x;
        this.y2 = pinInicio.y;
        this.valor = false;
    }

    public void actualizarValor(boolean nuevoValor) {
        this.valor = nuevoValor;
    }

    public void actualizarFinal(int x, int y) {
        if (pinFin == null) {
            this.x2 = x;
            this.y2 = y;
        }
    }

    public void conectar(Pin pinFin) {
        this.pinFin = pinFin;
        this.x2 = pinFin.x;
        this.y2 = pinFin.y;
    }

    public void dibujar(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        // Cambia el color dependiendo del valor boolean
        if (valor) {
            g2.setColor(Color.GREEN); // Verde claro si hay un 1
        } else {
            g2.setColor(new Color(0, 100, 0)); // Verde oscuro si no hay señal
        }

        // Establece el grosor de la línea (más grueso)
        g2.setStroke(new BasicStroke(3)); // Grosor de 3 píxeles
        
        int startX = (pinInicio != null) ? pinInicio.x : x1;
        int startY = (pinInicio != null) ? pinInicio.y : y1;
        int endX = (pinFin != null) ? pinFin.x : x2;
        int endY = (pinFin != null) ? pinFin.y : y2;
        g2.drawLine(startX, startY, endX, endY);
    }

    public boolean estaCerca(int x, int y) {
        int startX = (pinInicio != null) ? pinInicio.x : x1;
        int startY = (pinInicio != null) ? pinInicio.y : y1;
        int endX = (pinFin != null) ? pinFin.x : x2;
        int endY = (pinFin != null) ? pinFin.y : y2;

        double dx = endX - startX;
        double dy = endY - startY;

        if (dx == 0 && dy == 0) {
            double dist = Math.hypot(x - startX, y - startY);
            return dist <= 5;
        }

        double t = ((x - startX) * dx + (y - startY) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        double projX = startX + t * dx;
        double projY = startY + t * dy;

        double distance = Math.hypot(x - projX, y - projY);

        return distance <= 6;
    }
    
    
    /** Devuelve el valor de señal del cable */
    public boolean getValor() {
        return valor;
    }
    public boolean estaConectado() {
         return pinInicio != null && pinFin != null;
    }

}
