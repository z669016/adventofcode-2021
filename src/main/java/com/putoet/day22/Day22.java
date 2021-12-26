package com.putoet.day22;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;

public class Day22 extends Day {
    private final List<Command> commands;

    protected Day22(String[] args) {
        super(args);
        commands = Command.of(ResourceLines.list("/day22.txt"));
    }

    public static void main(String[] args) {
        final Day day = new Day22(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Reactor reactor = new Reactor();
        reactor.process(commands);

        System.out.println("The number of cubes on is " + reactor.size());
    }
}
