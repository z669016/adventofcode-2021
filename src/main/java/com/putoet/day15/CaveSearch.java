package com.putoet.day15;

import com.putoet.grid.Point;
import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

class CaveSearch {
    public static Optional<CaveNode> bfs(
            @NotNull Point initial,
            @NotNull Predicate<Point> goalTest,
            @NotNull Function<Point, List<Pair<Point, Integer>>> successors
    ) {
        final var riskLevels = new HashMap<Point, Integer>();
        riskLevels.put(initial, 0);

        final var frontier = new PriorityQueue<CaveNode>();
        Optional<CaveNode> finish = Optional.empty();

        frontier.offer(new CaveNode(initial, null, 0));
        while (!frontier.isEmpty()) {
            final var currentNode = frontier.poll();
            final var currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                if (finish.isEmpty())
                    finish = Optional.of(currentNode);
                else if (currentNode.totalRisk < finish.get().totalRisk)
                    finish = Optional.of(currentNode);
                continue;
            }

            if (currentNode.totalRisk > riskLevels.get(currentState))
                continue;

            for (var child : successors.apply(currentState)) {
                final var point = child.getValue0();
                final var riskLevel = currentNode.totalRisk + child.getValue1();

                if (riskLevels.computeIfAbsent(point, p -> Integer.MAX_VALUE) > riskLevel) {
                    riskLevels.put(point, riskLevel);
                    frontier.offer(new CaveNode(child.getValue0(), currentNode, riskLevel));
                }
            }
        }

        return finish;
    }

    public static Predicate<Point> goalTest(@NotNull Cave cave) {
        return point -> point.equals(cave.end());
    }

    public static Function<Point, List<Pair<Point, Integer>>> successors(@NotNull Cave cave) {
        return (Point point) -> Point.directions(true).stream()
                .map(point::add)
                .filter(cave::contains)
                .map(p -> Pair.with(p, cave.riskLevel(p)))
                .toList();
    }

    public static class CaveNode implements Comparable<CaveNode> {
        private final Point state;
        private final CaveNode parent;
        private final int totalRisk;

        public CaveNode(@NotNull Point state, CaveNode parent, int riskLevel) {
            assert riskLevel >= 0;

            this.state = state;
            this.parent = parent;
            this.totalRisk = riskLevel;
        }

        public int totalRisk() {
            return totalRisk;
        }

        @Override
        public int compareTo(@NotNull CaveNode other) {
            return Integer.compare(this.totalRisk, other.totalRisk);
        }

        @Override
        public String toString() {
            return String.format("{%s, %s, %d}", state, parent != null ? parent.state : "(no-parent)", totalRisk);
        }
    }
}
