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
                case 4 -> System.out.println("Leer archivo ordenado: pendiente");
                case 5 -> System.out.println("Buscar número: pendiente");
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
        NumberFileRepository repository =
                new NumberFileRepository(Path.of("data", "numbers.txt"));

        try {
            List<BigDecimal> numbers = repository.read();

            if (numbers.isEmpty()) {
                System.out.println("El archivo no contiene números.");
                return;
            }

            System.out.println("Números del archivo:");
            for (BigDecimal number : numbers) {
                System.out.println(number.toPlainString());
            }
        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo o no está creado, verifique: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo interpretar el archivo: " + e.getMessage());
        }
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
}
