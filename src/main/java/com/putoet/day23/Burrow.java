package com.putoet.day23;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

class Burrow implements Comparable<Burrow> {
    public static final char OPEN = '.';

    private final char[][] floorPlan;
    private final Map<Point, Amphipod> pods;
    private final long energyUsed;

    public Burrow(char[][] floorPlan, @NotNull Map<Point, Amphipod> pods) {
        this.floorPlan = floorPlan;
        this.pods = pods;
        energyUsed = pods.values().stream().mapToLong(Amphipod::energyUsed).sum();
    }

    public static Burrow of(@NotNull List<String> lines) {
        final var floorPlan = new char[lines.size()][];
        final var pods = new HashMap<Point, Amphipod>();
        var id = 1;

        for (var y = 0; y < lines.size(); y++) {
            floorPlan[y] = lines.get(y).toCharArray();
            for (var x = 0; x < floorPlan[y].length; x++) {
                switch (floorPlan[y][x]) {
                    case 'A' -> {
                        pods.put(Point.of(x, y), new Amphipod(id, AmphipodType.AMBER, Point.of(x, y), 0));
                        floorPlan[y][x] = OPEN;
                        id++;
                    }
                    case 'B' -> {
                        pods.put(Point.of(x, y), new Amphipod(id, AmphipodType.BRONZE, Point.of(x, y), 0));
                        floorPlan[y][x] = OPEN;
                        id++;
                    }
                    case 'C' -> {
                        pods.put(Point.of(x, y), new Amphipod(id, AmphipodType.COPPER, Point.of(x, y), 0));
                        floorPlan[y][x] = OPEN;
                        id++;
                    }
                    case 'D' -> {
                        pods.put(Point.of(x, y), new Amphipod(id, AmphipodType.DESERT, Point.of(x, y), 0));
                        floorPlan[y][x] = OPEN;
                        id++;
                    }
                }
            }
        }
        return new Burrow(floorPlan, pods);
    }

    public boolean isOpen(@NotNull Point point) {
        if (point.y() < 0 || point.y() >= floorPlan.length)
            return false;

        if (point.x() < 0 || point.x() >= floorPlan[point.y()].length)
            return false;

        if (pods.get(point) != null)
            return false;

        return floorPlan[point.y()][point.x()] == OPEN;
    }

    public Collection<Amphipod> pods() {
        return pods.values();
    }

    public Optional<Amphipod> podAt(@NotNull Point point) {
        return Optional.ofNullable(pods.get(point));
    }

    public Burrow move(@NotNull Amphipod pod) {
        final var nextPods = new HashMap<>(
                pods.entrySet().stream()
                        .filter(entry -> entry.getValue().id() != pod.id())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        );
        nextPods.put(pod.location(), pod);
        return new Burrow(floorPlan, nextPods);
    }

    public long energyUsed() {
        return energyUsed;
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        for (var y = 0; y < floorPlan.length; y++) {
            final var line = Arrays.copyOf(floorPlan[y], floorPlan[y].length);

            for (var pod : pods.values())
                if (pod.location().y() == y)
                    line[pod.location().x()] = pod.symbol();

            sb.append(String.valueOf(line));
            if (y == 0) {
                sb.append(" (");
                sb.append(energyUsed);
                sb.append(")");
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(@NotNull Object o) {
        if (this == o) return true;
        if (!(o instanceof Burrow burrow)) return false;
        return pods.equals(burrow.pods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pods);
    }

    @Override
    public int compareTo(@NotNull Burrow other) {
        return Long.compare(energyUsed, other.energyUsed);
    }
}
