package com.putoet.day11;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class Octopus {
    private final List<Octopus> neighbours = new ArrayList<>();
    private final Point id;
    private int energyLevel;
    private boolean flashed;

    public Octopus(@NotNull Point id, int energyLevel) {
        this.id = id;
        this.energyLevel = energyLevel;
    }

    public int energyLevel() {
        return energyLevel;
    }

    public boolean flash() {
        if (energyLevel > 9 && !flashed) {
            flashed = true;
            neighbours.forEach(Octopus::raiseNotFlashed);
            energyLevel = 0;
            return true;
        }
        return false;
    }

    public void raise() {
        flashed = false;
        raiseNotFlashed();
    }

    private void raiseNotFlashed() {
        if (!flashed)
            energyLevel++;
    }

    public void addNeighbour(Octopus neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public String toString() {
        return id + "{" + energyLevel + "}";
    }
}
