package com.putoet.day5;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public record OceanFloor(Grid grid, List<Vent> vents) {
    public OceanFloor {
        assert grid != null;
        assert vents != null;
    }

    public static OceanFloor of(List<String> input, Predicate<Vent> filter) {
        assert input != null;

        final List<Vent> vents = Vent.of(input);
        final char[][] grid = initGrid(vents, filter);

        return new OceanFloor(new Grid(grid), vents);
    }

    private static char[][] initGrid(List<Vent> vents, Predicate<Vent> filter) {
        final char[][] grid = grid(vents);

        for (Vent vent : vents) {
            if (filter.test(vent)) {
                final Point direction = direction(vent);

                Point point = vent.start();
                while (!point.equals(vent.end())) {
                    map(grid, point);
                    point = point.add(direction);
                }
                map(grid, point);
            }
        }

        return grid;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static char[][] grid(List<Vent> vents) {
        final int maxX = vents.stream().mapToInt(vent -> Math.max(vent.start().x(), vent.end().x())).max().getAsInt();
        final int maxY = vents.stream().mapToInt(vent -> Math.max(vent.start().y(), vent.end().y())).max().getAsInt();

        final char[][] grid = new char[maxY + 1][maxX + 1];
        for (char[] row : grid) {
            Arrays.fill(row, '.');
        }
        return grid;
    }

    private static void map(char[][] grid, Point point) {
        if (grid[point.y()][point.x()] == '.')
            grid[point.y()][point.x()] = '0';

        grid[point.y()][point.x()] = (char) (grid[point.y()][point.x()] + 1);
    }

    private static Point direction(Vent vent) {
        final Point direction;
        if (vent.start().y() > vent.end().y()) {
            if (vent.start().x() < vent.end().x())
                direction = Point.SOUTH_EAST;
            else if (vent.start().x() == vent.end().x())
                direction = Point.SOUTH;
            else
                direction = Point.SOUTH_WEST;
        }
        else if (vent.start().y() == vent.end().y()) {
            if (vent.start().x() < vent.end().x())
                direction = Point.EAST;
            else
                direction = Point.WEST;
        }
        else {
            if (vent.start().x() < vent.end().x())
                direction = Point.NORTH_EAST;
            else if (vent.start().x() == vent.end().x())
                direction = Point.NORTH;
            else
                direction = Point.NORTH_WEST;
        }
        return direction;
    }

    public long overlap() {
        return grid.count(c -> c > '1');
    }

    @Override
    public String toString() {
        return grid.toString();
    }
}
