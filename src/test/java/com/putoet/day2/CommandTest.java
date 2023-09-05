package com.putoet.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void ofForward() {
        assertEquals(new Command(Direction.FORWARD, 5), Command.of("forward 5"));
    }

    @Test
    void ofUp() {
        assertEquals(new Command(Direction.UP, 7), Command.of("up 7"));
    }

    @Test
    void ofDown() {
        assertEquals(new Command(Direction.DOWN, 9), Command.of("down 9"));
    }

    @Test
    void ofError() {
        assertThrows(AssertionError.class, () -> Command.of(""));
        assertThrows(AssertionError.class, () -> Command.of("forward1"));
    }
}