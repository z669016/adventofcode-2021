package com.putoet.day25;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day25 {
    public static void main(String[] args) {
        final var initial = SeaFloor.of(ResourceLines.list("/day25.txt"));

        Timer.run(() -> {
            var step = 1;
            var next = initial.step();
            while (next.getValue1() != 0) {
                step++;
                next = next.getValue0().step();
            }

            System.out.println("the first step on which no sea cucumbers move is " + step);
        });
    }
}
