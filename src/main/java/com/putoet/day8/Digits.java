package com.putoet.day8;

import com.putoet.statistics.Permutator;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.Collectors;

public class Digits {
    public final static Map<Integer, Set<Character>> SEGMENTS = Map.of(
            0, Set.of('a', 'b', 'c', 'e','f','g'),
            1, Set.of('c', 'f'),
            2, Set.of('a', 'c', 'd', 'e', 'g'),
            3, Set.of('a', 'c', 'd', 'f', 'g'),
            4, Set.of('b', 'c', 'd', 'f'),
            5, Set.of('a', 'b', 'd', 'f', 'g'),
            6, Set.of('a', 'b', 'd', 'e', 'f', 'g'),
            7, Set.of('a', 'c', 'f'),
            8, Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g'),
            9, Set.of('a', 'b', 'c', 'd', 'f', 'g')
    );

    public static final List<List<Character>> ENCODINGS =
            new Permutator<Character>().permute(List.of('a', 'b', 'c', 'd', 'e', 'f', 'g'));
    
    public static List<Character> encoding(List<String> samples) {
        for (List<Character> encoding : ENCODINGS) {
            if (samples.stream().allMatch(sample -> decode(encoding, sample).isPresent()))
                return encoding;
        }

        throw new IllegalArgumentException("No encoding found for " + samples);
    }

    public static OptionalInt decode(List<Character> encoding, List<String> digits) {
        assert encoding != null;
        assert encoding.size() == 7;
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


    public static OptionalInt decode(List<Character> encoding, String digit) {
        assert encoding != null;
        assert encoding.size() == 7;
        assert digit != null;

        final Set<Character> digitAsList = digit.chars().mapToObj(i -> (char) i).collect(Collectors.toSet());
        final Map<Set<Character>, Integer> mapper = mapper(encoding);
        final Integer decoded = mapper.get(digitAsList);
        return decoded != null ? OptionalInt.of(decoded) : OptionalInt.empty();
    }

    public static Map<Set<Character>,Integer> mapper(List<Character> encoding) {
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

    private static Set<Character> encodingFor(int number, List<Character> encoding) {
        return switch (number) {
            case 0 -> Set.of(encoding.get(0), encoding.get(1), encoding.get(2), encoding.get(4), encoding.get(5), encoding.get(6));
            case 1 -> Set.of(encoding.get(2), encoding.get(5));
            case 2 -> Set.of(encoding.get(0), encoding.get(2), encoding.get(3), encoding.get(4), encoding.get(6));
            case 3 -> Set.of(encoding.get(0), encoding.get(2), encoding.get(3), encoding.get(5), encoding.get(6));
            case 4 -> Set.of(encoding.get(1), encoding.get(2), encoding.get(3), encoding.get(5));
            case 5 -> Set.of(encoding.get(0), encoding.get(1), encoding.get(3), encoding.get(5), encoding.get(6));
            case 6 -> Set.of(encoding.get(0), encoding.get(1), encoding.get(3), encoding.get(4), encoding.get(5), encoding.get(6));
            case 7 -> Set.of(encoding.get(0), encoding.get(2), encoding.get(5));
            case 8 -> Set.of(encoding.get(0), encoding.get(1), encoding.get(2), encoding.get(3), encoding.get(4), encoding.get(5), encoding.get(6));
            case 9 -> Set.of(encoding.get(0), encoding.get(1), encoding.get(2), encoding.get(3), encoding.get(5), encoding.get(6));
            default -> throw new IllegalArgumentException("Invalid number for encoding: " + number);
        };
    }
}
