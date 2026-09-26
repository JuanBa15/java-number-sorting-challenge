package com.juan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGenerator {

    private final Random random = new Random();

    public List<BigDecimal> generate(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        List<BigDecimal> numbers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int randomCents = random.nextInt(-10_000, 10_001);
            BigDecimal number = BigDecimal.valueOf(randomCents, 2);
            numbers.add(number);
        }

        return numbers;
    }
}
