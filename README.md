# Ordenador de números

Aplicación de consola en Java desarrollada como solución al *Internship Quick Challenge*. Genera números aleatorios, los guarda en un archivo, permite consultarlos y los ordena con distintos algoritmos.

## Requisitos

- JDK 25
- IntelliJ IDEA (recomendado para abrir y ejecutar el proyecto)
- Maven, si se desea compilar desde la terminal

El proyecto no utiliza dependencias externas.

## Abrir y ejecutar en IntelliJ IDEA

1. Clona el repositorio o descárgalo desde GitHub.
2. En IntelliJ IDEA, selecciona **File → Open** y elige la carpeta del proyecto.
3. Importa o sincroniza el proyecto como Maven si IntelliJ lo solicita.
4. Configura JDK 25 como SDK del proyecto.
5. Abre `src/main/java/com/juan/Main.java` y ejecuta `Main.main()` con el botón verde junto al método.

Desde una terminal con Maven instalado también se puede compilar con:

```bash
mvn clean package
```

## Ejecutar con Docker

La imagen del proyecto está publicada en [Docker Hub](https://hub.docker.com/r/juanba15/java-number-sorter). Con Docker instalado y en ejecución, abre una terminal y ejecuta:

```bash
docker container run -it --name java-number-sorter juanba15/java-number-sorter:1.0
```

El contenedor conserva el nombre `java-number-sorter` después de que la aplicación termine. Para volver a iniciarlo, usa `docker container start -ai java-number-sorter`. Si quieres crearlo otra vez desde cero, primero elimina el anterior con `docker container rm java-number-sorter`.

También puedes usar esta variante:

```bash
docker container run --rm -it juanba15/java-number-sorter:1.0
```

Docker descargará la imagen automáticamente si todavía no está disponible en la máquina. `-it` permite interactuar con el menú desde la terminal; en esta variante, `--rm` elimina el contenedor al salir de la aplicación. Es una opción práctica para ejecutar el programa sin conservar el contenedor.

## Funcionalidades

| Opción | Función |
|---|---|
| 0 | Mostrar el menú |
| 1 | Generar números aleatorios y guardarlos en `data/numbers.txt` |
| 2 | Leer y mostrar el archivo original |
| 3 | Elegir un algoritmo, ordenar los números y guardar el resultado en `data/numbers-sorted.txt`; también muestra el tiempo de ordenamiento |
| 4 | Leer y mostrar el archivo ordenado |
| 5 | Buscar un número en el archivo original o en el ordenado |
| 6 | Salir |

La generación produce valores entre `-100.00` y `100.00`, con dos posiciones decimales. Cada número se almacena en una línea. Al mostrar las listas, las posiciones empiezan en 1.

Los algoritmos disponibles son:

- Merge Sort
- Quick Sort
- Bubble Sort

La búsqueda compara los valores numéricamente; por ejemplo, `5`, `5.0` y `5.00` se consideran iguales. Cuando hay coincidencias repetidas, muestra todas sus posiciones dentro del archivo seleccionado.

El tiempo se mide alrededor de la llamada al algoritmo con `System.nanoTime()` y se expresa en nanosegundos. Es una medición individual por ejecución; con listas pequeñas puede variar y no debe tomarse como una comparación concluyente de rendimiento.

## Organización del código

- `Main`: punto de entrada de la aplicación.
- `ConsoleMenu`: menú e interacción con el usuario.
- `NumberGenerator`: generación de valores usando `Random` y `BigDecimal`.
- `NumberFileRepository`: lectura y escritura de números en archivos de texto.
- `NumberSearch`: búsqueda de valores y cálculo de sus posiciones.
- `SortingAlgorithm`: contrato común de los algoritmos de ordenamiento.
- `algorithms/MergeSort`, `algorithms/QuickSort` y `algorithms/BubbleSort`: implementaciones de ordenamiento.

Los archivos generados en `data/*.txt` y los archivos compilados en `target/` están excluidos de Git.
