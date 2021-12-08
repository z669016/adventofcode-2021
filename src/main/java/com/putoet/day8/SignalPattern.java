package com.putoet.day8;

import java.util.Arrays;
import java.util.List;

public record SignalPattern(List<String> pattern, List<String> displayDigits) {
    public static SignalPattern of(String line) {
        assert line != null;

        final String[] split = line.trim().split(" \\| ");
        if (split.length == 2) {
            final List<String> pattern = Arrays.stream(split[0].trim().split(" ")).toList();
            final List<String> displayDigits = Arrays.stream(split[1].trim().split(" ")).toList();

            if (pattern.size() == 10 && displayDigits.size() == 4)
                return new SignalPattern(pattern, displayDigits);
        }

        throw new IllegalArgumentException("Invalid line format'" + line + "'");
    }
}
