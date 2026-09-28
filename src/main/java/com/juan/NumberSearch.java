package com.juan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NumberSearch {

    /**
     * Devuelve todas las posiciones, empezando desde 1, donde aparece el valor.
     * Compara los valores numéricamente, sin que importe su escala decimal.
     *
     * @param numbers lista donde se buscará
     * @param target número que se busca
     * @return posiciones encontradas; lista vacía si no aparece
     */
    public List<Integer> findPositions(
            List<BigDecimal> numbers,
            BigDecimal target
    ) {
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < numbers.size(); i++) {
            // Comparamos el valor numérico sin considerar cuántos decimales tiene cada BigDecimal.
            // No sería apropiado usar equals (5.00 y 5 serían tomados como falsos)
            // ni == (se comprobaría si ambas variables apuntan al mismo objeto, no si contienen el mismo valor numérico)
            if (numbers.get(i).compareTo(target) == 0) {
                positions.add(i + 1);
            }
        }

        return positions;
    }
}
