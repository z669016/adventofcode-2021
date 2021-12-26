package com.putoet.day22;

import com.putoet.grid.Point3D;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Range3DTest {
    private static final Range3D test = Range3D.of(Point3D.ORIGIN, Point3D.of(10, 10, 10));
    private static final Range3D inner = Range3D.of(Point3D.of(2, 2, 2), Point3D.of(4, 4, 4));
    private static final Range3D right = Range3D.of(Point3D.of(2, 2, 2), Point3D.of(14, 14, 14));
    private static final Range3D left = Range3D.of(Point3D.of(-2, -2, -2), Point3D.of(4, 4, 4));
    private static final Range3D out = Range3D.of(Point3D.of(-2, -2, -2), Point3D.of(-1, -1, -1));

    @Test
    void point3DCount() {
        Range3D range = Range3D.of(Point3D.ORIGIN, Point3D.of(1, 1, 1));
        assertEquals(8, range.point3DCount());
    }

    @Test
    void toSet() {
        Range3D range = Range3D.of("x=10..12,y=10..12,z=10..12");
        Set<Point3D> set = range.toSet();
        assertEquals(27, set.size());

        range = Range3D.of("x=9..11,y=9..11,z=9..11");
        set = range.toSet();
        assertEquals(27, set.size());

        range = Range3D.of("x=10..10,y=10..10,z=10..10");
        set = range.toSet();
        assertEquals(1, set.size());
    }

    @Test
    void overlap() {
        assertTrue(test.overlap(inner));
        assertTrue(inner.overlap(test));

        assertTrue(test.overlap(right));
        assertTrue(right.overlap(test));

        assertTrue(test.overlap(left));
        assertTrue(left.overlap(test));

        assertFalse(test.overlap(out));
        assertFalse(out.overlap(test));
    }

    @Test
    void contains() {
        assertTrue(test.contains(inner));
        assertFalse(inner.contains(test));

        assertFalse(test.contains(right));
        assertFalse(right.contains(test));

        assertFalse(test.contains(left));
        assertFalse(left.contains(test));

        assertFalse(test.contains(out));
        assertFalse(out.contains(test));
    }
}