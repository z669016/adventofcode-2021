package com.putoet.day4;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BingoCardTest {
    private static final List<String> BOARD = List.of(
            "14 21 17 24  4",
            "10 16 15  9 19",
            "18  8 23 26 20",
            "22 11 13  6  5",
            " 2  0 12  3  7"
    );
    private static final int[][] NUMBERS = {
            {14, 21, 17, 24, 4},
            {10, 16, 15, 9, 19},
            {18, 8, 23, 26, 20},
            {22, 11, 13, 6, 5},
            {2, 0, 12, 3, 7}
    };

    @Test
    void of() {
        final var board = BingoCard.of(BOARD);
        assertArrayEquals(NUMBERS, board.numbers());
    }


    @Test
    void score() {
        final var board = BingoCard.of(BOARD);
        final var calls = List.of(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21);
        calls.forEach(number -> assertFalse(board.call(number)));

        assertTrue(board.call(24));
        assertEquals(4512, board.score());
    }

    @Test
    void callRow() {
        final var board = BingoCard.of(BOARD);

        assertFalse(board.call(17));
        assertFalse(board.call(14));
        assertFalse(board.call(4));
        assertFalse(board.call(21));
        assertTrue(board.call(24));
    }

    @Test
    void callColumn() {
        final var board = BingoCard.of(BOARD);

        assertFalse(board.call(15));
        assertFalse(board.call(23));
        assertFalse(board.call(17));
        assertFalse(board.call(12));
        assertTrue(board.call(13));
    }
}