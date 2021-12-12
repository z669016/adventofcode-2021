package com.putoet.day12;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class MazeSeach {
    public record CaveNode(Cave state, MazeSeach.CaveNode parent) {
        public CaveNode {
            assert state != null;
        }

        public boolean visited(Cave cave) {
            assert !state.equals(cave);

            CaveNode node = parent;
            while (node != null) {
                if (node.state.equals(cave))
                    return true;
                node = node.parent;
            }
            return false;
        }

        public Map<Cave, Integer> visitedCount() {
            final Map<Cave, Integer> map = new HashMap<>();

            CaveNode node = this;
            while (node != null) {
                if (!map.containsKey(node.state))
                    map.put(node.state, 1);
                else
                    map.put(node.state, map.get(node.state) + 1);

                node = node.parent;
            }

            return map;
        }
    }

    public static List<CaveNode> paths(Cave initial, Predicate<Cave> goalTest, Function<CaveNode, List<Cave>> successors) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;

        final List<CaveNode> all = new ArrayList<>();
        final Queue<CaveNode> frontier = new LinkedList<>();
        frontier.offer(new CaveNode(initial, null));

        while (!frontier.isEmpty()) {
            final CaveNode currentNode = frontier.poll();
            final Cave currentState = currentNode.state;

            if (goalTest.test(currentState)) {
                all.add(currentNode);
                continue;
            }

            for (Cave child : successors.apply(currentNode)) {
                frontier.offer(new CaveNode(child, currentNode));
            }
        }

        return all;
    }

    public static Predicate<Cave> goalTest(Maze maze) {
        assert maze != null;

        return cave -> cave.equals(maze.end());
    }

    public static List<Cave> successors(MazeSeach.CaveNode node) {
        assert node != null;

        final Cave current = node.state;
        final List<Cave> neighbours = new ArrayList<>(current.neighbours());
        neighbours.removeIf(cave -> "start".equals(cave.name()));
        neighbours.removeIf(cave -> cave.isSmall() && node.visited(cave));
        return neighbours;
    }

    public static List<Cave> successorsOneSmall(MazeSeach.CaveNode node) {
        assert node != null;

        final Map<Cave,Integer> visitedCount = node.visitedCount();
        final Cave current = node.state;
        final List<Cave> neighbours = new ArrayList<>(current.neighbours());
        neighbours.removeIf(cave -> "start".equals(cave.name()));
        neighbours.removeIf(cave -> cave.isSmall() && node.visited(cave) && visitedSmallDouble(visitedCount));
        return neighbours;
    }

    private static boolean visitedSmallDouble(Map<Cave,Integer> visitedCount) {
        for (Cave cave : visitedCount.keySet()) {
            if (cave.isSmall() && visitedCount.get(cave) > 1)
                return true;
        }
        return false;
    }
}
