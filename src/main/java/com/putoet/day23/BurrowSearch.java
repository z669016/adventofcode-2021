package com.putoet.day23;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.*;

class BurrowSearch {
    public static final int ROOM_DISTANCE = 2;
    private static final int HALLWAY_LINE = 1;
    private static final int FINAL_LINE_1 = 2;
    private static final int POD_A_COLUMN = 3;
    private static final int POD_B_COLUMN = POD_A_COLUMN + ROOM_DISTANCE;
    private static final int POD_C_COLUMN = POD_B_COLUMN + ROOM_DISTANCE;
    private static final int POD_D_COLUMN = POD_C_COLUMN + ROOM_DISTANCE;

    public static Optional<BurrowNode> bfs(@NotNull Burrow initial) {
        final var visited = new HashSet<String>();
        final var frontier = new PriorityQueue<BurrowNode>();
        frontier.offer(new BurrowNode(initial, null));

        while (!frontier.isEmpty()) {
            final var currentNode = frontier.poll();
            final var currentState = currentNode.state;

            if (goalTest(currentState))
                return Optional.of(currentNode);

            final var next = successors(currentState);
            for (var child : next) {
                final var state = child.toString();
                if (!visited.contains(state)) {
                    visited.add(state);
                    frontier.offer(new BurrowNode(child, currentNode));
                }
            }
        }

        return Optional.empty();
    }

    public static boolean goalTest(@NotNull Burrow burrow) {
        for (var pod : burrow.pods()) {
            if (pod.location().y() < FINAL_LINE_1)
                return false;

            if (pod.location().x() != expectedRoomForPodType(pod.type()))
                return false;
        }
        return true;
    }

    public static List<Burrow> successors(@NotNull Burrow state) {
        return state.pods().stream()
                .map(pod -> options(state, pod))
                .flatMap(Collection::stream)
                .map(state::move)
                .toList();
    }

    public static List<Amphipod> options(@NotNull Burrow burrow, @NotNull Amphipod pod) {
        var next = pod;

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
        final var target = targetRoomAvailable(burrow, next);
        if (target.isPresent())
            return List.of(target.get());

        // if not moved yet, the pod was in the hallway, so don't move
        if (next.location().equals(pod.location()))
            return List.of();

        // take the options to the left and the right
        final var options = new ArrayList<Amphipod>();
        options.addAll(optionsMove(burrow, next, Point.EAST));
        options.addAll(optionsMove(burrow, next, Point.WEST));

        return options;
    }

    private static List<Amphipod> optionsMove(Burrow burrow, Amphipod next, Point direction) {
        final var options = new ArrayList<Amphipod>();
        while (isOpen(burrow, next.location(), direction)) {
            next = next.move(direction);
            if (canStop(next))
                options.add(next);
        }

        return options;
    }

    protected static boolean inCorrectRoom(@NotNull Burrow burrow, @NotNull Amphipod pod) {
        final var location = pod.location();
        final var room = expectedRoomForPodType(pod.type());

        if (location.x() != room)
            return false;

        if (location.y() == HALLWAY_LINE)
            return false;

        // Check if all pods in this room are the correct type
        return !wrongPodsInRoom(burrow, room);
    }

    protected static boolean wrongPodsInRoom(@NotNull Burrow burrow, int room) {
        return burrow.pods().stream()
                .filter(pod -> pod.location().x() == room)
                .filter(pod -> !inHallway(pod))
                .anyMatch(pod -> pod.type() != expectedTypeForColumn(room));
    }

    protected static Optional<Amphipod> targetRoomAvailable(@NotNull Burrow burrow, Amphipod pod) {
        assert inHallway(pod);

        final var room = expectedRoomForPodType(pod.type());
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

    protected static boolean canStop(@NotNull Amphipod pod) {
        final var x = pod.location().x();
        return (x != POD_A_COLUMN && x != POD_B_COLUMN && x != POD_C_COLUMN && x != POD_D_COLUMN);
    }

    protected static boolean canMove(@NotNull Burrow burrow, @NotNull Amphipod pod) {
        return !inCorrectRoom(burrow, pod) && canGetOut(burrow, pod);
    }

    private static boolean canGetOut(@NotNull Burrow burrow, @NotNull Amphipod pod) {
        final var room = pod.location().x();
        final var left = burrow.podAt(Point.of(room - 1, HALLWAY_LINE));
        if (left.isEmpty())
            return true;

        final var right = burrow.podAt(Point.of(room + 1, HALLWAY_LINE));
        return right.isEmpty();
    }

    protected static boolean notOpen(@NotNull Burrow burrow, @NotNull Point from) {
        return !burrow.isOpen(from);
    }

    protected static boolean isOpen(@NotNull Burrow burrow, @NotNull Point from, @NotNull Point direction) {
        return burrow.isOpen(from.add(direction));
    }

    protected static boolean inHallway(@NotNull Amphipod pod) {
        return pod.location().y() == HALLWAY_LINE;
    }

    protected static int expectedRoomForPodType(@NotNull AmphipodType type) {
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

    public record BurrowNode(@NotNull Burrow state, BurrowNode parent) implements Comparable<BurrowNode> {
        @Override
        public int compareTo(@NotNull BurrowNode other) {
            return Long.compare(state().energyUsed(), other.state.energyUsed());
        }
    }
}
