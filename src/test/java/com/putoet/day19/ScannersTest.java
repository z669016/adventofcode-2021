package com.putoet.day19;

import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScannersTest {
    private final List<Scanner> scanners = Scanners.of(ResourceLines.list("/day19-0.txt"));

    @Test
    void of() {
        assertEquals(5, scanners.size());
    }

    @Test
    void sameSmall() {
        assertTrue(Transformer.isTransformed(scanners.get(0).beacons(), scanners.get(0).beacons(), 6).isPresent());

        final Scanner first = scanners.get(0);
        for (int i = 1; i < scanners.size(); i++) {
            assertTrue(Transformer.isTransformed(first.beacons(), scanners.get(i).beacons(), 6).isPresent());
        }
    }

    @Test
    void same() {
        final List<Scanner> scanners = Scanners.of(ResourceLines.list("/day19.txt"));

        assertEquals(5, scanners.size());
        final List<Scanner> transformedScanners = new ArrayList<>();
        transformedScanners.add(scanners.get(0));
        for (int idx = 0; idx < scanners.size() - 1; idx++) {
            final Set<Point3D> from = transformedScanners.get(idx).beacons();
            for (int i = idx + 1; i < scanners.size(); i++) {
                System.out.println("Comparing " + idx + " with " + i);
                final Optional<Set<Point3D>> transformed =
                        Transformer.isTransformed(from, scanners.get(i).beacons(), 12);
                if (transformed.isPresent())
                    transformedScanners.add(new Scanner(scanners.get(i).id(), transformed.get()));
            }
        }
        assertEquals(scanners.size(), transformedScanners.size());
    }
}