package com.putoet.day1;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends Day {
    private final List<Integer> measurements;

    public Day1(String[] args) {
        super(args);
        this.measurements = ResourceLines.intList("/day1.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day1(args);
        day.challenge();
    }

    public static int countIncreases(List<Integer> measurements) {
        assert measurements != null;
        assert !measurements.isEmpty();

        int count = 0;
        int prev = measurements.get(0);
        for (int x = 1; x < measurements.size(); x++) {
            final int next = measurements.get(x);
            if (next - prev > 0)
                count++;

            prev = next;
        }

        return count;
    }

    public static List<Integer> groupsOfThree(List<Integer> measurements) {
        assert measurements != null;
        assert measurements.size() > 2;

        final List<Integer> newMeasurements = new ArrayList<>();
        int one = measurements.get(0);
        int two = measurements.get(1);
        for (int x = 2; x < measurements.size(); x++) {
            final int three = measurements.get(x);
            newMeasurements.add(one + two + three);
            one = two;
            two = three;
        }
        return newMeasurements;
    }

    public void part1() {
        System.out.println("Number of increases is " + countIncreases(measurements));
    }

    public void part2() {
        System.out.println("Number of 'groups of three' increases is " + countIncreases(groupsOfThree(measurements)));
    }
}
