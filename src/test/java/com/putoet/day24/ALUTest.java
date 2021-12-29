package com.putoet.day24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ALUTest {
    private ALU alu;

    @BeforeEach
    void setup() {
        alu = new ALU(() -> 7L);
    }

    @Test
    void inp() {
        assertEquals(0L, alu.w());
        alu.inp("w");
        assertEquals(7L, alu.w());
    }

    @Test
    void add() {
        assertEquals(0L, alu.x());
        assertEquals(0L, alu.y());

        alu.add("x", "-9");
        assertEquals(-9L, alu.x());

        alu.add("y", "17");
        assertEquals(17L, alu.y());

        alu.add("x", "y");
        assertEquals(8L, alu.x());
    }

    @Test
    void mul() {
        alu.add("z", "3");
        alu.mul("z", "5");

        assertEquals(15, alu.z());
    }

    @Test
    void div() {
        alu.add("x", "7");
        alu.div("x", "3");

        assertEquals(2, alu.x());
    }

    @Test
    void mod() {
        alu.add("x", "21");
        alu.div("x", "6");

        assertEquals(3, alu.x());
    }

    @Test
    void eql() {
        alu.add("x", "21");
        alu.add("y", "6");

        alu.eql("x", "y");
        assertEquals(0, alu.x());

        alu.add("x", "21");
        alu.eql("x", "21");
        assertEquals(1, alu.x());
    }
}