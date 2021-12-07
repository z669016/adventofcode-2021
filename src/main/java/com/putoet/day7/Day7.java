package com.putoet.day7;

import com.putoet.day.Day;
import com.putoet.resources.CSV;
import org.javatuples.Pair;

import java.util.List;
import java.util.Optional;

public class Day7 extends Day {
    private final FuelModel model;

    protected Day7(String[] args) {
        super(args);

        final List<Integer> fuels = CSV.flatList("/day7.txt", Integer::parseInt);
        model = new FuelModel(fuels);
    }

    public static void main(String[] args) {
        final Day day = new Day7(args);
        day.challenge();
    }

    @Override
    public void part1() {
        final Optional<Pair<Integer, Long>> optimal = model.optimalAltitude(model::consumptionSimple);

        if (optimal.isEmpty())
            System.out.println("Optimal altitude could not be determined.");
        else
            System.out.println("The consumption at the optimal altitude is " + optimal.get().getValue1());
    }

    @Override
    public void part2() {
        final Optional<Pair<Integer, Long>> optimal = model.optimalAltitude(model::consumptionComplicated);

        if (optimal.isEmpty())
            System.out.println("Optimal altitude could not be determined.");
        else
            System.out.println("The (complicated) consumption at the optimal altitude is " + optimal.get().getValue1());
    }
}
