package com.putoet.day2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubmarineTest {
    public static final List<String> commands = List.of(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2"
    );

    @Test
    void move() {
        final Submarine submarine = new Submarine();
        commands.stream()
                .map(Command::of)
                .forEach(submarine::move);

        assertEquals(150,submarine.depth() * submarine.horizontalPosition());
    }
}