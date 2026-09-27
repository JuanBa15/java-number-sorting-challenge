package com.juan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumberSearch {

    public List<Integer> findPositions(
            List<BigDecimal> numbers,
            BigDecimal target //Número que quiero buscar
    ) {
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++) {
            if (numbers.get(i).compareTo(target) == 0) {
                positions.add(i + 1);
            }
        }

        return positions;
    }
}
