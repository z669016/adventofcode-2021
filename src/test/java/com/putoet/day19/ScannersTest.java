package com.putoet.day19;

import com.putoet.grid.Point3D;
import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScannersTest {
    private final List<Scanner> scanners = Scanners.of(ResourceLines.list("/day19.txt"));

    @Test
    void beaconCount() {
        final var scannerLocations = Scanners.scannerLocations(scanners);
        assertEquals(79, scannerLocations.getValue0().beacons().size());
    }

    @Test
    void longestDistance() {
        final var scannerLocations = Scanners.scannerLocations(scanners).getValue1();
        assertEquals(3621, Scanners.maxDistance(scannerLocations));
    }

    @Test
    void rotations() {
        final var point = Point3D.of(1, 2, 3);
        assertEquals(allRotations(), Point3D.rotations(point));
    }

    List<Point3D> allRotations() {
        final var rotations = new ArrayList<Point3D>();
        rotations.add(Point3D.of(1, 3, -2));
        rotations.add(Point3D.of(-3, 1, -2));
        rotations.add(Point3D.of(-1, -3, -2));
        rotations.add(Point3D.of(3, -1, -2));
        rotations.add(Point3D.of(3, -2, 1));
        rotations.add(Point3D.of(2, 3, 1));
        rotations.add(Point3D.of(-3, 2, 1));
        rotations.add(Point3D.of(-2, -3, 1));
        rotations.add(Point3D.of(-2, 1, 3));
        rotations.add(Point3D.of(-1, -2, 3));
        rotations.add(Point3D.of(2, -1, 3));
        rotations.add(Point3D.of(1, 2, 3));
        rotations.add(Point3D.of(-3, -1, 2));
        rotations.add(Point3D.of(1, -3, 2));
        rotations.add(Point3D.of(3, 1, 2));
        rotations.add(Point3D.of(-1, 3, 2));
        rotations.add(Point3D.of(-1, 2, -3));
        rotations.add(Point3D.of(-2, -1, -3));
        rotations.add(Point3D.of(1, -2, -3));
        rotations.add(Point3D.of(2, 1, -3));
        rotations.add(Point3D.of(2, -3, -1));
        rotations.add(Point3D.of(3, 2, -1));
        rotations.add(Point3D.of(-2, 3, -1));
        rotations.add(Point3D.of(-3, -2, -1));

        return rotations;
    }
}