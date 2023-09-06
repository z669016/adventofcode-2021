package com.putoet.day12;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

class MazeSearch {
    record CaveNode(@NotNull Cave state, CaveNode parent) {
        public boolean visited(Cave cave) {
            assert !state.equals(cave);

            var node = parent;
            while (node != null) {
                if (node.state.equals(cave))
                    return true;
                node = node.parent;
            }
            return false;
        }

        public Map<Cave, Integer> visitedCount() {
            final var map = new HashMap<Cave, Integer>();

            var node = this;
            while (node != null) {
                map.put(node.state, map.getOrDefault(node.state, 0) + 1);
                node = node.parent;
            }

            return map;
        }
    }

    public static List<CaveNode> paths(
            @NotNull Cave initial,
            @NotNull Predicate<Cave> goalTest,
            @NotNull Function<CaveNode, List<Cave>> successors
    ) {
        final var all = new ArrayList<CaveNode>();
        final var frontier = new LinkedList<CaveNode>();
        frontier.offer(new CaveNode(initial, null));

        while (!frontier.isEmpty()) {
            final var currentNode = frontier.poll();
            final var currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                all.add(currentNode);
                continue;
            }

            for (var child : successors.apply(currentNode)) {
                frontier.offer(new CaveNode(child, currentNode));
            }
        }

        return all;
    }

    public static Predicate<Cave> goalTest(@NotNull Maze maze) {
        return cave -> cave.equals(maze.end());
    }

    public static List<Cave> successors(@NotNull CaveNode node) {
        final var current = node.state;
        final var neighbours = new ArrayList<>(current.neighbours());
        neighbours.removeIf(cave -> "start".equals(cave.name()));
        neighbours.removeIf(cave -> cave.isSmall() && node.visited(cave));
        return neighbours;
    }

    public static List<Cave> successorsOneSmall(@NotNull CaveNode node) {
        final var visitedCount = node.visitedCount();
        final var current = node.state;
        final var neighbours = new ArrayList<>(current.neighbours());
        neighbours.removeIf(cave -> "start".equals(cave.name()));
        neighbours.removeIf(cave -> cave.isSmall() && node.visited(cave) && visitedSmallDouble(visitedCount));
        return neighbours;
    }

    private static boolean visitedSmallDouble(Map<Cave, Integer> visitedCount) {
        for (var cave : visitedCount.keySet()) {
            if (cave.isSmall() && visitedCount.get(cave) > 1)
                return true;
        }
        return false;
    }
}
