package com.putoet.day21;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiracDiceTest {
    private int startOne, startTwo;

    @BeforeEach
    void setup() {
        final var lines = ResourceLines.list("/day21.txt");
        startOne = Integer.parseInt(lines.get(0).split("starting position: ")[1]);
        startTwo = Integer.parseInt(lines.get(1).split("starting position: ")[1]);
    }

    @Test
    void play() {
        final var game = new DiracDice(startOne, startTwo, new DeterministicDie());
        final var scores = game.play(1000);
        final var looserScore = Math.min(scores.getValue0().longValue(), scores.getValue1().longValue());

        assertEquals(745L, looserScore);
        assertEquals(993, game.turns());
    }

    @Test
    void playQuantum() {
        final var game = new DiracDice(startOne, startTwo, new QuantumDie());
        final var scores = game.play(21);
        final var highest = Math.max(scores.getValue0().longValue(), scores.getValue1().longValue());

        assertEquals(444_356_092_776_315L, highest);
    }
}