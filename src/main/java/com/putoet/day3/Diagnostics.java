package com.putoet.day3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Diagnostics {
    private static int count(List<String> diagnostics, int score){
        assert diagnostics != null;
        assert !diagnostics.isEmpty();
        assert diagnostics.get(0) != null;
        assert diagnostics.get(0).length() > 0;

        final int notScore = score == 1 ? 0 : 1;

        final int size = diagnostics.size();
        final int length = diagnostics.get(0).length();
        int rate = 0;

        for (int x = 0; x < length; x++) {
            rate = (rate << 1) + (countOnes(diagnostics, x) > size / 2 ? score : notScore);
        }

        return rate;
    }

    private static long countOnes(List<String> diagnostics, int x) {
        return diagnostics.stream().map(diagnostic -> diagnostic.charAt(x)).filter(c -> c == '1').count();
    }

    private static char mostFrequent(List<String> diagnostics, int x) {
        final long ones = countOnes(diagnostics, x);
        final int size = diagnostics.size();

        if (ones > size / 2.0)
            return '1';
        if (ones < size / 2.0)
            return '0';
        return '1';
    }

    public static int gammaRate(List<String> diagnostics){
        return count(diagnostics, 1);
    }

    public static int epsilonRate(List<String> diagnostics){
        return count(diagnostics, 0);
    }

    public static int countFilter(List<String> diagnostics, BiFunction<Character,Character,Boolean> filter) {
        final List<String> values = new ArrayList<>(diagnostics);

        int x = 0;
        while (values.size() > 1 && x < diagnostics.get(0).length()) {
            int finalX = x;
            final char mostFrequent = mostFrequent(values, x);
            values.removeIf(value -> filter.apply(mostFrequent, value.charAt(finalX)));
            x++;
        }

        if (values.size() != 1)
            throw new IllegalStateException("Invalid list calculated " + values);

        return Integer.parseInt(values.get(0), 2);
    }


    public static int oxygenGeneratorRate(List<String> diagnostics) {
        return countFilter(diagnostics, (mostFrequent, charAt) -> mostFrequent != charAt);
    }

    public static int co2ScrubberRating(List<String> diagnostics) {
        return countFilter(diagnostics, (mostFrequent, charAt) -> mostFrequent == charAt);

    }
}
