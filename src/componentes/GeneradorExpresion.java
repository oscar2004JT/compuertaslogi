package componentes;

import java.util.*;
import logica.Cable;

public class GeneradorExpresion {
    private Circuito circuito;

    public GeneradorExpresion(Circuito circuito) {
        this.circuito = circuito;
    }

    public Map<Led, String> generarExpresiones() {
        Map<Led, String> resultados = new LinkedHashMap<>();
        Map<Pin, Cable> cableMap = new HashMap<>();
        for (Cable c : circuito.getCables()) {
            if (c.pinFin != null) {
                cableMap.put(c.pinFin, c);
            }
        }

        for (Componenetes comp : circuito.getComponentes()) {
            if (comp instanceof Led) {
                Led led = (Led) comp;
                Pin entrada = comp.getPins().stream()
                    .filter(p -> !p.esSalida)
                    .findFirst().orElse(null);

                String expr = buildExpresion(entrada, cableMap);
                resultados.put(led, expr);
            }
        }
        return resultados;
    }

    private String buildExpresion(Pin pin, Map<Pin, Cable> cableMap) {
        if (pin == null) {
            return "";
        }

        Cable cable = cableMap.get(pin);
        if (cable == null) {
            return "UNCONNECTED";
        }

        Pin fuente = cable.pinInicio;
        Componenetes comp = findComponentePorPin(fuente);
        if (comp == null) {
            return "";
        }

        if (comp instanceof Switch) {
            int idx = circuito.getComponentes().indexOf(comp) + 1;
            return "S" + idx;
        }

        List<Pin> inputs = new ArrayList<>();
        Pin output = null;
        for (Pin p : comp.getPins()) {
            if (p.esSalida) output = p;
            else inputs.add(p);
        }

        String op;
        if (comp instanceof AND) op = " AND ";
        else if (comp instanceof OR) op = " OR ";
        else if (comp instanceof XOR) op = " XOR ";
        else if (comp instanceof NOT) {
            String sub = buildExpresion(inputs.get(0), cableMap);
            return "(NOT " + sub + ")";
        } else {
            op = " ? ";
        }

        List<String> subExprs = new ArrayList<>();
        for (Pin inPin : inputs) {
            subExprs.add(buildExpresion(inPin, cableMap));
        }

        return "(" + String.join(op, subExprs) + ")";
    }

    private Componenetes findComponentePorPin(Pin pin) {
        for (Componenetes comp : circuito.getComponentes()) {
            if (comp.getPins().contains(pin)) {
                return comp;
            }
        }
        return null;
    }
}
