package com.juan.algorithms;

import com.juan.SortingAlgorithm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MergeSort implements SortingAlgorithm {

    @Override
    public List<BigDecimal> sort(List<BigDecimal> numbers) {
        List<BigDecimal> result = new ArrayList<>(numbers);

        if (result.size() < 2) return result;

        BigDecimal[] temporary = new BigDecimal[result.size()];
        mergeSort(result, temporary, 0, result.size() - 1);

        return result;
    }

    public void mergeSort(
            List<BigDecimal> numbers,
            BigDecimal[] temporary,
            int start,
            int end
    ) {
        if (start >= end) return;

        int middle = start + (end - start) / 2;

        mergeSort(numbers, temporary, start, middle);
        mergeSort(numbers, temporary, middle + 1, end);
        merge(numbers, temporary, start, middle, end);
    }

    public void merge(
            List<BigDecimal> numbers,
            BigDecimal[] temporary,
            int start,
            int middle,
            int end
    ) {
        int left = start;
        int right = middle + 1;
        int position = start;

        while (left <= middle && right <= end) {
            if (numbers.get(left).compareTo(numbers.get(right)) <= 0) {
                temporary[position] = numbers.get(left);
                left++;
            } else {
                temporary[position] = numbers.get(right);
                right++;
            }

            position++;
        }

        while (left <= middle) {
            temporary[position] = numbers.get(left);
            left++;
            position++;
        }

        while (right <= end) {
            temporary[position] = numbers.get(right);
            right++;
            position++;
        }

        for (int i = start; i <= end; i++) {
            numbers.set(i, temporary[i]);
        }
    }
}
