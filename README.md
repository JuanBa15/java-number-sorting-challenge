# Ordenador de números

Aplicación de consola en Java para generar números aleatorios, guardarlos en archivos de texto y ordenarlos. Proyecto desarrollado como solución al *Internship Quick Challenge*.

## Requisitos

- JDK 25
- IntelliJ IDEA
- Maven (opcional desde la terminal; IntelliJ también puede importar y ejecutar el proyecto Maven)

El proyecto no utiliza dependencias externas.

## Abrir y ejecutar en IntelliJ IDEA

1. Clona el repositorio o descárgalo desde GitHub.
2. En IntelliJ IDEA, selecciona **File → Open** y elige la carpeta del proyecto.
3. Si IntelliJ pregunta, impórtalo como proyecto Maven.
4. Configura el JDK 25 como SDK del proyecto.
5. Abre `src/main/java/com/juan/Main.java` y ejecuta `Main.main()` con el botón verde junto al método.

También puedes compilarlo desde una terminal que tenga Maven instalado:

```bash
mvn clean package
```

## Funcionalidades

| Opción | Función | Estado |
|---|---|---|
| 0 | Mostrar el menú | Implementada |
| 1 | Generar números aleatorios y guardarlos en `data/numbers.txt` | Implementada |
| 2 | Leer y mostrar `data/numbers.txt` | Implementada |
| 3 | Ordenar con Merge Sort y guardar en `data/numbers-sorted.txt` | Implementada |
| 4 | Leer y mostrar el archivo ordenado | Pendiente |
| 5 | Buscar un número en el archivo | Pendiente |
| 6 | Salir | Implementada |

La generación produce números entre `-100.00` y `100.00`, con dos posiciones decimales. Los archivos de datos se crean dentro de `data/`, relativa al directorio de trabajo desde el cual se ejecuta el programa.

## Organización del código

- `Main`: punto de entrada de la aplicación.
- `ConsoleMenu`: interacción con el usuario y despacho de opciones.
- `NumberGenerator`: generación de valores usando `Random` y `BigDecimal`.
- `NumberFileRepository`: lectura y escritura de números en archivos de texto.
- `SortingAlgorithm`: contrato común para los algoritmos de ordenamiento.
- `algorithms/MergeSort`: implementación de Merge Sort.

Cada número se almacena en una línea del archivo. Los resultados generados en `data/*.txt` y los archivos compilados de `target/` están excluidos de Git.

## Próximos pasos

- Implementar la lectura del archivo ordenado desde el menú.
- Añadir búsqueda de números.
- Incorporar otro algoritmo de ordenamiento y comparar sus tiempos de ejecución.
