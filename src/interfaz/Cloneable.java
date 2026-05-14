package interfaz;

import componentes.Componenetes;

public interface Cloneable<T extends Componenetes> {
    T clonar(T original);
}
