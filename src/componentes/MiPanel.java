package componentes;

import logica.Cable;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Map;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.ComponenteFactory;
import logica.TablaVerdad;


public class MiPanel extends JPanel {
    private Circuito circuito = new Circuito();
    private Componenetes componenteSeleccionado = null;
    private int desplazamientoX, desplazamientoY;
    private Pin hoveredPin = null;
    private Cable cableEnCurso = null;
    private Componenetes componenteClicDerecho = null;
    private Cable cableClicDerecho = null;

    private final JPopupMenu menuContextual;
    
    public MiPanel() {
        setLayout(null);
        // Crear el menú contextual para eliminar elementos
        menuContextual = new JPopupMenu();
        JMenuItem eliminarItem = new JMenuItem("Eliminar");
        eliminarItem.addActionListener(e -> eliminarSeleccionado());
        menuContextual.add(eliminarItem);
        JMenuItem duplicarItem = new JMenuItem("Duplicar");
        duplicarItem.addActionListener(e -> {
            try {
                duplicarSeleccionado();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(MiPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menuContextual.add(duplicarItem);


        // Mouse listener para clicks y press/drag
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Toggle switch solo en click (no interfiere con drag)
                if (SwingUtilities.isLeftMouseButton(e)) {
                    for (Componenetes comp : circuito.getComponentes()) {
                        if (comp instanceof Switch && dentroComponente(e.getX(), e.getY(), comp)) {
                            Switch sw = (Switch) comp;
                            sw.setActivado(!sw.getActivado());
                            repaint();
                            return;
                        }
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Clic derecho: menú contextual
                if (SwingUtilities.isRightMouseButton(e)) {
                    componenteClicDerecho = null;
                    cableClicDerecho = null;
                    for (Componenetes comp : circuito.getComponentes()) {
                        if (dentroComponente(e.getX(), e.getY(), comp)) {
                            componenteClicDerecho = comp;
                            menuContextual.show(MiPanel.this, e.getX(), e.getY());
                            return;
                        }
                    }
                    for (Cable cable : circuito.getCables()) {
                        if (cable.estaCerca(e.getX(), e.getY())) {
                            cableClicDerecho = cable;
                            menuContextual.show(MiPanel.this, e.getX(), e.getY());
                            return;
                        }
                    }
                    return;
                }

                // Clic izquierdo: iniciar conexión de cable si tocó pin
                if (SwingUtilities.isLeftMouseButton(e)) {
                    for (Componenetes comp : circuito.getComponentes()) {
                        Pin pin = comp.obtenerPinSiEstáSobre(e.getX(), e.getY());
                        if (pin != null) {
                            cableEnCurso = new Cable(pin);
                            return;
                        }
                    }
                    // Clic izquierdo: preparar arrastre de componente
                    for (Componenetes comp : circuito.getComponentes()) {
                        if (dentroComponente(e.getX(), e.getY(), comp)) {
                            componenteSeleccionado = comp;
                            desplazamientoX = e.getX() - comp.x;
                            desplazamientoY = e.getY() - comp.y;
                            setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                            return;
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Finalizar conexión de cable
                if (cableEnCurso != null) {
                    for (Componenetes comp : circuito.getComponentes()) {
                        Pin destino = comp.obtenerPinSiEstáSobre(e.getX(), e.getY());
                        if (destino != null && destino != cableEnCurso.pinInicio) {
                            cableEnCurso.conectar(destino);
                            circuito.agregarCable(cableEnCurso);
                            break;
                        }
                    }
                    cableEnCurso = null;
                }
                // Terminar arrastre
                componenteSeleccionado = null;
                setCursor(Cursor.getDefaultCursor());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (componenteSeleccionado != null) {
                    componenteSeleccionado.x = Math.max(0, Math.min(e.getX() - desplazamientoX, getWidth() - componenteSeleccionado.ancho));
                    componenteSeleccionado.y = Math.max(0, Math.min(e.getY() - desplazamientoY, getHeight() - componenteSeleccionado.alto));
                    repaint();
                }
                if (cableEnCurso != null) {
                    cableEnCurso.actualizarFinal(e.getX(), e.getY());
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                hoveredPin = null;
                for (Componenetes comp : circuito.getComponentes()) {
                    Pin candidate = comp.obtenerPinSiEstáSobre(e.getX(), e.getY());
                    if (candidate != null) {
                        hoveredPin = candidate;
                        break;
                    }
                }
                if (cableEnCurso != null) {
                    cableEnCurso.actualizarFinal(e.getX(), e.getY());
                }
                repaint();
            }
        });
        
        new Timer(50, e -> {
            circuito.evaluar();
            repaint();
        }).start();
    }

    /**
     * Muestra un diálogo con las expresiones lógicas generadas para cada LED.
     */
    public void mostrarExpresiones() {
            GeneradorExpresion gen = new GeneradorExpresion(circuito);
            Map<Led, String> expresiones = gen.generarExpresiones();
            StringBuilder sb = new StringBuilder();
            expresiones.forEach((led, expr) -> sb.append("LED at (" + led.x + "," + led.y + "): " + expr + "\n"));
            JOptionPane.showMessageDialog(this, sb.length() > 0 ? sb.toString() : "No hay LEDs conectados.");
    }
    
    public void mostrarTablaDesdeExpresion() {
        String expr = JOptionPane.showInputDialog(this, "Ingrese expresión booleana:", "Tabla de Verdad", JOptionPane.PLAIN_MESSAGE);
        if (expr == null || expr.isBlank()) return;
        try {
            TablaVerdad tv = new TablaVerdad(expr);
            List<String> variables = tv.getVariables();
            List<boolean[]> combinaciones = tv.getCombinaciones();
            List<Boolean> resultados = tv.getResultados();
            String[] columnas = new String[variables.size() + 1];
            for (int i = 0; i < variables.size(); i++) {
                columnas[i] = variables.get(i);
            }
            columnas[variables.size()] = "Resultado";
            String[][] datos = new String[combinaciones.size()][columnas.length];
            for (int i = 0; i < combinaciones.size(); i++) {
                boolean[] fila = combinaciones.get(i);
                for (int j = 0; j < fila.length; j++) {
                    datos[i][j] = fila[j] ? "1" : "0";
                }
                datos[i][variables.size()] = resultados.get(i) ? "1" : "0";
            }
            JTable tabla = new JTable(datos, columnas);
            JScrollPane scroll = new JScrollPane(tabla);
            tabla.setFillsViewportHeight(true);
            JOptionPane.showMessageDialog(this, scroll, "Tabla de Verdad: " + expr, JOptionPane.PLAIN_MESSAGE);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(this, "Error al generar tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     /**
     * Genera el circuito a partir de una expresión ingresada por el usuario.
     */
   public void generarDesdeExpresion() {
        String expr = JOptionPane.showInputDialog(this, "Ingrese expresión booleana:", "S1 XOR S2", JOptionPane.PLAIN_MESSAGE);
        if (expr == null || expr.isBlank()) return;

        try {
            List<Componenetes> componentes = new ArrayList<>();
            List<Cable> cables = new ArrayList<>();
            GeneradorCircuito gc = new GeneradorCircuito();
            Componenetes raiz = gc.construirDesdeExpresion(expr, cables, componentes);

            // Limpiar listas actuales del circuito
            circuito.getComponentes().clear();
            circuito.getCables().clear();

            // Agregar nuevos componentes y cables
            for (Componenetes c : componentes) {
                circuito.agregarComponente(c);
            }
            for (Cable c : cables) {
                circuito.agregarCable(c);
            }

            repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    

    private void eliminarSeleccionado() {
        if (componenteClicDerecho != null) {
            circuito.eliminarComponente(componenteClicDerecho);
            componenteClicDerecho = null;
        } else if (cableClicDerecho != null) {
            circuito.eliminarCable(cableClicDerecho);
            cableClicDerecho = null;
        }
        repaint();
    }


   public void agregarComponente(int x, int y, String tipo, int num) {
    Componenetes nuevo = ComponenteFactory.crearComponente(tipo, x, y, num);
    circuito.agregarComponente(nuevo);
    repaint();
}

    private boolean dentroComponente(int mouseX, int mouseY, Componenetes comp) {
        return mouseX >= comp.x && mouseX <= comp.x + comp.ancho &&
               mouseY >= comp.y && mouseY <= comp.y + comp.alto;
    }

    public void guardarCircuitoEnDisco(String rutaArchivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(circuito);
            JOptionPane.showMessageDialog(this, "Circuito guardado exitosamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar el circuito: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cargarCircuitoDesdeDisco(String rutaArchivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            circuito = (Circuito) ois.readObject();
            repaint();
            JOptionPane.showMessageDialog(this, "Circuito cargado exitosamente.");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el circuito: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Componenetes comp : circuito.getComponentes()) comp.dibujar(g);
        for (Cable cable : circuito.getCables()) cable.dibujar(g);
        if (cableEnCurso != null) cableEnCurso.dibujar(g);
        if (hoveredPin != null) hoveredPin.dibujarResaltado((Graphics2D) g);
    }

    private void duplicarSeleccionado() throws CloneNotSupportedException {
    if (componenteClicDerecho != null) {
        Componenetes clon = (Componenetes) componenteClicDerecho.Clone();
        clon.x += 20; // Mueve el clon para que no se superponga
        clon.y += 20;
        circuito.agregarComponente(clon);
        repaint();
    }
    }
    
    public void limpiarCircuito() {
    circuito.getComponentes().clear();
    circuito.getCables().clear();
    repaint();
}

}
