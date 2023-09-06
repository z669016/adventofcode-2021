package com.putoet.day21;

class QuantumDie implements Die {
    public int turns = 0;
    public int number = 1;

    @Override
    public int get() {
        turns++;
        if (number > 3)
            number = 1;
        return number++;
    }

    public int turns() {
        return turns;
    }

    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
