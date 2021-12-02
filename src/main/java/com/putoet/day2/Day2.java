package com.putoet.day2;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.List;
import java.util.stream.Collectors;

public class Day2 extends Day {
    private final List<Command> commands;

    public Day2(String[] args) {
        super(args);

        commands = ResourceLines.list("/day2.txt").stream().map(Command::of).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        final Day day = new Day2(args);
        day.challenge(args);
    }

    @Override
    public void part1() {
        final Submarine submarine = new Submarine().move(commands);
        System.out.println("Submarine position is " + submarine.depth() * submarine.horizontalPosition());
    }

    @Override
    public void part2() {
        final Submarine submarine = new Submarine2().move(commands);
        System.out.println("Submarine position is " + submarine.depth() * submarine.horizontalPosition());
    }
}
