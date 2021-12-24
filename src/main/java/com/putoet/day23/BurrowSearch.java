package com.putoet.day23;

import com.putoet.grid.Point;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


public class BurrowSearch {
    private static final int HALLWAY_LINE = 1;
    private static final int FINAL_LINE_1 = 2;
    private static final int FINAL_LINE_2 = 3;
    private static final int POD_A_COLUMN = 3;
    private static final int POD_B_COLUMN = POD_A_COLUMN + 2;
    private static final int POD_C_COLUMN = POD_B_COLUMN + 2;
    private static final int POD_D_COLUMN = POD_C_COLUMN + 2;

    private static final List<List<Point>> ROOMS = List.of(
            List.of(),
            List.of(),
            List.of(),
            List.of(Point.of(POD_A_COLUMN, FINAL_LINE_1), Point.of(POD_A_COLUMN, FINAL_LINE_2)),
            List.of(),
            List.of(Point.of(POD_B_COLUMN, FINAL_LINE_1), Point.of(POD_B_COLUMN, FINAL_LINE_2)),
            List.of(),
            List.of(Point.of(POD_C_COLUMN, FINAL_LINE_1), Point.of(POD_C_COLUMN, FINAL_LINE_2)),
            List.of(),
            List.of(Point.of(POD_D_COLUMN, FINAL_LINE_1), Point.of(POD_D_COLUMN, FINAL_LINE_2))
    );

    public static Optional<BurrowNode> bfs(Burrow initial, Predicate<Burrow> goalTest, Function<Burrow, List<Burrow>> successors) {
        assert initial != null;
        assert goalTest != null;
        assert successors != null;

        final PriorityQueue<BurrowNode> frontier = new PriorityQueue<>();
        frontier.offer(new BurrowNode(initial, null));

        while (!frontier.isEmpty()) {
            System.out.print(frontier.size());
            System.out.print('\n');
            final BurrowNode currentNode = frontier.poll();
            final Burrow currentState = currentNode.state;

            if (goalTest.test(currentState))
                return Optional.of(currentNode);

            final List<Burrow> next = successors.apply(currentState);
            for (Burrow child : next) {
                frontier.offer(new BurrowNode(child, currentNode));
            }
        }

        return Optional.empty();
    }

    public static boolean goalTest(Burrow burrow) {
        assert burrow != null;

        for (Amphipod pod : burrow.pods()) {
            final List<Point> rooms = ROOMS.get(expectedRoomForPodType(pod.type()));
            if (!rooms.get(0).equals(pod.location()) && !rooms.get(1).equals(pod.location()))
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
        if (!canMove(burrow, pod))
            return List.of();

        Amphipod next = pod;
        while (canMoveTo(burrow, next, Point.SOUTH)) {
            next = next.move(Point.SOUTH);
        }

        if (!inHallway(next))
            return List.of();

        final Optional<Amphipod> target = targetRoomAvailable(burrow, next);
        if (target.isPresent())
            return List.of(target.get());

        final List<Amphipod> options = new ArrayList<>();
        options.addAll(optionsMove(burrow, next, Point.EAST));
        options.addAll(optionsMove(burrow, next, Point.WEST));

        return options;
    }

    private static List<Amphipod> optionsMove(Burrow burrow, Amphipod next, Point direction) {
        final List<Amphipod> options = new ArrayList<>();
        while (burrow.isOpen(next.location().add(direction))) {
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

        if (location.y() == FINAL_LINE_1) {
            final Optional<Amphipod> other = burrow.podAt(location.add(Point.NORTH));
            return other.isPresent() && other.get().type() == pod.type();
        }

        return true;
    }

    protected static Optional<Amphipod> targetRoomAvailable(Burrow burrow, Amphipod pod) {
        assert inHallway(pod);

        final int room = expectedRoomForPodType(pod.type());
        final Optional<Amphipod> pod1 = burrow.podAt(ROOMS.get(room).get(0));
        if (pod1.isPresent())
            return Optional.empty();

        final Optional<Amphipod> pod2 = burrow.podAt(ROOMS.get(room).get(1));
        if (pod2.isPresent() && pod2.get().type() != pod.type())
            return Optional.empty();

        while (pod.location().x() > room) {
            pod = pod.move(Point.WEST);
            if (!burrow.isOpen(pod.location())) {
                return Optional.empty();
            }
        }

        while (pod.location().x() < room) {
            pod = pod.move(Point.EAST);
            if (!burrow.isOpen(pod.location()))
                return Optional.empty();
        }

        pod = pod.move(Point.NORTH);
        while (burrow.isOpen(pod.location().add(Point.NORTH)))
            pod = pod.move(Point.NORTH);

        return Optional.of(pod);
    }

    protected static boolean canStop(Amphipod pod) {
        final int x = pod.location().x();
        return (x != POD_A_COLUMN && x != POD_B_COLUMN && x != POD_C_COLUMN && x != POD_D_COLUMN);
    }

    protected static boolean canMove(Burrow burrow, Amphipod pod) {
        if (pod.location().y() == HALLWAY_LINE)
            return targetRoomAvailable(burrow, pod).isPresent();

        return !inCorrectRoom(burrow, pod);
    }

    protected static boolean canMoveTo(Burrow burrow, Amphipod pod, Point direction) {
        final Point next = pod.location().add(direction);
        return burrow.isOpen(next);
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
