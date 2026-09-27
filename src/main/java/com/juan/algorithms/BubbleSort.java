package com.juan.algorithms;

import com.juan.SortingAlgorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BubbleSort implements SortingAlgorithm {

    @Override
    public List<BigDecimal> sort(List<BigDecimal> numbers) {
        List<BigDecimal> result = new ArrayList<>(numbers);

        for (int end = result.size() - 1; end > 0; end--) {
            boolean swapped = false;

            for (int i = 0; i < end; i++) {
                if (result.get(i).compareTo(result.get(i + 1)) > 0) {
                    BigDecimal temporary = result.get(i);
                    result.set(i, result.get(i + 1));
                    result.set(i + 1, temporary);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }

        return result;
    }
}
