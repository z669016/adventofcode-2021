package com.putoet.day4;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Day4Test {

    @Test
    void play() {
        final Day4 day4 = new Day4(new String[]{});
        final Optional<BingoCard> card = day4.play();
        assertTrue(card.isPresent());
        assertEquals(4512, card.get().score());

        System.out.println(card.get());
    }

    @Test
    void playForLast() {
        final Day4 day4 = new Day4(new String[]{});
        final Optional<BingoCard> card = day4.playForLast();
        assertTrue(card.isPresent());
        assertEquals(1924, card.get().score());
    }
}