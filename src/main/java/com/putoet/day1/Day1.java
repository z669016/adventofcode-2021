package com.putoet.day1;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        final var measurements = ResourceLines.list("/day1.txt", Integer::parseInt);

        Timer.run(() -> System.out.println("Number of increases is " + countIncreases(measurements)));
        Timer.run(() -> System.out.println("Number of 'groups of three' increases is " + countIncreases(groupsOfThree(measurements))));
    }

   static int countIncreases(@NotNull List<Integer> measurements) {
        assert !measurements.isEmpty();

        var count = 0;
        var prev = measurements.get(0);
        for (var x = 1; x < measurements.size(); x++) {
            final var next = measurements.get(x);
            if (next - prev > 0)
                count++;

            prev = next;
        }

        return count;
    }

    static List<Integer> groupsOfThree(@NotNull List<Integer> measurements) {
        assert measurements.size() > 2;

        final var newMeasurements = new ArrayList<Integer>();
        var one = measurements.get(0);
        var two = measurements.get(1);
        for (var x = 2; x < measurements.size(); x++) {
            final var three = measurements.get(x);
            newMeasurements.add(one + two + three);
            one = two;
            two = three;
        }
        return newMeasurements;
    }
}
