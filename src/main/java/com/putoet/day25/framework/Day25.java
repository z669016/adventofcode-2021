package com.putoet.day25.framework;

import com.putoet.day.Day;
import com.putoet.day25.application.Day25InputPort;
import com.putoet.day25.application.Day25OutputPort;
import com.putoet.day25.application.Day25UseCase;

public class Day25 extends Day {
    private final Day25OutputPort outputPort = new FileAdapter("/day25.txt");
    private final Day25UseCase useCase = new Day25InputPort(outputPort);

    protected Day25(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day25(args);
        day.challenge();
    }

    @Override
    public void part1() {
        System.out.println("the first step on which no sea cucumbers move is " + useCase.firstStepOnWhichNoSeaCucumbersMove());
    }

    @Override
    public void part2() {
        // Day 25 part 2 is a free ride !!!!
    }
}
