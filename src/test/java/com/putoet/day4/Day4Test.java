package com.putoet.day4;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day4Test {

    @Test
    void play() {
        final Day4 day4 = new Day4(new String[]{});
        final Optional<BingoBoard> board = day4.play();
        assertTrue(board.isPresent());
        assertEquals(4512, board.get().score());
    }

    @Test
    void playForLast() {
        final Day4 day4 = new Day4(new String[]{});
        final Optional<BingoBoard> board = day4.playForLast();
        assertTrue(board.isPresent());
        assertEquals(1924, board.get().score());
    }
}