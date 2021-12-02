package com.putoet.day2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @Test
    void ofForward() {
        assertEquals(Command.with(Direction.FORWARD, 5), Command.of("forward 5"));
    }

    @Test
    void ofUp() {
        assertEquals(Command.with(Direction.UP, 7), Command.of("up 7"));
    }

    @Test
    void ofDown() {
        assertEquals(Command.with(Direction.DOWN, 9), Command.of("down 9"));
    }

    @Test
    void ofError() {
        assertThrows(AssertionError.class, () -> Command.of(null));
        assertThrows(AssertionError.class, () -> Command.of(""));
        assertThrows(AssertionError.class, () -> Command.of("forward1"));
    }
}