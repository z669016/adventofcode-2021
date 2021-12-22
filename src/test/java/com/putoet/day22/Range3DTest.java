package com.putoet.day22;

import com.putoet.grid.Point3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range3DTest {
    private static final Range3D test = Range3D.of(Point3D.ORIGIN, Point3D.of(10, 10, 10));
    private static final Range3D inner = Range3D.of(Point3D.of(2, 2, 2), Point3D.of(4, 4, 4));
    private static final Range3D right = Range3D.of(Point3D.of(2, 2, 2), Point3D.of(14, 14, 14));
    private static final Range3D left = Range3D.of(Point3D.of(-2, -2, -2), Point3D.of(4, 4, 4));
    private static final Range3D out = Range3D.of(Point3D.of(-2, -2, -2), Point3D.of(-1, -1, -1));

    @Test
    void point3DCount() {
        Range3D range = Range3D.of(Point3D.ORIGIN, Point3D.of(1,1,1));
        assertEquals(8,range.point3DCount());
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