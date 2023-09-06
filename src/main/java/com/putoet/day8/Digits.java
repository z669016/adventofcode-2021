package com.putoet.day8;

import org.jetbrains.annotations.NotNull;
import org.paukov.combinatorics3.Generator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Digits {
    public final static Map<Integer, String> SEGMENTS = Map.of(
            0, "abcefg",
            1, "cf",
            2, "acdeg",
            3, "acdfg",
            4, "bcdf",
            5, "abdfg",
            6, "abdefg",
            7, "acf",
            8, "abcdefg",
            9, "abcdfg"
    );

    public static final List<String> ENCODINGS =
            Generator.permutation('a', 'b', 'c', 'd', 'e', 'f', 'g').simple().stream()
                    .map(list -> list.stream().map(Object::toString).collect(Collectors.joining()))
                    .toList();

    public static String encoding(@NotNull List<String> samples) {
        for (var encoding : ENCODINGS) {
            if (samples.stream().allMatch(sample -> decode(encoding, sample).isPresent()))
                return encoding;
        }

        throw new IllegalArgumentException("No encoding found for " + samples);
    }

    public static OptionalInt decode(@NotNull String encoding, @NotNull List<String> digits) {
        assert encoding.length() == 7;
        assert digits.size() == 4;

        var value = 0;
        for (var s : digits) {
            final var digit = decode(encoding, s);
            if (digit.isEmpty())
                return OptionalInt.empty();

            value = value * 10 + digit.getAsInt();
        }
        return OptionalInt.of(value);
    }


    public static OptionalInt decode(@NotNull String encoding, @NotNull String digit) {
        assert encoding.length() == 7;

        final var mapper = mapper(encoding);
        final var decoded = mapper.get(sortedDigits(digit));
        return decoded != null ? OptionalInt.of(decoded) : OptionalInt.empty();
    }

    private static String sortedDigits(String digits) {
        final var temp = digits.toCharArray();
        Arrays.sort(temp);
        return String.valueOf(temp);
    }

    public static Map<String, Integer> mapper(@NotNull String encoding) {
        return Map.of(
                encodingFor(0, encoding), 0,
                encodingFor(1, encoding), 1,
                encodingFor(2, encoding), 2,
                encodingFor(3, encoding), 3,
                encodingFor(4, encoding), 4,
                encodingFor(5, encoding), 5,
                encodingFor(6, encoding), 6,
                encodingFor(7, encoding), 7,
                encodingFor(8, encoding), 8,
                encodingFor(9, encoding), 9
        );
    }

    private static String encodingFor(int number, String encoding) {
        final var characters = switch (number) {
            case 0 -> new char[]{encoding.charAt(0), encoding.charAt(1), encoding.charAt(2), encoding.charAt(4), encoding.charAt(5), encoding.charAt(6)};
            case 1 -> new char[]{encoding.charAt(2), encoding.charAt(5)};
            case 2 -> new char[]{encoding.charAt(0), encoding.charAt(2), encoding.charAt(3), encoding.charAt(4), encoding.charAt(6)};
            case 3 -> new char[]{encoding.charAt(0), encoding.charAt(2), encoding.charAt(3), encoding.charAt(5), encoding.charAt(6)};
            case 4 -> new char[]{encoding.charAt(1), encoding.charAt(2), encoding.charAt(3), encoding.charAt(5)};
            case 5 -> new char[]{encoding.charAt(0), encoding.charAt(1), encoding.charAt(3), encoding.charAt(5), encoding.charAt(6)};
            case 6 -> new char[]{encoding.charAt(0), encoding.charAt(1), encoding.charAt(3), encoding.charAt(4), encoding.charAt(5), encoding.charAt(6)};
            case 7 -> new char[]{encoding.charAt(0), encoding.charAt(2), encoding.charAt(5)};
            case 8 -> new char[]{encoding.charAt(0), encoding.charAt(1), encoding.charAt(2), encoding.charAt(3), encoding.charAt(4), encoding.charAt(5), encoding.charAt(6)};
            case 9 -> new char[]{encoding.charAt(0), encoding.charAt(1), encoding.charAt(2), encoding.charAt(3), encoding.charAt(5), encoding.charAt(6)};
            default -> throw new IllegalArgumentException("Invalid number for encoding: " + number);
        };

        Arrays.sort(characters);
        return String.valueOf(characters);
    }
}
