package com.putoet.day21;

class DeterministicDie implements Die {
    private int number = 1;
    private int turns = 0;

    @Override
    public int get() {
        var score = 0;
        turns += 3;
        for (var i = 0; i < 3; i++) {
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
