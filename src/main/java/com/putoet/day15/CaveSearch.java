package com.putoet.day15;

import com.putoet.grid.Point;
import org.javatuples.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class CaveSearch {
    public static Optional<CaveNode> bfs(Point initial, Predicate<Point> goalTest, Function<Point, List<Pair<Point, Integer>>> successors) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;

        final Map<Point, Integer> riskLevels = new HashMap<>();
        riskLevels.put(initial, 0);

        final PriorityQueue<CaveNode> frontier = new PriorityQueue<>();
        Optional<CaveNode> finish = Optional.empty();

        frontier.offer(new CaveNode(initial, null, 0));

        while (!frontier.isEmpty()) {
            final CaveNode currentNode = frontier.poll();
            final Point currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                if (finish.isEmpty())
                    finish = Optional.of(currentNode);
                else if (currentNode.totalRisk < finish.get().totalRisk)
                    finish = Optional.of(currentNode);
                continue;
            }

            if (currentNode.totalRisk > riskLevels.get(currentState))
                continue;

            for (Pair<Point, Integer> child : successors.apply(currentState)) {
                final Point point = child.getValue0();
                final int riskLevel = currentNode.totalRisk + child.getValue1();

                if (riskLevels.computeIfAbsent(point, p -> Integer.MAX_VALUE) > riskLevel) {
                    riskLevels.put(point, riskLevel);
                    frontier.offer(new CaveNode(child.getValue0(), currentNode, riskLevel));
                }
            }
        }

        return finish;
    }

    public static Predicate<Point> goalTest(Cave cave) {
        assert cave != null;

        return point -> point.equals(cave.end());
    }

    public static Function<Point, List<Pair<Point, Integer>>> successors(Cave cave) {
        assert cave != null;

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

        public CaveNode(Point state, CaveNode parent, int riskLevel) {
            assert state != null;
            assert riskLevel >= 0;

            this.state = state;
            this.parent = parent;
            this.totalRisk = riskLevel;
        }

        public Point state() {
            return state;
        }

        public int totalRisk() {
            return totalRisk;
        }

        @Override
        public int compareTo(CaveNode other) {
            return Integer.compare(this.totalRisk, other.totalRisk);
        }

        @Override
        public String toString() {
            return String.format("{%s, %s, %d}", state, parent != null ? parent.state : "(no-parent)", totalRisk);
        }
    }
}
