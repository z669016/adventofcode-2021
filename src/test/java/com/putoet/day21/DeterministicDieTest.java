package com.putoet.day21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeterministicDieTest {

    @Test
    void get() {
        final Die die = new DeterministicDie();

        assertEquals(6, die.get());
        assertEquals(3, die.turns());
        assertEquals(15, die.get());
        assertEquals(6, die.turns());

        for (int t = 6; t < 99; t += 3) {
            die.get();
        }
        assertEquals(103, die.get());
        assertEquals(102,die.turns());
    }
}