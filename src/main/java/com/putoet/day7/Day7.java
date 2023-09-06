package com.putoet.day7;

import com.putoet.resources.CSV;
import com.putoet.utils.Timer;

public class Day7 {


    public static void main(String[] args) {
        final var model = new FuelModel(CSV.flatList("/day7.txt", Integer::parseInt));

        Timer.run(() -> part1(model));
        Timer.run(() -> part2(model));
    }

    static void part1(FuelModel model) {
        final var optimal = model.optimalAltitude(model::consumptionSimple).orElseThrow();
        System.out.println("The consumption at the optimal altitude is " + optimal.getValue1());
    }

    static void part2(FuelModel model) {
        final var optimal = model.optimalAltitude(model::consumptionComplicated).orElseThrow();
        System.out.println("The (complicated) consumption at the optimal altitude is " + optimal.getValue1());
    }
}
