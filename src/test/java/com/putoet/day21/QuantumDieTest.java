package com.putoet.day21;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantumDieTest {

    @Test
    void get() {
        final Die die = new QuantumDie();

        assertEquals(1, die.get());
        assertEquals(2, die.get());
        assertEquals(3, die.get());
        assertEquals(1, die.get());
        assertEquals(2, die.get());
        assertEquals(3, die.get());
        assertEquals(6, die.turns());
    }
}