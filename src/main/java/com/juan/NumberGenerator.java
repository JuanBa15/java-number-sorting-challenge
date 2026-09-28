package com.juan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGenerator {

    private static final int MIN_CENTS = -10_000;
    private static final int MAX_CENTS = 10_001;
    private static final int DECIMAL_SCALE = 2;

    private final Random random = new Random();

    public List<BigDecimal> generate(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        List<BigDecimal> numbers = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int randomCents = random.nextInt(MIN_CENTS, MAX_CENTS);
            BigDecimal number = BigDecimal.valueOf(randomCents, DECIMAL_SCALE);
            numbers.add(number);
        }

        return numbers;
    }
}
