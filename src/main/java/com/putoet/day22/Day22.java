package com.putoet.day22;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day22 {
    public static void main(String[] args) {
        final var commands = Command.of(ResourceLines.list("/day22.txt"));

        Timer.run(() -> System.out.println("The number of cubes on after limited processing is " + Reactor.processLimited(commands)));
        Timer.run(() -> System.out.println("The number of cubes on is " + Reactor.process(commands)));
    }
}
