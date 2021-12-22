package com.putoet.day21;

import java.util.function.Supplier;

public class Die implements Supplier<Integer> {
    private int number = 1;
    private int turns = 0;

    @Override
    public Integer get() {
        int score = 0;
        turns += 3;
        for (int i = 0; i < 3; i++) {
            score += number;
            number += 1;
            if (number > 100)
                number -= 100;
        }

        return score % 10;
    }

    public int turns() {
        return turns;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
