package com.putoet.day11;

import com.putoet.grid.Point;

import java.util.ArrayList;
import java.util.List;

public class Octopus {
    public List<Octopus> neighbours = new ArrayList<>();
    private final Point id;
    private int energyLevel;
    private boolean flashed;

    public Octopus(Point id, int energyLevel) {
        this.id = id;
        this.energyLevel = energyLevel;
    }

    public int energyLevel() {
        return energyLevel;
    }

    public boolean flash() {
        if (energyLevel > 9 && !flashed) {
            flashed = true;
            neighbours.forEach(Octopus::raise);
            energyLevel = 0;
            return true;
        }
        return false;
    }

    public void raise() {
        if (!flashed)
            energyLevel++;
    }

    public void step() {
        flashed = false;
    }

    public void addNeighbour(Octopus neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public String toString() {
        return id + "{" + energyLevel + "}";
    }
}
