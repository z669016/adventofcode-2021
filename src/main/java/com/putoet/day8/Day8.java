package com.putoet.day8;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Day8 extends Day {
    private final List<SignalPattern> signalPatterns;

    protected Day8(String[] args) {
        super(args);

        signalPatterns = ResourceLines.list("/day8.txt").stream()
                .map(SignalPattern::of)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        final Day day = new Day8(args);
        day.challenge();
    }

    public static long countUniqueNumbers(List<String> displayDigits) {
        return displayDigits.stream().filter(Day8::isUniqueNumber).count();
    }

    public static boolean isUniqueNumber(String digit) {
        return digit.length() == 2 ||
                digit.length() == 4 ||
                digit.length() == 3 ||
                digit.length() == 7;
    }

    @Override
    public void part1() {
        System.out.println("The digits 1, 4, 7, or 8 appear " + countUniqueNumbers() + " times.");
    }

    public long countUniqueNumbers() {
        return signalPatterns.stream().map(SignalPattern::displayDigits).mapToLong(Day8::countUniqueNumbers).sum();
    }

    @Override
    public void part2() {
        System.out.println("The sumof the decoded values is " + decode());
    }

    public int decode() {
        return signalPatterns.stream()
                .map(this::decode)
                .filter(OptionalInt::isPresent)
                .mapToInt(OptionalInt::getAsInt)
                .sum();
    }

    public OptionalInt decode(SignalPattern signalPattern) {
        final String encoding = Digits.encoding(signalPattern.pattern());
        return Digits.decode(encoding, signalPattern.displayDigits());
    }

    public List<SignalPattern> signalPatterns() {
        return signalPatterns;
    }
}
