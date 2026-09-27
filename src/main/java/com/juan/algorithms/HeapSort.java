package com.juan.algorithms;

import com.juan.SortingAlgorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HeapSort implements SortingAlgorithm {

    @Override
    public List<BigDecimal> sort(List<BigDecimal> numbers) {
        List<BigDecimal> result = new ArrayList<>(numbers);
        int size = result.size();

        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(result, size, i);
        }

        for (int end = size - 1; end > 0; end--) {
            swap(result, 0, end);
            heapify(result, end, 0);
        }

        return result;
    }

    private void heapify(List<BigDecimal> numbers, int size, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size
                && numbers.get(left).compareTo(numbers.get(largest)) > 0) {
            largest = left;
        }

        if (right < size
                && numbers.get(right).compareTo(numbers.get(largest)) > 0) {
            largest = right;
        }

        if (largest != root) {
            swap(numbers, root, largest);
            heapify(numbers, size, largest);
        }
    }

    private void swap(List<BigDecimal> numbers, int first, int second) {
        BigDecimal temporary = numbers.get(first);
        numbers.set(first, numbers.get(second));
        numbers.set(second, temporary);
    }
}
