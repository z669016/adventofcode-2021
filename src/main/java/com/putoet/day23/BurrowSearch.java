package com.putoet.day23;

import com.putoet.grid.Point;

import java.util.*;

public class BurrowSearch {
    public static final int ROOM_DISTANCE = 2;
    private static final int HALLWAY_LINE = 1;
    private static final int FINAL_LINE_1 = 2;
    private static final int POD_A_COLUMN = 3;
    private static final int POD_B_COLUMN = POD_A_COLUMN + ROOM_DISTANCE;
    private static final int POD_C_COLUMN = POD_B_COLUMN + ROOM_DISTANCE;
    private static final int POD_D_COLUMN = POD_C_COLUMN + ROOM_DISTANCE;

    public static Optional<BurrowNode> bfs(Burrow initial) {
        assert initial != null;

        final Set<String> visited = new HashSet<>();
        final PriorityQueue<BurrowNode> frontier = new PriorityQueue<>();
        frontier.offer(new BurrowNode(initial, null));

        while (!frontier.isEmpty()) {
            final BurrowNode currentNode = frontier.poll();
            final Burrow currentState = currentNode.state;

            if (goalTest(currentState))
                return Optional.of(currentNode);

            final List<Burrow> next = successors(currentState);
            for (Burrow child : next) {
                final String state = child.toString();
                if (!visited.contains(state)) {
                    visited.add(state);
                    frontier.offer(new BurrowNode(child, currentNode));
                }
            }
        }

        return Optional.empty();
    }

    public static boolean goalTest(Burrow burrow) {
        assert burrow != null;

        for (Amphipod pod : burrow.pods()) {
            if (pod.location().y() < FINAL_LINE_1)
                return false;

            if (pod.location().x() != expectedRoomForPodType(pod.type()))
                return false;
        }
        return true;
    }

    public static List<Burrow> successors(Burrow state) {
        assert state != null;

        return state.pods().stream()
                .map(pod -> options(state, pod))
                .flatMap(Collection::stream)
                .map(state::move)
                .toList();
    }

    public static List<Amphipod> options(Burrow burrow, Amphipod pod) {
        Amphipod next = pod;

        if (!inHallway(next)) {
            // if the pod can't move, fine
            if (!canMove(burrow, next))
                return List.of();

            // move up to the hallway
            while (isOpen(burrow, next.location(), Point.SOUTH)) {
                next = next.move(Point.SOUTH);
            }
        }

        // when not in the hallway, no options are left
        if (!inHallway(next))
            return List.of();

        // when you can move to the target room, do so
        final Optional<Amphipod> target = targetRoomAvailable(burrow, next);
        if (target.isPresent())
            return List.of(target.get());

        // if not moved yet, the pod was in the hallway, so don't move
        if (next.location().equals(pod.location()))
            return List.of();

        // take the options to the left and the right
        final List<Amphipod> options = new ArrayList<>();
        options.addAll(optionsMove(burrow, next, Point.EAST));
        options.addAll(optionsMove(burrow, next, Point.WEST));

        return options;
    }

    private static List<Amphipod> optionsMove(Burrow burrow, Amphipod next, Point direction) {
        final List<Amphipod> options = new ArrayList<>();
        while (isOpen(burrow, next.location(), direction)) {
            next = next.move(direction);
            if (canStop(next))
                options.add(next);
        }

        return options;
    }

    protected static boolean inCorrectRoom(Burrow burrow, Amphipod pod) {
        final Point location = pod.location();
        final int room = expectedRoomForPodType(pod.type());

        if (location.x() != room)
            return false;

        if (location.y() == HALLWAY_LINE)
            return false;

        // Check if all pods in this room are the correct type
        return !wrongPodsInRoom(burrow, room);
    }

    protected static boolean wrongPodsInRoom(Burrow burrow, int room) {
        return burrow.pods().stream()
                .filter(pod -> pod.location().x() == room)
                .filter(pod -> !inHallway(pod))
                .anyMatch(pod -> pod.type() != expectedTypeForColumn(room));
    }

    protected static Optional<Amphipod> targetRoomAvailable(Burrow burrow, Amphipod pod) {
        assert inHallway(pod);

        final int room = expectedRoomForPodType(pod.type());
        if (wrongPodsInRoom(burrow, room))
            return Optional.empty();

        while (pod.location().x() > room) {
            pod = pod.move(Point.WEST);
            if (notOpen(burrow, pod.location())) {
                return Optional.empty();
            }
        }

        while (pod.location().x() < room) {
            pod = pod.move(Point.EAST);
            if (notOpen(burrow, pod.location()))
                return Optional.empty();
        }

        pod = pod.move(Point.NORTH);
        while (isOpen(burrow, pod.location(), Point.NORTH))
            pod = pod.move(Point.NORTH);

        return Optional.of(pod);
    }

    protected static boolean canStop(Amphipod pod) {
        final int x = pod.location().x();
        return (x != POD_A_COLUMN && x != POD_B_COLUMN && x != POD_C_COLUMN && x != POD_D_COLUMN);
    }

    protected static boolean canMove(Burrow burrow, Amphipod pod) {
        return !inCorrectRoom(burrow, pod) && canGetOut(burrow, pod);
    }

    private static boolean canGetOut(Burrow burrow, Amphipod pod) {
        final int room = pod.location().x();
        final Optional<Amphipod> left = burrow.podAt(Point.of(room - 1, HALLWAY_LINE));
        if (left.isEmpty())
            return true;

        final Optional<Amphipod> right = burrow.podAt(Point.of(room + 1, HALLWAY_LINE));
        return right.isEmpty();
    }

    protected static boolean notOpen(Burrow burrow, Point from) {
        return !burrow.isOpen(from);
    }

    protected static boolean isOpen(Burrow burrow, Point from, Point direction) {
        return burrow.isOpen(from.add(direction));
    }

    protected static boolean inHallway(Amphipod pod) {
        return pod.location().y() == HALLWAY_LINE;
    }

    protected static int expectedRoomForPodType(AmphipodType type) {
        return switch (type) {
            case AMBER -> POD_A_COLUMN;
            case BRONZE -> POD_B_COLUMN;
            case COPPER -> POD_C_COLUMN;
            case DESERT -> POD_D_COLUMN;
        };
    }

    protected static AmphipodType expectedTypeForColumn(int room) {
        return switch (room) {
            case POD_A_COLUMN -> AmphipodType.AMBER;
            case POD_B_COLUMN -> AmphipodType.BRONZE;
            case POD_C_COLUMN -> AmphipodType.COPPER;
            case POD_D_COLUMN -> AmphipodType.DESERT;
            default -> throw new IllegalArgumentException("Invalid room number " + room);
        };
    }

    public record BurrowNode(Burrow state, BurrowNode parent) implements Comparable<BurrowNode> {
        public BurrowNode {
            assert state != null;
        }

        @Override
        public int compareTo(BurrowNode other) {
            return Long.compare(state().energyUsed(), other.state.energyUsed());
        }
    }
}
