package com.juan;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NumberFileRepository {
    private final Path filePath;

    public NumberFileRepository(Path filePath) {
        // Comprobamos que no se haya llamado a este metodo sin haber pasado una ruta correcta
        this.filePath = Objects.requireNonNull(
                filePath,
                "La ruta del archivo no puede ser null."
        );
    }

    public void save(List<BigDecimal> numbers) throws IOException {

        // Obtenemos la carpeta contenedora del archivo
        Path parent = filePath.getParent();

        // Si es la primera vez que vamos a generar el archivo, creamos la carpeta Data
        if (parent != null) {
            Files.createDirectories(parent);
        }

        List<String> lines = new ArrayList<>();

        for (BigDecimal number : numbers) {
            lines.add(number.toPlainString()); // Convertimos a string para evitar quedarnos con números en notación científica
        }

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    public List<BigDecimal> read() throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        List<BigDecimal> numbers = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim(); // Quitamos espacios en blanco al inicio y al final de texto

            if (line.isEmpty()) continue;

            try {
                numbers.add(new BigDecimal(line));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                        "Contenido inválido en la línea " + (i + 1) + ": " + line, e
                );
            }
        }

        return numbers;
    }
}
