package com.putoet.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Diagnostics {
    public static int gammaRating(List<String> diagnostics){
        assertDiagnostics(diagnostics);

        return mostFrequentRating(diagnostics, '1');
    }

    public static int epsilonRating(List<String> diagnostics){
        assertDiagnostics(diagnostics);

        return mostFrequentRating(diagnostics, '0');
    }

    private static int mostFrequentRating(List<String> diagnostics, char toCount){
        final int length = diagnostics.get(0).length();

        int rating = 0;
        for (int x = 0; x < length; x++) {
            rating = (rating << 1) + (mostFrequent(diagnostics, x) == toCount ? 1 : 0);
        }

        return rating;
    }

    private static char mostFrequent(List<String> diagnostics, int x) {
        final long ones = countOnes(diagnostics, x);
        final int size = diagnostics.size();

        if (ones < size / 2.0)
            return '0';
        return '1';
    }

    private static long countOnes(List<String> diagnostics, int x) {
        return diagnostics.stream().map(diagnostic -> diagnostic.charAt(x)).filter(c -> c == '1').count();
    }

    public static int oxygenGeneratorRating(List<String> diagnostics) {
        assertDiagnostics(diagnostics);

        return filterRating(diagnostics, (mostFrequent, charAt) -> mostFrequent != charAt);
    }

    public static int co2ScrubberRating(List<String> diagnostics) {
        assertDiagnostics(diagnostics);

        return filterRating(diagnostics, (mostFrequent, charAt) -> mostFrequent == charAt);
    }

    private static int filterRating(List<String> diagnostics, BiFunction<Character,Character,Boolean> filter) {
        final List<String> values = new ArrayList<>(diagnostics);

        int x = 0;
        while (values.size() > 1 && x < diagnostics.get(0).length()) {
            final int finalX = x;
            values.removeIf(value -> filter.apply(mostFrequent(values, finalX), value.charAt(finalX)));
            x++;
        }

        if (values.size() != 1)
            throw new IllegalStateException("Invalid list filtered " + values);

        return Integer.parseInt(values.get(0), 2);
    }

    private static void assertDiagnostics(List<String> diagnostics) {
        assert diagnostics != null;
        assert !diagnostics.isEmpty();
        assert diagnostics.get(0) != null;
        assert diagnostics.get(0).length() > 0;
    }
}
