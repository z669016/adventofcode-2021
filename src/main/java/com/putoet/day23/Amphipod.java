package com.putoet.day23;

import com.putoet.grid.Point;
import org.jetbrains.annotations.NotNull;

record Amphipod(int id, @NotNull AmphipodType type, @NotNull Point location, long energyUsed) {

    public Amphipod move(@NotNull Point direction) {
        return new Amphipod(id, type, location.add(direction), energyUsed + type.energy());
    }

    public char symbol() {
        return type.symbol();
    }

    @Override
    public String toString() {
        return String.format("%c:%d:%s:%d", type.symbol(), id, location, energyUsed);
    }
}
