package com.putoet.day21;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day21 {
    public static void main(String[] args) {
        final var lines = ResourceLines.list("/day21.txt");
        final var startOne = Integer.parseInt(lines.get(0).split("starting position: ")[1]);
        final var startTwo = Integer.parseInt(lines.get(1).split("starting position: ")[1]);

        Timer.run(() -> part1(startOne, startTwo));
        Timer.run(() -> part2(startOne, startTwo));

    }

    static void part1(int startOne, int startTwo) {
        final var game = new DiracDice(startOne, startTwo, new DeterministicDie());
        final var scores = game.play(1000);
        final var looserScore = Math.min(scores.getValue0().longValue(), scores.getValue1().longValue());
        System.out.println("if you multiply the score of the losing player by the number of times the die was rolled during the game you get " +
                (looserScore * game.turns()));
    }

    static void part2(int startOne, int startTwo) {
        final var game = new DiracDice(startOne, startTwo, new QuantumDie());
        final var scores = game.play(21);
        System.out.println("the player that wins in more universes; has one in  " +
                Math.max(scores.getValue0().longValue(), scores.getValue1().longValue()));
    }
}
