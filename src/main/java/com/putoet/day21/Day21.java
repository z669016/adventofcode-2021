package com.putoet.day21;

import com.putoet.day.Day;
import com.putoet.day20.Image;
import com.putoet.day20.ImageEnhancer;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day21 extends Day {
    public static final int START1 = 5;
    public static final int START2 = 8;

    protected Day21(String[] args) {
        super(args);

        final List<String> lines = ResourceLines.list("/day20.txt");
    }

    public static void main(String[] args) {
        final Day day = new Day21(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final DiracDice game = new DiracDice(START1, START2);
        game.play();

        System.out.println("if you multiply the score of the losing player by the number of times the die was rolled during the game you get " +
                (game.looserScore() * game.turns()));

        // 739785 is not the right number
    }
}
