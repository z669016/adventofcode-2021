package com.putoet.day21;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;

import java.math.BigInteger;
import java.util.List;

public class Day21 extends Day {
    public final int startTwo;
    private final int startOne;

    protected Day21(String[] args) {
        super(args);

        final List<String> lines = ResourceLines.list("/day21.txt");
        startOne = Integer.parseInt(lines.get(0).split("starting position: ")[1]);
        startTwo = Integer.parseInt(lines.get(1).split("starting position: ")[1]);
    }

    public static void main(String[] args) {
        final Day day = new Day21(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final DiracDice game = new DiracDice(startOne, startTwo, new DeterministicDie());
        final Pair<BigInteger, BigInteger> scores = game.play(1000);
        final long looserScore = Math.min(scores.getValue0().longValue(), scores.getValue1().longValue());
        System.out.println("if you multiply the score of the losing player by the number of times the die was rolled during the game you get " +
                (looserScore * game.turns()));
    }

    @Override
    public void part2() {
        final DiracDice game = new DiracDice(startOne, startTwo, new QuantumDie());
        final Pair<BigInteger, BigInteger> scores = game.play(21);
        System.out.println("the player that wins in more universes; has one in  " +
                Math.max(scores.getValue0().longValue(), scores.getValue1().longValue()));
    }
}
