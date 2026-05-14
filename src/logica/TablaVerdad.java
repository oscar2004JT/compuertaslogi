package logica;

import java.util.*;
import java.util.regex.*;

public class TablaVerdad {

    private String expresionRaw;
    private Nodo root;
    private List<String> variables;
    private List<boolean[]> combinaciones;
    private List<Boolean> resultados;

    public TablaVerdad(String expresion) {
        this.expresionRaw = expresion.replaceAll("\\s+", "");
        this.root = parsear(this.expresionRaw);
        this.variables = new ArrayList<>(collectVariables(root));
        Collections.sort(this.variables);
        this.combinaciones = new ArrayList<>();
        this.resultados = new ArrayList<>();
        generarDatos();
    }

    private void generarDatos() {
        int n = variables.size();
        int filas = 1 << n;
        for (int mask = 0; mask < filas; mask++) {
            boolean[] entrada = new boolean[n];
            Map<String, Boolean> vals = new HashMap<>();
            for (int i = 0; i < n; i++) {
                boolean bit = (mask & (1 << (n - i - 1))) != 0;
                entrada[i] = bit;
                vals.put(variables.get(i), bit);
            }
            combinaciones.add(entrada);
            resultados.add(evaluar(root, vals));
        }
    }

    public void generar() {
        for (String v : variables) System.out.print(v + "\t");
        System.out.println("| Resultado");
        for (int i = 0; i < combinaciones.size(); i++) {
            for (boolean b : combinaciones.get(i))
                System.out.print((b ? 1 : 0) + "\t");
            System.out.println("|   " + (resultados.get(i) ? 1 : 0));
        }
    }

    public List<String> getVariables()        { return variables; }
    public List<boolean[]> getCombinaciones() { return combinaciones; }
    public List<Boolean> getResultados()      { return resultados; }

    private boolean evaluar(Nodo nodo, Map<String, Boolean> vals) {
        switch (nodo.tipo) {
            case "VAR":
                return vals.getOrDefault(nodo.valor, false);
            case "NOT":
                return !evaluar(nodo.izq, vals);
            case "AND":
                return evaluar(nodo.izq, vals) && evaluar(nodo.der, vals);
            case "OR":
                return evaluar(nodo.izq, vals) || evaluar(nodo.der, vals);
            case "XOR":
                return evaluar(nodo.izq, vals) ^ evaluar(nodo.der, vals);
            default:
                throw new IllegalArgumentException("Tipo desconocido: " + nodo.tipo);
        }
    }

    private Set<String> collectVariables(Nodo nodo) {
        Set<String> s = new HashSet<>();
        if (nodo == null) return s;
        if ("VAR".equals(nodo.tipo)) {
            s.add(nodo.valor);
        } else {
            s.addAll(collectVariables(nodo.izq));
            if (nodo.der != null) s.addAll(collectVariables(nodo.der));
        }
        return s;
    }

    private Nodo parsear(String expr) {
        expr = trimParens(expr);
        int nivel = 0;
        for (int i = expr.length() - 1; i >= 0; i--) {
            char c = expr.charAt(i);
            if (c == ')') nivel++;
            else if (c == '(') nivel--;
            else if (nivel == 0) {
                if (i >= 2 && expr.substring(i - 2, i + 1).equals("AND")) {
                    return new Nodo("AND",
                        parsear(expr.substring(0, i - 2)),
                        parsear(expr.substring(i + 1)));
                }
                if (i >= 2 && expr.substring(i - 2, i + 1).equals("XOR")) {
                    return new Nodo("XOR",
                        parsear(expr.substring(0, i - 2)),
                        parsear(expr.substring(i + 1)));
                }
                if (i >= 1 && expr.substring(i - 1, i + 1).equals("OR")) {
                    return new Nodo("OR",
                        parsear(expr.substring(0, i - 1)),
                        parsear(expr.substring(i + 1)));
                }
            }
        }
        if (expr.startsWith("NOT")) {
            String inside = expr.substring(3);
            if (inside.startsWith("(") && inside.endsWith(")")) {
                inside = inside.substring(1, inside.length() - 1);
            }
            return new Nodo("NOT", parsear(inside), null);
        }
        return new Nodo("VAR", expr);
    }

    private String trimParens(String expr) {
        while (expr.startsWith("(") && expr.endsWith(")") &&
               balanced(expr.substring(1, expr.length() - 1))) {
            expr = expr.substring(1, expr.length() - 1);
        }
        return expr;
    }

    private boolean balanced(String s) {
        int bal = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') bal++;
            if (c == ')') bal--;
            if (bal < 0) return false;
        }
        return bal == 0;
    }

    private static class Nodo {
        String tipo;
        String valor;
        Nodo izq, der;

        Nodo(String tipo, Nodo izq, Nodo der) {
            this.tipo = tipo; this.izq = izq; this.der = der;
        }
        Nodo(String tipo, String valor) {
            this.tipo = tipo; this.valor = valor;
        }
    }
}
