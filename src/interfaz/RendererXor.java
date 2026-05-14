/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import componentes.Pin;
import componentes.XOR;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ESTUDIANTE
 */
public class RendererXor implements ComponenteRenderer{
    private XOR compuerta;
    
      public RendererXor(XOR compuerta) {
        this.compuerta = compuerta;
    }

    @Override
    public void actualizarPines() {
        int x = compuerta.getX();
        int y = compuerta.getY();
        int alto = compuerta.getAlto();
        int ancho = compuerta.getAncho();

        // Actualiza las entradas
        for (int i = 0; i < compuerta.getPinesEntrada().size(); i++) {
            Pin entrada = compuerta.getPinesEntrada().get(i);
            entrada.x = x - 4;
            entrada.y = y + (i + 1) * alto / (compuerta.getPinesEntrada().size() + 1);
        }

        // Actualiza salida
        Pin salida = compuerta.getPinSalida();
        salida.x = x + ancho + 15;
        salida.y = y + alto / 2;
    }

    @Override
    public void dibujar(Graphics g) {
        actualizarPines();  // Asegúrate de actualizar antes de dibujar
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        int x = compuerta.getX();
        int y = compuerta.getY();
        int ancho = compuerta.getAncho();
        int alto = compuerta.getAlto();

        // Cuerpo de la compuerta XOR
        g2.drawArc(x - (ancho / 4) - 5, y, ancho / 2, alto, 270, 180); // línea extra de XOR
        g2.drawArc(x, y, ancho, alto, 275, 183);
        g2.drawArc(x - (ancho / 4), y, ancho / 2, alto, 270, 180);

        g2.drawLine(x, y, x + 21, y);
        g2.drawLine(x, y + alto, x + 27, y + alto);

        // Entradas
        for (Pin entrada : compuerta.getPinesEntrada()) {
            g2.drawLine(x + 10, entrada.y, entrada.x, entrada.y);
            entrada.dibujar(g2);
        }

        // Salida
        Pin salida = compuerta.getPinSalida();
        g2.drawLine(x + ancho, y + alto / 2, salida.x, salida.y);
        salida.dibujar(g2);
    }
        
}
    

