package com.putoet.day8;

import com.putoet.statistics.Permutator;

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
            new Permutator<Character>().permute(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g')).stream()
                    .map(list -> list.stream().map(Object::toString).collect(Collectors.joining()))
                    .collect(Collectors.toList());

    public static String encoding(List<String> samples) {
        for (String encoding : ENCODINGS) {
            if (samples.stream().allMatch(sample -> decode(encoding, sample).isPresent()))
                return encoding;
        }

        throw new IllegalArgumentException("No encoding found for " + samples);
    }

    public static OptionalInt decode(String encoding, List<String> digits) {
        assert encoding != null;
        assert encoding.length() == 7;
        assert digits != null;
        assert digits.size() == 4;

        int value = 0;
        for (String s : digits) {
            final OptionalInt digit = decode(encoding, s);
            if (digit.isEmpty())
                return OptionalInt.empty();

            value = value * 10 + digit.getAsInt();
        }
        return OptionalInt.of(value);
    }


    public static OptionalInt decode(String encoding, String digit) {
        assert encoding != null;
        assert encoding.length() == 7;
        assert digit != null;

        final Map<String, Integer> mapper = mapper(encoding);
        final Integer decoded = mapper.get(sortedDigits(digit));
        return decoded != null ? OptionalInt.of(decoded) : OptionalInt.empty();
    }

    private static String sortedDigits(String digits) {
        final char[] temp = digits.toCharArray();
        Arrays.sort(temp);
        return String.valueOf(temp);
    }

    public static Map<String, Integer> mapper(String encoding) {
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
        final char[] characters = switch (number) {
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
