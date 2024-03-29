package com.putoet.day12;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Cave implements Comparable<Cave> {
    private final List<Cave> neighbours = new ArrayList<>();
    private final String name;
    @Getter
    private final boolean small;

    public Cave(String name) {
        this.name = name;
        this.small = name.toLowerCase().equals(name);
    }

    public String name() {
        return name;
    }

    public List<Cave> neighbours() {
        return neighbours;
    }

    public void addNeighbour(Cave neighbour){
        if (!neighbours.contains(neighbour))
            neighbours.add(neighbour);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Cave cave)) return false;
        return Objects.equals(name, cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(@NotNull Cave other) {
        return name.compareTo(other.name);
    }
}
