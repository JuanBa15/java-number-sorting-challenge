package com.juan;

import com.juan.algorithms.MergeSort;

import java.util.Scanner;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;

public class ConsoleMenu {
    private final Scanner scanner;

    public ConsoleMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Seleccione una opción: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Por favor, ingrese un número del menú.");
                scanner.nextLine(); //Descarta la entrada inválida
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine(); //Consume el salto de línea pendiente

            switch (option) {
                case 0 -> printMenu();
                case 1 -> generateFile();
                case 2 -> readGenerateFile();
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

        NumberFileRepository repository =
                new NumberFileRepository(Path.of("data", "numbers.txt"));

        try {
            repository.save(numbers);
            System.out.println("Se generaron y guardaron " + count + " números.");
            System.out.println("Archivo: data/numbers.txt");
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo: " + e.getMessage());
        }
    }

    private void readGenerateFile() {
        readFile(
                Path.of("data", "numbers.txt"),
                "Números del archivo generado:"
        );
    }

    private void sortGeneratedFile() {
        NumberFileRepository sourceRepository =
                new NumberFileRepository(Path.of("data", "numbers.txt"));

        NumberFileRepository sortedRepository =
                new NumberFileRepository(Path.of("data", "numbers-sorted.txt"));

        try {
            List<BigDecimal> numbers = sourceRepository.read();

            SortingAlgorithm algorithm = new MergeSort();
            List<BigDecimal> sortedNumbers = algorithm.sort(numbers);

            sortedRepository.save(sortedNumbers);

            System.out.println("Archivo ordenado con Merge Sort.");
            System.out.println("Resultado guardado en data/numbers-sorted.txt");
        } catch (IOException e) {
            System.out.println("No se pudo leer o guardar el archivo: "
                    + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo interpretar el archivo: "
                    + e.getMessage());
        }
    }

    private void readSortedFile() {
        readFile(
                Path.of("data", "numbers-sorted.txt"),
                "Números del archivo ordenado:"
        );
    }

    private void readFile(Path filePath, String title) {
        NumberFileRepository repository =
                new NumberFileRepository(filePath);

        try {
            List<BigDecimal> numbers = repository.read();

            if (numbers.isEmpty()) {
                System.out.println("El archivo no contiene números.");
                return;
            }

            System.out.println(title);

            for (int i = 0; i < numbers.size(); i++) {
                System.out.printf(
                        "%d: %s%n",
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

        Integer fileOption = new Integer(option);
        Path filePath;

        switch (fileOption) {
            case 1 -> filePath = Path.of("data", "numbers.txt");
            case 2 -> filePath = Path.of("data", "numbers-sorted.txt");
            default -> {
                System.out.println("Opción de archivo no válida.");
                return;
            }
        }

        NumberFileRepository repository = new NumberFileRepository(filePath);

        System.out.print("¿Qué número desea buscar?: ");
        String input = scanner.nextLine().trim();

        BigDecimal target;

        try {
            target = new BigDecimal(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido, por ejemplo: -6.25");
            return;
        }

        try {
            List<BigDecimal> numbers = repository.read();

            if (numbers.isEmpty()) {
                System.out.println("El archivo no contiene números.");
                return;
            }

            NumberSearch searcher = new NumberSearch();
            List<Integer> positions = searcher.findPosition(numbers, target);

            if (positions.isEmpty()) {
                System.out.println("Número " + target.toPlainString() + " no encontrado.");
            } else {
                System.out.println(
                        "Número " + target.toPlainString()
                                + " encontrado en " + filePath
                                + " en la posición o posiciones: "
                                + positions
                );
            }
        } catch (IOException e) {
            System.out.println(
                    "No se pudo leer el archivo. Genérelo primero: " + e.getMessage()
            );
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "No se pudo interpretar el archivo: " + e.getMessage()
            );
        }
    }
}
