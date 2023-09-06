package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import com.putoet.utils.Timer;

public class Day23 {

    public static void main(String[] args) {
        Timer.run(Day23::part1);
        Timer.run(Day23::part2);
    }

    static void part1() {
        final var burrow = Burrow.of(ResourceLines.list("/day23.txt"));
        final var node = BurrowSearch.bfs(burrow).orElseThrow();
        System.out.println("the least energy required to organize the amphipods is " + node.state().energyUsed());
    }

    static void part2() {
        final var burrow = Burrow.of(ResourceLines.list("/day23-2.txt"));
        final var node = BurrowSearch.bfs(burrow).orElseThrow();
        System.out.println("the least energy required to organize the unfolded amphipods is " + node.state().energyUsed());
    }
}
