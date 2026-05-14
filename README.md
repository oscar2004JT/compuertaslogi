# Compuertas Lógicas

Este proyecto lo desarrollé como parte de una clase, con el objetivo de construir una aplicación de escritorio en Java para crear, visualizar y probar circuitos de compuertas lógicas de forma interactiva. En este trabajo implementé una herramienta con la que puedo armar circuitos manualmente, conectar componentes, evaluar su comportamiento y además trabajar con expresiones booleanas dentro de la misma interfaz.

La aplicación me permite:

- Agregar compuertas `AND`, `OR`, `NOT` y `XOR`.
- Agregar entradas tipo `Switch` y salidas tipo `Led`.
- Conectar componentes visualmente por medio de cables.
- Mover, duplicar y eliminar componentes dentro del área de trabajo.
- Guardar y cargar circuitos desde disco.
- Generar expresiones booleanas a partir de un circuito armado.
- Generar un circuito automáticamente desde una expresión booleana.
- Generar tablas de verdad a partir de expresiones booleanas.

## Tecnologías que utilicé

- Java 21
- Java Swing para la interfaz gráfica
- Apache Ant como sistema de construcción
- NetBeans como entorno de desarrollo del proyecto

## Cómo funciona

La aplicación inicia en la clase principal `interfaz.Main`, que carga la ventana y el panel de trabajo donde construyo el circuito. En ese panel puedo insertar componentes, arrastrarlos, conectarlos y ver cómo se propagan las señales en tiempo real.

La lógica del proyecto está separada en varias partes:

- `src/interfaz`: contiene la ventana principal y los renderizadores gráficos de cada componente.
- `src/componentes`: contiene el modelo del circuito, compuertas, switches, LEDs, generación de expresiones y generación automática de circuitos.
- `src/logica`: contiene clases de apoyo como cables, fábrica de componentes y generación de tablas de verdad.
- `src/Imagenes`: contiene los recursos gráficos usados por la interfaz.

## Funcionalidades principales

### 1. Construcción manual del circuito

Yo puedo agregar componentes desde el menú de la aplicación y colocarlos dentro del área de trabajo. Luego conecto sus pines para formar el circuito lógico que quiera simular.

### 2. Evaluación del circuito

El circuito se evalúa periódicamente para propagar el estado de las entradas hacia las salidas. De esta manera puedo activar o desactivar los `Switch` y observar el resultado directamente en los `Led`.

### 3. Generación de expresiones booleanas

Si ya armé un circuito, la aplicación puede recorrer sus conexiones y construir la expresión lógica correspondiente para cada salida conectada a un `Led`.

### 4. Generación automática de circuitos

También implementé una funcionalidad que recibe una expresión booleana, la interpreta como un árbol lógico y construye automáticamente el circuito visual equivalente.

### 5. Tabla de verdad

Además incluí una utilidad para ingresar una expresión booleana y obtener su tabla de verdad, mostrando todas las combinaciones posibles de entrada y su resultado.

## Cómo ejecutar el proyecto

### Opción 1. Desde NetBeans

1. Abrir el proyecto en NetBeans.
2. Compilar el proyecto.
3. Ejecutar la clase principal `interfaz.Main`.

### Opción 2. Desde Ant

En la raíz del proyecto ejecutar:

```powershell
ant run
```

## Estructura del proyecto

```text
src/
  componentes/
  interfaz/
  logica/
  Imagenes/
test/
build.xml
```

## Objetivo del proyecto

Con este proyecto busqué integrar la parte visual con la lógica de simulación para tener una herramienta educativa y práctica sobre compuertas lógicas. No solo quise dibujar componentes en pantalla, sino también permitir que el circuito se comportara como uno real, que pudiera transformarse en expresiones booleanas y que esas expresiones también pudieran volver a convertirse en circuitos.

## Estado actual

Actualmente el proyecto ya permite modelar circuitos básicos, simular su comportamiento y trabajar con expresiones lógicas de manera bidireccional. Es una base funcional que se puede seguir ampliando con más compuertas, validaciones, mejoras visuales o soporte para circuitos más complejos.
