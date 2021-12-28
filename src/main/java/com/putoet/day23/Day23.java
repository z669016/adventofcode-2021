package com.putoet.day23;

import com.putoet.day.Day;
import com.putoet.resources.ResourceLines;

import java.util.Optional;

public class Day23 extends Day {

    protected Day23(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        final Day day = new Day23(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Burrow burrow = Burrow.of(ResourceLines.list("/day23.txt"));
        final Optional<BurrowSearch.BurrowNode> node = BurrowSearch.bfs(burrow);
        if (node.isEmpty())
            System.out.println("No solution found");
        else
            System.out.println("the least energy required to organize the amphipods is " + node.get().state().energyUsed());
    }

    @Override
    public void part2() {
        final Burrow burrow = Burrow.of(ResourceLines.list("/day23-2.txt"));
        final Optional<BurrowSearch.BurrowNode> node = BurrowSearch.bfs(burrow);
        if (node.isEmpty())
            System.out.println("No solution found");
        else
            System.out.println("the least energy required to organize the unfolded amphipods is " + node.get().state().energyUsed());
    }
}
