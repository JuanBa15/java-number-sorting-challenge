package com.juan;

import java.math.BigDecimal;
import java.util.List;

// Define el contrato común de los algoritmos de ordenamiento.
public interface SortingAlgorithm {

    /**
     * Devuelve una copia de los números en orden ascendente.
     * La lista recibida no debe modificarse.
     *
     * @param numbers números que se ordenarán
     * @return una nueva lista ordenada de menor a mayor
     */
    List<BigDecimal> sort(List<BigDecimal> numbers);
}
