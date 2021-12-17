package com.putoet.day17;

import com.putoet.grid.Point;

import java.util.*;

public record ProbeLauncher(Point topLeft, Point bottomRight) {
    public Optional<Point> highest(List<Point> hits) {
        return hits.stream().max(Comparator.comparing(Point::y));
    }

    public List<Point> hits() {
        final List<Point> list = new ArrayList<>();

        for (int xDelta = 1; xDelta <= bottomRight.x(); xDelta++) {
            for (int yDelta = bottomRight.y(); yDelta <= Math.abs(bottomRight.y()); yDelta++) {
                if (hits(xDelta, yDelta))
                    list.add(Point.of(xDelta, yDelta));
            }
        }

        return list;
    }

    public boolean hits(int xDelta, int yDelta) {
        int x = 0;
        int y = 0;

        while (x < topLeft.x() && y > topLeft.y()) {
            x += xDelta;
            y += yDelta;

            xDelta = xDelta + Integer.compare(0, xDelta);
            yDelta = yDelta - 1;
        }

        while (x <= bottomRight.x() && y >= bottomRight.y()) {
            if (x >= topLeft.x() && y <= topLeft.y())
                return true;

            x += xDelta;
            y += yDelta;

            xDelta = xDelta + Integer.compare(0, xDelta);
            yDelta = yDelta - 1;
        }

        return false;
    }

    public int maxHeight(Point velocity) {
        int height = 0;
        int yDelta = velocity.y();

        while (yDelta > 0) {
            height += yDelta;
            yDelta -= 1;
        }

        return height;
    }
}
