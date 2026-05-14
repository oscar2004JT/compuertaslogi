/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import componentes.NOT;
import componentes.Pin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author ESTUDIANTE
 */
public class RendererNot implements ComponenteRenderer {
    private NOT compuerta;

    public RendererNot(NOT compuerta) {
        this.compuerta = compuerta;
    }

    @Override
    public void actualizarPines() {
        int x = compuerta.getX();
        int y = compuerta.getY();
        int alto = compuerta.getAlto();
        int ancho = compuerta.getAncho();

        List<Pin> entradas = compuerta.getPinesEntrada();
        if (entradas == null || entradas.isEmpty()) {
            System.err.println("RendererNot: no hay pines de entrada");
            return;
        }

        Pin pinEntrada = entradas.get(0);
        pinEntrada.x = x - 15;
        pinEntrada.y = y + alto / 2;

        Pin pinSalida = compuerta.getPinSalida();
        if (pinSalida == null) {
            System.err.println("RendererNot: el pin de salida es null");
            return;
        }

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

        Pin pinEntrada = compuerta.getPinesEntrada().get(0);
        Pin pinSalida = compuerta.getPinSalida();

        // Dibuja triángulo de compuerta NOT
        g2.drawLine(x, y, x + ancho, y + alto / 2);
        g2.drawLine(x, y + alto, x + ancho, y + alto / 2);
        g2.drawLine(x, y, x, y + alto);

        // Línea de entrada
        g2.drawLine(pinEntrada.x, pinEntrada.y, x, y + alto / 2);
        pinEntrada.dibujar(g2);

        // Línea de salida
        g2.drawLine(x + ancho, y + alto / 2, pinSalida.x, pinSalida.y);
        pinSalida.dibujar(g2);
    }
}

