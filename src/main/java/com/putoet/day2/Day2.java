package com.putoet.day2;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day2  {

    public static void main(String[] args) {
        final var commands = ResourceLines.list("/day2.txt").stream().map(Command::of).toList();

        Timer.run(() -> {
            final var submarine = new Submarine().move(commands);
            System.out.println("Submarine position is " + submarine.depth() * submarine.horizontalPosition());
        });

        Timer.run(() -> {
            final var submarine = new Submarine2().move(commands);
            System.out.println("Submarine position is " + submarine.depth() * submarine.horizontalPosition());
        });
    }
}
