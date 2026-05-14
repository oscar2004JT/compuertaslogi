/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import componentes.OR;
import componentes.Pin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ESTUDIANTE
 */
public class RendererOr implements ComponenteRenderer {
    private OR compuerta;
    
      public RendererOr(OR compuerta) {
        this.compuerta = compuerta;
    }

    @Override
    public void actualizarPines() {
        int x = compuerta.getX();
        int y = compuerta.getY();
        int alto = compuerta.getAlto();
        int ancho = compuerta.getAncho();

        for (int i = 0; i < compuerta.getPinesEntrada().size(); i++) {
            Pin pin = compuerta.getPinesEntrada().get(i);
            pin.x = x - 4;
            pin.y = y + (i + 1) * alto / (compuerta.getPinesEntrada().size() + 1);
        }

        Pin pinSalida = compuerta.getPinSalida();
        pinSalida.x = x + ancho + 15;
        pinSalida.y = y + alto / 2;
    }

    @Override
    public void dibujar(Graphics g) {
        actualizarPines();
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        int x = compuerta.getX();
        int y = compuerta.getY();
        int ancho = compuerta.getAncho();
        int alto = compuerta.getAlto();

        // Dibuja cuerpo de compuerta OR
        g2.drawArc(x, y, ancho, alto, 275, 183);
        g2.drawArc(x - (ancho / 4), y, ancho / 2, alto, 270, 180);
        g2.drawLine(x, y, x + 21, y);
        g2.drawLine(x, y + alto, x + 27, y + alto);

        // Dibuja entradas
        for (Pin pin : compuerta.getPinesEntrada()) {
            g2.drawLine(x + 10, pin.y, pin.x, pin.y);
            pin.dibujar(g2);
        }

        // Dibuja salida
        Pin pinSalida = compuerta.getPinSalida();
        g2.drawLine(x + ancho, y + alto / 2, pinSalida.x, pinSalida.y);
        pinSalida.dibujar(g2);
    }
    
}
