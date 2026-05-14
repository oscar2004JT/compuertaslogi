package componentes;

import interfaz.*;
import logica.Cable;
import java.util.*;
import logica.ComponenteFactory;

/**
 * Clase que permite generar un circuito lógico visual a partir de una expresión booleana.
 * Usa una estructura de árbol para representar la expresión y genera los componentes y conexiones correspondientes.
 */
public class GeneradorCircuito {
    private Map<String, Switch> variables = new HashMap<>();
    private int posX = 50, posY = 100, espaciadoX = 100, espaciadoY = 80;

    /**
     * Construye un circuito lógico a partir de una expresión booleana.
     *
     * @param expresion   La expresión booleana (ej: (S1 AND S2) OR NOT(S3))
     * @param cables      Lista de cables donde se agregarán las conexiones generadas.
     * @param componentes Lista de componentes donde se agregarán los elementos generados.
     * @return El componente raíz del circuito (último evaluado.
     */
    public Componenetes construirDesdeExpresion(String expresion, List<Cable> cables, List<Componenetes> componentes) {
        Nodo nodo = parsear(expresion);
        return construir(nodo, cables, componentes);
    }

    /**
     * Construye recursivamente el circuito lógico a partir de un nodo de expresión.
     *
     * @param nodo        Nodo raíz actual a procesar.
     * @param cables      Lista de cables a los que se añadirán nuevas conexiones.
     * @param componentes Lista de componentes a los que se añadirán nuevos elementos.
     * @return Componente correspondiente al nodo actual.
     */
    public Componenetes construir(Nodo nodo, List<Cable> cables, List<Componenetes> componentes) {
        if (nodo == null) return null;

        if (nodo.tipo.equals("VAR")) {
            String nombre = nodo.valor;
            // Crear un nuevo Switch si no existe
            if (!variables.containsKey(nombre)) {
                Switch sw = (Switch) ComponenteFactory.crearComponente("SWITCH", posX, posY, 1);
                variables.put(nombre, sw);
                componentes.add(sw);
                posY += espaciadoY;
            }
            return variables.get(nombre);
        }

        // Crear compuerta lógica (AND, OR, NOT, XOR)
        Componenetes comp = ComponenteFactory.crearComponente(
            nodo.tipo, posX + espaciadoX, posY, nodo.tipo.equals("NOT") ? 1 : 2
        );

        if (comp == null) {
            System.err.println("Tipo de componente desconocido: " + nodo.tipo);
            return null;
        }

        componentes.add(comp);

        // Conexión de hijo izquierdo
        if (nodo.izq != null) {
            Componenetes hIzq = construir(nodo.izq, cables, componentes);
            conectar(hIzq, comp, cables, 0);
        }

        // Conexión de hijo derecho si no es NOT
        if (nodo.der != null && !"NOT".equals(nodo.tipo)) {
            Componenetes hDer = construir(nodo.der, cables, componentes);
            conectar(hDer, comp, cables, 1);
        }

        return comp;
    }

    /**
     * Conecta un componente origen a un componente destino con un cable.
     *
     * @param origen   Componente fuente.
     * @param destino  Componente destino.
     * @param cables   Lista de cables donde se agregará la conexión.
     * @param idx      Índice del pin de entrada del destino a conectar.
     */
    private void conectar(Componenetes origen, Componenetes destino, List<Cable> cables, int idx) {
        if (origen == null || destino == null) return;

        Pin pOut = origen.getPinSalida();
        Pin pIn  = destino.getPinEntrada(idx);

        if (pOut == null || pIn == null) {
            System.err.println("Error al conectar: pines no disponibles");
            return;
        }

        Cable cable = new Cable(pOut);
        cable.conectar(pIn);
        cables.add(cable);
    }

    // =======================
    //       PARSEADOR
    // =======================

    /**
     * Elimina espacios en blanco y parsea la expresión a un árbol de nodos.
     *
     * @param expr La expresión booleana a procesar.
     * @return Nodo raíz del árbol de la expresión.
     */
    private Nodo parsear(String expr) {
        expr = expr.replaceAll("\\s+", "");
        return parseExpresion(expr);
    }

    /**
     * Parsea una subexpresión booleana recursivamente y genera su nodo correspondiente.
     *
     * @param expr Subexpresión booleana.
     * @return Nodo correspondiente.
     */
    private Nodo parseExpresion(String expr) {
        expr = trimParens(expr);
        int nivel = 0;

        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == ')') nivel++;
            else if (c == '(') nivel--;

            if (nivel == 0) {
                // 1) AND
                if (i >= 2 && expr.substring(i - 2, i + 1).equals("AND")) {
                    return new Nodo("AND",
                        parseExpresion(expr.substring(0, i - 2)),
                        parseExpresion(expr.substring(i + 1))
                    );
                }
                // 2) XOR
                if (i >= 2 && expr.substring(i - 2, i + 1).equals("XOR")) {
                    return new Nodo("XOR",
                        parseExpresion(expr.substring(0, i - 2)),
                        parseExpresion(expr.substring(i + 1))
                    );
                }
                // 3) OR
                if (i >= 1 && expr.substring(i - 1, i + 1).equals("OR")) {
                    return new Nodo("OR",
                        parseExpresion(expr.substring(0, i - 1)),
                        parseExpresion(expr.substring(i + 1))
                    );
                }
            }
        }

        // 4) NOT
        if (expr.startsWith("NOT(") && expr.endsWith(")")) {
            return new Nodo("NOT",
                parseExpresion(expr.substring(4, expr.length() - 1)),
                null
            );
        }
        if (expr.startsWith("NOT")) {
            return new Nodo("NOT",
                parseExpresion(expr.substring(3)),
                null
            );
        }

        // 5) Variable (Switch)
        return new Nodo("VAR", expr);
    }

    /**
     * Elimina paréntesis externos si están balanceados y envuelven toda la expresión.
     *
     * @param expr La expresión a limpiar.
     * @return Expresión sin paréntesis externos innecesarios.
     */
    private String trimParens(String expr) {
        while (expr.startsWith("(") && expr.endsWith(")") && balanced(expr.substring(1, expr.length() - 1))) {
            expr = expr.substring(1, expr.length() - 1);
        }
        return expr;
    }

    /**
     * Verifica si una cadena tiene paréntesis balanceados.
     *
     * @param s Cadena a evaluar.
     * @return true si está balanceada, false si hay error de paréntesis.
     */
    private boolean balanced(String s) {
        int bal = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') bal++;
            if (c == ')') bal--;
            if (bal < 0) return false;
        }
        return bal == 0;
    }

    /**
     * Clase interna que representa un nodo del árbol de la expresión lógica.
     */
    private static class Nodo {
        String tipo;
        String valor;
        Nodo izq, der;

        Nodo(String tipo, Nodo izq, Nodo der) {
            this.tipo = tipo;
            this.izq = izq;
            this.der = der;
        }

        Nodo(String tipo, String valor) {
            this.tipo = tipo;
            this.valor = valor;
        }
    }
}
