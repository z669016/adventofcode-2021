package com.putoet.day19;

import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScannersTest {
    private final List<Scanner> scanners = Scanners.of(ResourceLines.list("/day19-0.txt"));

    @Test
    void of() {
        assertEquals(5, scanners.size());
    }

    @Test
    void sameSmall() {
        final Scanner first = scanners.get(0);
        for (int i = 1; i < scanners.size(); i++) {
            assertTrue(Transformer.isTransformed(first, scanners.get(i), 6).isPresent());
        }
    }

    @Test
    void beacons() {
        final List<Scanner> scanners = new ArrayList<>(Scanners.of(ResourceLines.list("/day19.txt")));
        final List<Scanner> transformed = Transformer.transform(scanners);
        final Set<Point3D> beacons = Scanners.beacons(transformed);

        assertEquals(79, beacons.size());
    }
}