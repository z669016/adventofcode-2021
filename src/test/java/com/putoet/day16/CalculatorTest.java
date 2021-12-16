package com.putoet.day16;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorTest {

    @Test
    void addVersionNumbers() {
        addVersionNumbers("8A004A801A8002F478", 16);
        addVersionNumbers("620080001611562C8802118E34", 12);
        addVersionNumbers("C0015000016115A2E0802F182340", 23);
        addVersionNumbers("A0016C880162017C3686B18A3D4780", 31);
    }

    void addVersionNumbers(String hexData, long expected) {
        assertEquals(expected, Calculator.addVersionNumbers(hexData));
    }

    @Test
    void compute() {
        assertEquals(3, Calculator.compute("C200B40A82"));
        assertEquals(54, Calculator.compute("04005AC33890"));
        assertEquals(7, Calculator.compute("880086C3E88112"));
        assertEquals(9, Calculator.compute("CE00C43D881120"));
        assertEquals(1, Calculator.compute("D8005AC2A8F0"));
        assertEquals(0, Calculator.compute("F600BC2D8F"));
        assertEquals(0, Calculator.compute("9C005AC2F8F0"));
        assertEquals(1, Calculator.compute("9C0141080250320F1802104A08"));
    }
}