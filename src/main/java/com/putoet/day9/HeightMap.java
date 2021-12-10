package com.putoet.day9;

import com.putoet.grid.Point;

import java.util.*;

public record HeightMap(char[][] grid) {

    public static HeightMap of(List<String> map) {
        assert map != null;

        final char[][] grid = new char[map.size()][];

        for (int i = 0; i < map.size(); i++) {
            grid[i] = map.get(i).toCharArray();
        }
        return new HeightMap(grid);
    }

    public List<Point> lowest() {
        final List<Point> lowest = new ArrayList<>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                char valueAt = grid[y][x];
                if (valueAt < valueAtLowestAdjacent(x, y))
                    lowest.add(Point.of(x, y));
            }
        }
        return lowest;
    }

    private char valueAtLowestAdjacent(int x, int y) {
        int lowest = Integer.MAX_VALUE;
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

    public int riskLevel(List<Point> points) {
        return points.stream().mapToInt(this::riskLevel).sum();
    }

    public int riskLevel(Point point) {
        contains(point);

        return grid[point.y()][point.x()] - '0' + 1;
    }

    private void contains(Point point) {
        assert point != null;
        assert point.y() >= 0;
        assert point.x() >= 0;
        assert point.y() < grid.length;
        assert point.x() < grid[point.y()].length;
    }

    public Set<Point> basin(Point lowest) {
        contains(lowest);

        final Set<Point> basin = new HashSet<>();
        final Queue<Point> queue = new LinkedList<>();

        queue.offer(lowest);
        while (!queue.isEmpty()) {
            final Point point = queue.poll();
            if (grid[point.y()][point.x()] < '9') {
                basin.add(point);

                if (point.x() > 0) {
                    final Point next = Point.of(point.x() - 1, point.y());
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.x() + 1 < grid[point.y()].length) {
                    final Point next = Point.of(point.x() + 1, point.y());
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.y() > 0) {
                    final Point next = Point.of(point.x(), point.y() - 1);
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.y() + 1 < grid.length) {
                    final Point next = Point.of(point.x(), point.y() + 1);
                    if (!basin.contains(next))
                        queue.offer(next);
                }
            }
        }

        return basin;
    }

    public List<Set<Point>> largestBasins() {
        final List<Point> lowest = lowest();
        final List<Set<Point>> basins = new ArrayList<>(lowest.stream().map(this::basin).toList());
        basins.sort(Comparator.comparing(Set::size));

        while (basins.size() > 3)
            basins.remove(0);

        return basins;
    }
}
