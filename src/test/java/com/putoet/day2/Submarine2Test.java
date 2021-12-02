package com.putoet.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Submarine2Test {

    @Test
    void move() {
        final Submarine2 submarine = new Submarine2();
        SubmarineTest.commands.stream()
                .map(Command::of)
                .forEach(submarine::move);

        assertEquals(900,submarine.depth() * submarine.horizontalPosition());
    }

}