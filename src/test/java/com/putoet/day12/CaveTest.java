package com.putoet.day12;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaveTest {

    @Test
    void small() {
        assertFalse(new Cave("NN").isSmall());
        assertFalse(new Cave("Na").isSmall());
        assertTrue(new Cave("n").isSmall());
    }
}