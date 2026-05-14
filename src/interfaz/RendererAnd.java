/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import componentes.AND;
import componentes.Pin;
import java.awt.Graphics;
import componentes.Componenetes;
import componentes.Compuerta;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ESTUDIANTE
 */
public class RendererAnd implements ComponenteRenderer {
    private AND compuerta;
    
      public RendererAnd(AND compuerta) {
        this.compuerta = compuerta;
    }
    

    @Override
    public void dibujar(Graphics g) {
    actualizarPines(); // Asegura que los pines estén en la posición correcta

    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.BLACK);

    int x = compuerta.getX();
    int y = compuerta.getY();
    int ancho = compuerta.getAncho();
    int alto = compuerta.getAlto();

    // Dibuja la forma de la compuerta AND
    g2.drawArc(x, y, ancho, alto, 274, 185);
    g2.drawLine(x + 10, y, x + 10, y + alto);
    g2.drawLine(x + 10, y, x + ancho / 2, y);
    g2.drawLine(x + 10, y + alto, (x + ancho / 2) + 1, y + alto);

    // Dibuja los pines de entrada
    for (Pin pin : compuerta.getPinesEntrada()) {
        g2.drawLine(x + 10, pin.getY(), pin.getX(), pin.getY());
        pin.dibujar(g2);
    }

    // Dibuja el pin de salida
    Pin pinSalida = compuerta.getPinSalida();
    g2.drawLine(x + ancho, y + alto / 2, pinSalida.getX(), pinSalida.getY());
    pinSalida.dibujar(g2);
    }

    @Override
    public void actualizarPines() {
        for (int i = 0; i < compuerta.getPinesEntrada().size(); i++) {
            Pin pin = compuerta.getPinesEntrada().get(i);
            pin.x = compuerta.getX() - 4;
            pin.y = compuerta.getY() + (i + 1) * compuerta.getAlto() / (compuerta.getPinesEntrada().size() + 1);
        }

        Pin pinSalida = compuerta.getPinSalida();
        pinSalida.x = compuerta.getX() + compuerta.getAncho() + 15;
        pinSalida.y = compuerta.getY() + compuerta.getAlto() / 2;
    }
}
    
    
    

