package com.juan;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class NumberFileRepository {
    private final Path filePath;

    public NumberFileRepository(Path filePath) {
        this.filePath = filePath;
    }

    public void save(List<BigDecimal> numbers) throws IOException {
        Path parent = filePath.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        List<String> lines = numbers.stream()
                .map(BigDecimal::toPlainString)
                .toList();

        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    public List<BigDecimal> read() throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        List<BigDecimal> numbers = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();

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
