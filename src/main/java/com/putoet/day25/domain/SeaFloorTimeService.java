package com.putoet.day25.domain;

import com.putoet.grid.Point;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public final class SeaFloorTimeService {
    private SeaFloorTimeService() {
    }

    public static Pair<SeaFloor, Integer> advance(SeaFloor seaFloor, int units) {
        if (seaFloor == null)
            throw new IllegalArgumentException("The advance service can only operate on a sea floor instance.");

        if (units <= 0)
            throw new IllegalArgumentException("A SeaFloor can only advance forward, " +
                    "the number of units must be a value greater than 0. " + units + " is an invalid value for units.");

        var pair = Pair.with(seaFloor, 0);
        while (units > 0) {
            pair = moveOneStep(pair.getValue0());
            units--;
        }

        return pair;
    }

    private static Pair<SeaFloor, Integer> moveOneStep(SeaFloor seaFloor) {
        final var step1 = move(Pair.with(seaFloor, 0),
                cucumber -> cucumber.type() == SeaCucumberType.RIGHT,
                (size, point) -> Point.of(point.x() < size.x() - 1 ? point.x() + 1 : 0, point.y())
        );

        return move(step1,
                cucumber -> cucumber.type() == SeaCucumberType.DOWN,
                (size, point) -> Point.of(point.x(), point.y() < size.y() - 1 ? point.y() + 1 : 0)
        );
    }

    private static Pair<SeaFloor, Integer> move(Pair<SeaFloor, Integer> pair,
                                                Predicate<SeaCucumber> predicate, BiFunction<Point, Point, Point> next) {
        final var seaFloor = pair.getValue0();
        int count = pair.getValue1();

        char[][] grid = Arrays.stream(seaFloor.toString().split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);

        final var cucumbers = new ArrayList<SeaCucumber>(seaFloor.cucumbers().size());
        for (var cucumber : seaFloor.cucumbers()) {
            if (predicate.test(cucumber)) {
                final var to = next.apply(seaFloor.size(), cucumber.location());
                if (grid[to.y()][to.x()] == SeaFloor.EMPTY) {
                    cucumbers.add(new SeaCucumber(cucumber.id(), cucumber.type(), to));
                    count++;
                } else
                    cucumbers.add(cucumber);
            } else {
                cucumbers.add(cucumber);
            }
        }

        return Pair.with(new SeaFloor(seaFloor.id(), seaFloor.size(), Collections.unmodifiableList(cucumbers)), count);
    }
}
