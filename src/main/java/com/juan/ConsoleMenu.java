package com.juan;

import com.juan.algorithms.MergeSort;
import com.juan.algorithms.BubbleSort;
import com.juan.algorithms.HeapSort;
import com.juan.algorithms.QuickSort;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private static final Path GENERATED_FILE = Path.of("data", "numbers.txt");
    private static final Path SORTED_FILE = Path.of("data", "numbers-sorted.txt");
    private final Scanner scanner;

    public ConsoleMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;

        printMenu();

        while (running) {
            System.out.print("Seleccione una opción: ");

            // Salimos del menú si la entrada estándar se cerró o llegamos al final.
            if (!scanner.hasNext()) {
                System.out.println();
                break;
            }

            if (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número del menú.");
                scanner.nextLine(); //Descartamos la entrada inválida
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine(); //Consumimos el salto de línea pendiente

            switch (option) {
                case 0 -> printMenu();
                case 1 -> generateFile();
                case 2 -> readGeneratedFile();
                case 3 -> sortGeneratedFile();
                case 4 -> readSortedFile();
                case 5 -> searchNumber();
                case 6 -> {
                    System.out.println("Hasta luego.");
                    running = false;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Ordenador de números ===");
        System.out.println("0 - Mostrar Menú");
        System.out.println("1 - Generar nuevo archivo");
        System.out.println("2 - Leer archivo generado");
        System.out.println("3 - Ordenar archivo");
        System.out.println("4 - Leer archivo ordenado");
        System.out.println("5 - Buscar número en archivo");
        System.out.println("6 - Salir");
    }

    private void generateFile() {
        System.out.print("¿Cuántos números quiere generar?: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Ingrese una cantidad entera válida.");
            scanner.nextLine();
            return;
        }

        int count = scanner.nextInt();
        scanner.nextLine();

        if (count <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }

        NumberGenerator generator = new NumberGenerator();
        List<BigDecimal> numbers = generator.generate(count);

        NumberFileRepository repository = new NumberFileRepository(GENERATED_FILE);

        try {
            repository.save(numbers);
            System.out.println("Se generaron y guardaron " + count + " números.");
            System.out.println("Archivo: data/numbers.txt");
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    private void readGeneratedFile() {
        readFile(GENERATED_FILE, "Números del archivo generado:");
    }

    private void sortGeneratedFile() {

        SortingAlgorithm algorithm = chooseSortingAlgorithm();

        if (algorithm == null) return;

        NumberFileRepository sourceRepository = new NumberFileRepository(GENERATED_FILE);
        NumberFileRepository sortedRepository = new NumberFileRepository(SORTED_FILE);

        try {
            List<BigDecimal> numbers = sourceRepository.read();

            long startTime = System.nanoTime(); //Registro el inicio

            List<BigDecimal> sortedNumbers = algorithm.sort(numbers);

            long endTime = System.nanoTime(); //Registro el final
            long differenceNanos = endTime - startTime; //Duración de tiempo del algoritmo

            System.out.println(
                    algorithm.getClass().getSimpleName()
                            + " tardó " + differenceNanos
                            + " ns en ordenar " + numbers.size() + " números."
            );

            /*Hacemos después el guardado, porque el tiempo de guardado no nos
             * interesa en la comparación de tiempos de los algoritmos.*/
            sortedRepository.save(sortedNumbers);

            System.out.println(
                    "Archivo ordenado con " + algorithm.getClass().getSimpleName() + "."
            );
            System.out.println("Resultado guardado en data/numbers-sorted.txt");
        } catch (IOException e) {
            System.out.println("No se pudo leer o guardar el archivo: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo interpretar el archivo: "
                    + e.getMessage());
        }
    }

    private SortingAlgorithm chooseSortingAlgorithm() {
        System.out.println("¿Qué método de ordenamiento quiere utilizar?");
        System.out.println("1 - Merge Sort");
        System.out.println("2 - Quick Sort");
        System.out.println("3 - Heap Sort");
        System.out.println("4 - Bubble Sort");
        System.out.print("Seleccione: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Ingrese una opción numérica.");
            scanner.nextLine();
            return null;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        SortingAlgorithm algorithm = switch (option) {
            case 1 -> new MergeSort();
            case 2 -> new QuickSort();
            case 3 -> new HeapSort();
            case 4 -> new BubbleSort();
            default -> null;
        };

        if (algorithm == null) {
            System.out.println("Opción de ordenamiento no válida.");
        }

        return algorithm;
    }

    private void readSortedFile() {
        readFile(SORTED_FILE, "Números del archivo ordenado:");
    }

    private void readFile(Path filePath, String title) {
        NumberFileRepository repository = new NumberFileRepository(filePath);

        try {
            List<BigDecimal> numbers = repository.read();

            if (numbers.isEmpty()) {
                System.out.println("El archivo no contiene números.");
                return;
            }

            System.out.println(title);

            for (int i = 0; i < numbers.size(); i++) {
                System.out.printf(
                        "%d) %s%n",
                        i + 1,
                        numbers.get(i).toPlainString()
                );
            }
        } catch (IOException e) {
            System.out.println(
                    "No se pudo leer el archivo. Verifique que exista: " + e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "No se pudo interpretar el archivo: " + e.getMessage()
            );
        }
    }

    private void searchNumber() {

        System.out.println("¿En qué archivo desea buscar?");
        System.out.println("1 - Original (archivo con los números sin ordenar)");
        System.out.println("2 - Ordenado (archivo con los números ordenados)");
        System.out.print("Seleccione: ");
        String option = scanner.nextLine().trim();

        int fileOption;

        try {
            fileOption = Integer.parseInt(option);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese 1 o 2.");
            return;
        }

        Path filePath;

        switch (fileOption) {
            case 1 -> filePath = GENERATED_FILE;
            case 2 -> filePath = SORTED_FILE;
            default -> {
                System.out.println("Opción de archivo no válida.");
                return;
            }
        }

        System.out.print("¿Qué número desea buscar?: ");
        String input = scanner.nextLine().trim();

        BigDecimal target;

        try {
            target = new BigDecimal(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido, por ejemplo: -6.25");
            return;
        }

        searchInFile(filePath, target);
    }

    private void searchInFile(Path filePath, BigDecimal target) {
        NumberFileRepository repository = new NumberFileRepository(filePath);

        try {
            List<BigDecimal> numbers = repository.read();

            if (numbers.isEmpty()) {
                System.out.println("El archivo no contiene números.");
                return;
            }

            NumberSearch searcher = new NumberSearch();
            List<Integer> positions = searcher.findPositions(numbers, target);

            if (positions.isEmpty()) {
                System.out.println(
                        "Número " + target.toPlainString()
                                + " no encontrado en " + filePath
                );
            } else {
                System.out.println(
                        "Número " + target.toPlainString()
                                + " encontrado en " + filePath
                                + " en la posición o posiciones: " + positions
                );
            }
        } catch (IOException e) {
            System.out.println(
                    "No se pudo leer el archivo. Genérelo primero: "
                            + e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "No se pudo interpretar el archivo: " + e.getMessage()
            );
        }
    }
}
