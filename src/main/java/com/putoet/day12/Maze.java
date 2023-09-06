package com.putoet.day12;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Maze {
    private final Cave start = new Cave("start");
    private final Cave end = new Cave("end");
    private final Map<String, Cave> caves = new HashMap<>();

    public Maze() {
        caves.put(start.name(), start);
        caves.put(end.name(),end);
    }

    public Cave start() {
        return start;
    }

    public Cave end() {
        return end;
    }
    public void connect(String from, String to) {
        final var first = caves.computeIfAbsent(from, Cave::new);
        final var second = caves.computeIfAbsent(to, Cave::new);

        first.addNeighbour(second);
        second.addNeighbour(first);
    }

    public int size() {
        return caves.size();
    }

    public static Maze of(@NotNull List<String> lines) {
        final var maze = new Maze();
        for(var line : lines) {
            final var split = line.split("-");
            if (split.length != 2)
                throw new IllegalArgumentException("Invalid connection " + line);

            maze.connect(split[0],split[1]);
        }

        return maze;
    }
}
