package com.juan.algorithms;

import com.juan.SortingAlgorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class QuickSort implements SortingAlgorithm {

    @Override
    public List<BigDecimal> sort(List<BigDecimal> numbers) {
        List<BigDecimal> result = new ArrayList<>(numbers);
        quickSort(result, 0, result.size() - 1);
        return result;
    }

    private void quickSort(List<BigDecimal> numbers, int low, int high) {
        int left = low;
        int right = high;
        BigDecimal pivot = numbers.get(low + (high - low) / 2);

        while (left <= right) {
            while (numbers.get(left).compareTo(pivot) < 0) left++;
            while (numbers.get(right).compareTo(pivot) > 0) right--;

            if (left <= right) {
                BigDecimal temporary = numbers.get(left);
                numbers.set(left, numbers.get(right));
                numbers.set(right, temporary);

                left++;
                right--;
            }
        }

        if (low < right) quickSort(numbers, low, right);
        if (left < high) quickSort(numbers, left, high);
    }
}
