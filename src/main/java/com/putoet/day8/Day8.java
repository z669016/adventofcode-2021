package com.putoet.day8;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;
import java.util.OptionalInt;

public class Day8 {
    public static void main(String[] args) {
        final var signalPatterns = ResourceLines.list("/day8.txt").stream()
                .map(SignalPattern::of)
                .toList();

        Timer.run(() -> part1(signalPatterns));
        Timer.run(() -> part2(signalPatterns));
    }

    static void part1(List<SignalPattern> signalPatterns) {
        System.out.println("The digits 1, 4, 7, or 8 appear " + countUniqueSignalPatterns(signalPatterns) + " times.");
    }

    static long countUniqueSignalPatterns(List<SignalPattern> signalPatterns) {
        return signalPatterns.stream().map(SignalPattern::displayDigits).mapToLong(Day8::countUniqueNumbers).sum();
    }


    static long countUniqueNumbers(List<String> numbers) {
        return numbers.stream().filter(Day8::isUniqueNumber).count();
    }

    static boolean isUniqueNumber(String digit) {
        return digit.length() == 2 ||
               digit.length() == 4 ||
               digit.length() == 3 ||
               digit.length() == 7;
    }

    static void part2(List<SignalPattern> signalPatterns) {
        System.out.println("The sum of the decoded values is " + decode(signalPatterns));
    }

    static int decode(List<SignalPattern> signalPatterns) {
        return signalPatterns.stream()
                .map(Day8::decode)
                .filter(OptionalInt::isPresent)
                .mapToInt(OptionalInt::getAsInt)
                .sum();
    }

    static OptionalInt decode(SignalPattern signalPattern) {
        final var encoding = Digits.encoding(signalPattern.pattern());
        return Digits.decode(encoding, signalPattern.displayDigits());
    }
}
