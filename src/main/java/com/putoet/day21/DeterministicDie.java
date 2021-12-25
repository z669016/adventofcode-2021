package com.putoet.day21;

public class DeterministicDie implements Die {
    private int number = 1;
    private int turns = 0;

    @Override
    public int get() {
        int score = 0;
        turns += 3;
        for (int i = 0; i < 3; i++) {
            score += number;
            number += 1;
            if (number > 100)
                number -= 100;
        }

        return score;
    }

    public int turns() {
        return turns;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
