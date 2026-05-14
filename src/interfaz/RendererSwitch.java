/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import componentes.Pin;
import componentes.Switch;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author ESTUDIANTE
 */
public class RendererSwitch implements ComponenteRenderer{
    private Switch componente;

    public RendererSwitch(Switch componente) {
        this.componente = componente;
    }
    
    @Override
    public void actualizarPines() {
        int x = componente.getX();
        int y = componente.getY();
        int ancho = componente.getAncho();
        int alto = componente.getAlto();

        Pin salida = componente.getPinSalida();
        salida.x = x + (ancho / 2) + 5;
        salida.y = y + alto / 2;
    }

    @Override
    public void dibujar(Graphics g) {
        actualizarPines();
        Graphics2D g2 = (Graphics2D) g;

        int x = componente.getX();
        int y = componente.getY();
        int ancho = componente.getAncho();
        int alto = componente.getAlto();
        boolean activado = componente.getActivado();

        int anchoRectangulo = ancho / 2;

        // Rectángulo principal
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(x, y, anchoRectangulo, alto);
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, anchoRectangulo, alto);

        // Botón circular
        int diametroBoton = alto - 4;
        int posicionXBoton = x + (anchoRectangulo - diametroBoton) / 2;
        int posicionYBoton = y + 2;

        g2.setColor(activado ? new Color(144, 238, 144) : new Color(0, 100, 0)); // Verde claro u oscuro
        g2.fillOval(posicionXBoton, posicionYBoton, diametroBoton, diametroBoton);
        g2.setColor(Color.BLACK);
        g2.drawOval(posicionXBoton, posicionYBoton, diametroBoton, diametroBoton);

        // Línea hacia pin de salida
        Pin salida = componente.getPinSalida();
        g2.drawLine(x + anchoRectangulo, y + alto / 2, salida.x, salida.y);
        salida.dibujar(g2);
    }

}
