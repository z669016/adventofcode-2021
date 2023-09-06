package com.putoet.day3;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

class Diagnostics {
    public static int gammaRating(@NotNull List<String> diagnostics){
        assert !diagnostics.isEmpty();

        return mostFrequentRating(diagnostics, '1');
    }

    public static int epsilonRating(@NotNull List<String> diagnostics){
        assert !diagnostics.isEmpty();

        return mostFrequentRating(diagnostics, '0');
    }

    private static int mostFrequentRating(@NotNull List<String> diagnostics, char toCount){
        final var length = diagnostics.get(0).length();

        var rating = 0;
        for (var x = 0; x < length; x++) {
            rating = (rating << 1) + (mostFrequent(diagnostics, x) == toCount ? 1 : 0);
        }

        return rating;
    }

    private static char mostFrequent(List<String> diagnostics, int x) {
        final var ones = countOnes(diagnostics, x);
        final var size = diagnostics.size();

        if (ones < size / 2.0)
            return '0';
        return '1';
    }

    private static long countOnes(List<String> diagnostics, int x) {
        return diagnostics.stream().map(diagnostic -> diagnostic.charAt(x)).filter(c -> c == '1').count();
    }

    public static int oxygenGeneratorRating(@NotNull List<String> diagnostics) {
        assert !diagnostics.isEmpty();

        return filterRating(diagnostics, (mostFrequent, charAt) -> mostFrequent != charAt);
    }

    public static int co2ScrubberRating(@NotNull List<String> diagnostics) {
        assert !diagnostics.isEmpty();

        return filterRating(diagnostics, (mostFrequent, charAt) -> mostFrequent == charAt);
    }

    private static int filterRating(List<String> diagnostics, BiFunction<Character,Character,Boolean> filter) {
        final var values = new ArrayList<>(diagnostics);

        var x = 0;
        while (values.size() > 1 && x < diagnostics.get(0).length()) {
            final var finalX = x;
            values.removeIf(value -> filter.apply(mostFrequent(values, finalX), value.charAt(finalX)));
            x++;
        }

        if (values.size() != 1)
            throw new IllegalStateException("Invalid list filtered " + values);

        return Integer.parseInt(values.get(0), 2);
    }
}
