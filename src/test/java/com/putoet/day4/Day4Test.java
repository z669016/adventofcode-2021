package com.putoet.day4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {
    @Test
    void play() {
        final var card = Day4.play();
        assertEquals(4512, card.orElseThrow().score());

        System.out.println(card.get());
    }

    @Test
    void playForLast() {
        final var card = Day4.playForLast();
        assertEquals(1924, card.orElseThrow().score());
    }
}