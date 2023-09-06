package com.putoet.day9;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.*;

record HeightMap(char[][] grid) {

    public static HeightMap of(@NotNull List<String> map) {
        final var grid = new char[map.size()][];

        for (var i = 0; i < map.size(); i++) {
            grid[i] = map.get(i).toCharArray();
        }
        return new HeightMap(grid);
    }

    public List<Point> lowest() {
        final var lowest = new ArrayList<Point>();

        for (var y = 0; y < grid.length; y++) {
            for (var x = 0; x < grid[y].length; x++) {
                final var valueAt = grid[y][x];
                if (valueAt < valueAtLowestAdjacent(x, y))
                    lowest.add(Point.of(x, y));
            }
        }
        return lowest;
    }

    private char valueAtLowestAdjacent(int x, int y) {
        var lowest = Integer.MAX_VALUE;
        if (x > 0)
            lowest = Math.min(lowest, grid[y][x - 1]);
        if (x + 1 < grid[y].length)
            lowest = Math.min(lowest, grid[y][x + 1]);
        if (y > 0)
            lowest = Math.min(lowest, grid[y - 1][x]);
        if (y + 1 < grid.length)
            lowest = Math.min(lowest, grid[y + 1][x]);

        return (char) lowest;
    }

    public int riskLevel(@NotNull List<Point> points) {
        return points.stream().mapToInt(this::riskLevel).sum();
    }

    public int riskLevel(@NotNull Point point) {
        contains(point);

        return grid[point.y()][point.x()] - '0' + 1;
    }

    private void contains(Point point) {
        assert point.y() >= 0;
        assert point.x() >= 0;
        assert point.y() < grid.length;
        assert point.x() < grid[point.y()].length;
    }

    public Set<Point> basin(@NotNull Point lowest) {
        contains(lowest);

        final var basin = new HashSet<Point>();
        final var queue = new LinkedList<Point>();

        queue.offer(lowest);
        while (!queue.isEmpty()) {
            final Point point = queue.poll();
            if (grid[point.y()][point.x()] < '9') {
                basin.add(point);

                if (point.x() > 0) {
                    final var next = Point.of(point.x() - 1, point.y());
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.x() + 1 < grid[point.y()].length) {
                    final var next = Point.of(point.x() + 1, point.y());
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.y() > 0) {
                    final var next = Point.of(point.x(), point.y() - 1);
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.y() + 1 < grid.length) {
                    final var next = Point.of(point.x(), point.y() + 1);
                    if (!basin.contains(next))
                        queue.offer(next);
                }
            }
        }

        return basin;
    }

    public List<Set<Point>> largestBasins() {
        final var lowest = lowest();
        final var basins = new ArrayList<>(lowest.stream().map(this::basin).toList());
        basins.sort(Comparator.comparing(Set::size));

        while (basins.size() > 3)
            basins.remove(0);

        return basins;
    }
}
