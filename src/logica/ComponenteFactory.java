/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

import componentes.AND;
import componentes.Componenetes;
import componentes.Led;
import componentes.NOT;
import componentes.OR;
import componentes.Switch;
import componentes.XOR;
import interfaz.RendererAnd;
import interfaz.RendererLed;
import interfaz.RendererNot;
import interfaz.RendererOr;
import interfaz.RendererSwitch;
import interfaz.RendererXor;

/**
 *
 * @author ESTUDIANTE
 */
public class ComponenteFactory {
    public static Componenetes crearComponente(String tipo, int x, int y, int num) {
        Componenetes nuevo;

        switch (tipo.toUpperCase()) {
            case "AND" -> {
                nuevo = new AND(x, y, 50, 40, num, null);
                nuevo.setRenderer(new RendererAnd((AND) nuevo));
            }
            case "OR" -> {
                nuevo = new OR(x, y, 50, 40, num, null);
                nuevo.setRenderer(new RendererOr((OR) nuevo));
            }
            case "NOT" -> {
                nuevo = new NOT(x, y, 50, 40, num, null);
                nuevo.setRenderer(new RendererNot((NOT) nuevo));
            }
            case "XOR" -> {
                nuevo = new XOR(x, y, 50, 40, num, null);
                nuevo.setRenderer(new RendererXor((XOR) nuevo));
            }
            case "SWITCH" -> {
                nuevo = new Switch(x, y, 30, 15, num, null);
                nuevo.setRenderer(new RendererSwitch((Switch) nuevo));
            }
            case "LED" -> {
                nuevo = new Led(x, y, 11, 11, num, null);
                nuevo.setRenderer(new RendererLed((Led) nuevo));
            }
            default -> throw new IllegalArgumentException("Tipo de componente desconocido: " + tipo);
        }

        return nuevo;
    }
    
}
