package com.putoet.day7;

import com.putoet.resources.CSV;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FuelModelTest {
    private FuelModel model;

    @BeforeEach
    void setup() {
        final List<Integer> crabs = CSV.flatList("/day7.txt", Integer::parseInt);
        model = new FuelModel(crabs);
    }

    @Test
    void altitudeFuelCost() {
        assertEquals(37L, model.altitudeFuelCost(2, model::consumptionSimple));
        assertEquals(41L, model.altitudeFuelCost(1, model::consumptionSimple));
        assertEquals(39L, model.altitudeFuelCost(3, model::consumptionSimple));
        assertEquals(71L, model.altitudeFuelCost(10, model::consumptionSimple));
    }

    @Test
    void altitudeFuelCostComplicated() {
        assertEquals(168L, model.altitudeFuelCost(5, model::consumptionComplicated));
        assertEquals(206L, model.altitudeFuelCost(2, model::consumptionComplicated));
    }

    @Test
    void minMax() {
        assertEquals(Pair.with(0, 16), model.minMax());
    }

    @Test
    void optimalAltitude() {
        Optional<Pair<Integer, Long>> optimal = model.optimalAltitude(model::consumptionSimple);
        assertTrue(optimal.isPresent());
        assertEquals(2, optimal.get().getValue0());

        optimal = model.optimalAltitude(model::consumptionComplicated);
        assertTrue(optimal.isPresent());
        assertEquals(5, optimal.get().getValue0());
    }
}