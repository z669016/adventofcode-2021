package com.putoet.day3;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

import java.util.List;

public class Day3  {
    public static void main(String[] args) {
        final var diagnostics = ResourceLines.list("/day3.txt");

        Timer.run(() -> part1(diagnostics));
        Timer.run(() -> part2(diagnostics));
    }

    static void part1(List<String> diagnostics) {
        final var gammaRate = Diagnostics.gammaRating(diagnostics);
        final var epsilonRate = Diagnostics.epsilonRating(diagnostics);

        System.out.println("Power consumption is " + (gammaRate * epsilonRate));
    }

    static void part2(List<String> diagnostics) {
        final var oxygenGeneratorRate = Diagnostics.oxygenGeneratorRating(diagnostics);
        final var co2ScrubberRating = Diagnostics.co2ScrubberRating(diagnostics);

        System.out.println("Life support rating is " + (oxygenGeneratorRate * co2ScrubberRating));
    }
}
