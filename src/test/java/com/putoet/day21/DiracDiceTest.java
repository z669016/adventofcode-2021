package com.putoet.day21;

import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiracDiceTest {
    private int startOne, startTwo;

    @BeforeEach
    void setup() {
        final List<String> lines = ResourceLines.list("/day21.txt");
        startOne = Integer.parseInt(lines.get(0).split("starting position: ")[1]);
        startTwo = Integer.parseInt(lines.get(1).split("starting position: ")[1]);
    }

    @Test
    void play() {
        final DiracDice game = new DiracDice(startOne, startTwo, new DeterministicDie());
        final Pair<BigInteger, BigInteger> scores = game.play(1000);
        final long looserScore = Math.min(scores.getValue0().longValue(), scores.getValue1().longValue());

        assertEquals(745L, looserScore);
        assertEquals(993, game.turns());
    }

    @Test
    void playQuantum() {
        final DiracDice game = new DiracDice(startOne, startTwo, new QuantumDie());
        final Pair<BigInteger, BigInteger> scores = game.play(21);
        final long highest = Math.max(scores.getValue0().longValue(), scores.getValue1().longValue());

        assertEquals(444_356_092_776_315L, highest);
    }
}