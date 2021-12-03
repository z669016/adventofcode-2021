package com.putoet.day3;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day3 extends Day {
    private final List<String> diagnostics;

    protected Day3(String[] args) {
        super(args);
        diagnostics = ResourceLines.list("/day3.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day3(args);
        day.challenge(args);
    }

    @Override
    public void part1() {
        final int gammaRate = Diagnostics.gammaRating(diagnostics);
        final int epsilonRate = Diagnostics.epsilonRating(diagnostics);

        System.out.println("Gamma rate is " + gammaRate);
        System.out.println(("Epsilon rate is " + epsilonRate));
        System.out.println("Power consumption is " + (gammaRate * epsilonRate));
    }

    @Override
    public void part2() {
        final int oxygenGeneratorRate = Diagnostics.oxygenGeneratorRating(diagnostics);
        final int co2ScrubberRating = Diagnostics.co2ScrubberRating(diagnostics);

        System.out.println("Oxygen rate is " + oxygenGeneratorRate);
        System.out.println(("CO2 scrubbing rate is " + co2ScrubberRating));
        System.out.println("Life support rating is " + (oxygenGeneratorRate * co2ScrubberRating));
    }
}
