package com.putoet.day23;

import com.putoet.grid.Point;

public record Amphipod(int id, AmphipodType type, Point location, long energyUsed) {

    public Amphipod move(Point direction) {
        return new Amphipod(id, type, location.add(direction), energyUsed + type.energy());
    }

    public char symbol() {
        return type.symbol();
    }

    @Override
    public String toString() {
        return String.format("%c:%d:%s:%d", type.symbol(), id, location.toString(), energyUsed);
    }
}
