package com.putoet.day9;

import com.putoet.grid.Grid;
import com.putoet.grid.Point;

import java.util.*;

public record HeightMap(Grid grid) {

    public static HeightMap of(List<String> map) {
        assert map != null;

        final char[][] grid = new char[map.size()][];

        for (int i = 0; i < map.size(); i++) {
            grid[i] = map.get(i).toCharArray();
        }
        return new HeightMap(new Grid(grid));
    }

    public List<Point> lowest() {
        final List<Point> lowest = new ArrayList<>();

        for (int y = 0; y < grid.maxY(); y++) {
            for (int x = 0; x < grid.maxX(); x++) {
                char valueAt = grid.get(x, y);
                if (valueAt < valueAtLowestAdjacent(x, y))
                    lowest.add(Point.of(x, y));
            }
        }
        return lowest;
    }

    private char valueAtLowestAdjacent(int x, int y) {
        int lowest = Integer.MAX_VALUE;
        if (x > 0)
            lowest = Math.min(lowest, grid.get(x - 1, y));
        if (x + 1 < grid.maxX())
            lowest = Math.min(lowest, grid.get(x + 1, y));
        if (y > 0)
            lowest = Math.min(lowest, grid.get(x, y - 1));
        if (y + 1 < grid.maxY())
            lowest = Math.min(lowest, grid.get(x, y + 1));

        return (char) lowest;
    }

    public int riskLevel(List<Point> points) {
        return points.stream().mapToInt(this::riskLevel).sum();
    }

    public int riskLevel(Point point) {
        assert grid.contains(point.x(), point.y());

        return grid.get(point.x(), point.y()) - '0' + 1;
    }

    public Set<Point> basin(Point lowest) {
        final Set<Point> basin = new HashSet<>();
        final Queue<Point> queue = new LinkedList<>();

        queue.offer(lowest);
        while (!queue.isEmpty()) {
            final Point point = queue.poll();
            if (grid.get(point.x(), point.y()) < '9') {
                basin.add(point);

                if (point.x() > 0) {
                    final Point next = Point.of(point.x() - 1, point.y());
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.x() + 1 < grid.maxX()) {
                    final Point next = Point.of(point.x() + 1, point.y());
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.y() > 0) {
                    final Point next = Point.of(point.x(), point.y() - 1);
                    if (!basin.contains(next))
                        queue.offer(next);
                }
                if (point.y() + 1 < grid.maxY()) {
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
