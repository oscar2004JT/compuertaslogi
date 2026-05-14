/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import java.awt.Graphics;
import java.io.Serializable;

/**
 *
 * @author ESTUDIANTE
 */
public interface ComponenteRenderer extends Serializable {
    public void actualizarPines();
    public void dibujar(Graphics g);
    
    
}
