package com.putoet.day23;

public enum AmphipodType {
    AMBER(1, 'A'),
    BRONZE(10, 'B'),
    COPPER(100, 'C'),
    DESERT(1000, 'D');

    private final int energy;
    private final char symbol;

    AmphipodType(int energy, char symbol) {
        this.energy = energy;
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }

    public int energy() {
        return energy;
    }
}
