package com.putoet.day17;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.*;

record ProbeLauncher(@NotNull Point topLeft, @NotNull Point bottomRight) {
    public Optional<Point> highest(List<Point> hits) {
        return hits.stream().max(Comparator.comparing(Point::y));
    }

    public List<Point> hits() {
        final var list = new ArrayList<Point>();

        for (var xDelta = 1; xDelta <= bottomRight.x(); xDelta++) {
            for (var yDelta = bottomRight.y(); yDelta <= Math.abs(bottomRight.y()); yDelta++) {
                if (hits(xDelta, yDelta))
                    list.add(Point.of(xDelta, yDelta));
            }
        }

        return list;
    }

    public boolean hits(int xDelta, int yDelta) {
        var x = 0;
        var y = 0;

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
        var height = 0;
        var yDelta = velocity.y();

        while (yDelta > 0) {
            height += yDelta;
            yDelta -= 1;
        }

        return height;
    }
}
