package com.putoet.day7;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

class FuelModel {
    private final List<Integer> fuels;
    private final Map<Integer,Integer> cache  = new ConcurrentHashMap<>();

    public FuelModel(@NotNull List<Integer> fuels) {
        assert !fuels.isEmpty();

        this.fuels = fuels;
    }

    public long altitudeFuelCost(int altitude, @NotNull BiFunction<Integer,Integer,Integer> calculator) {
        return fuels.stream().mapToLong(fuel -> calculator.apply(altitude, fuel)).sum();
    }

    public int consumptionSimple(int from, int to) {
        return Math.abs(from - to);
    }

    public int consumptionComplicated(int from, int to) {
        final var distance = consumptionSimple(from, to);
        return cache.computeIfAbsent(distance, key -> IntStream.range(0, key + 1).sum());
    }

    public Pair<Integer,Integer> minMax() {
        final var min = fuels.stream().mapToInt(i ->i).min();
        if (min.isEmpty())
            throw new IllegalStateException("No minimum in the fuels list");

        return Pair.with(min.getAsInt(), fuels.stream().mapToInt(i ->i).max().getAsInt());
    }

    public Optional<Pair<Integer, Long>> optimalAltitude(@NotNull BiFunction<Integer,Integer,Integer> calculator) {
        final var minMax = minMax();
        return IntStream.range(minMax.getValue0(), minMax.getValue1() + 1)
                .mapToObj(altitude -> Pair.with(altitude, this.altitudeFuelCost(altitude, calculator)))
                .min(Comparator.comparing(Pair::getValue1));
    }
}
