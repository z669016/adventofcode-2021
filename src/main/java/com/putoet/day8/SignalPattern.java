package com.putoet.day8;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

record SignalPattern(@NotNull List<String> pattern, @NotNull List<String> displayDigits) {
    public static SignalPattern of(@NotNull String line) {
        final var split = line.trim().split(" \\| ");
        if (split.length == 2) {
            final var pattern = Arrays.stream(split[0].trim().split(" ")).toList();
            final var displayDigits = Arrays.stream(split[1].trim().split(" ")).toList();

            if (pattern.size() == 10 && displayDigits.size() == 4)
                return new SignalPattern(pattern, displayDigits);
        }

        throw new IllegalArgumentException("Invalid line format'" + line + "'");
    }
}
