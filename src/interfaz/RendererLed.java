/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import componentes.Led;
import componentes.Pin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ESTUDIANTE
 */
public class RendererLed implements ComponenteRenderer{
    private Led componente;

    public RendererLed(Led componente) {
        this.componente = componente;
    }
    

    @Override
    public void actualizarPines() {
         int x = componente.getX();
        int y = componente.getY();
        int alto = componente.getAlto();

        Pin entrada = componente.getPinesEntrada().get(0); // Led tiene solo una entrada
        entrada.x = x - 5;
        entrada.y = y + alto / 2;
    }

    @Override
    public void dibujar(Graphics g) {
        actualizarPines();
        Graphics2D g2 = (Graphics2D) g;

        int x = componente.getX();
        int y = componente.getY();
        int ancho = componente.getAncho();
        int alto = componente.getAlto();

        Pin entrada = componente.getPinesEntrada().get(0);

        // LED como óvalo
        g2.setColor(componente.isEncendido() ? Color.YELLOW : Color.DARK_GRAY);
        g2.fillOval(x, y, ancho, alto);

        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, ancho, alto);

        // Línea desde el pin a la entrada del LED
        g2.drawLine(entrada.x, entrada.y, x, y + alto / 2);

        entrada.dibujar(g2);
    }
    
}
