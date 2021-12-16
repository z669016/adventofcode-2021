package com.putoet.day16;

import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PacketParserTest {

    @Test
    void hexToBinary() {
        assertThrows(AssertionError.class, () -> PacketParser.hexToBinary('a'));

        assertEquals("0000", PacketParser.hexToBinary('0'));
        assertEquals("0001", PacketParser.hexToBinary('1'));
        assertEquals("0010", PacketParser.hexToBinary('2'));
        assertEquals("0011", PacketParser.hexToBinary('3'));
        assertEquals("0100", PacketParser.hexToBinary('4'));
        assertEquals("0101", PacketParser.hexToBinary('5'));
        assertEquals("0110", PacketParser.hexToBinary('6'));
        assertEquals("0111", PacketParser.hexToBinary('7'));
        assertEquals("1000", PacketParser.hexToBinary('8'));
        assertEquals("1001", PacketParser.hexToBinary('9'));
        assertEquals("1010", PacketParser.hexToBinary('A'));
        assertEquals("1011", PacketParser.hexToBinary('B'));
        assertEquals("1100", PacketParser.hexToBinary('C'));
        assertEquals("1101", PacketParser.hexToBinary('D'));
        assertEquals("1110", PacketParser.hexToBinary('E'));
        assertEquals("1111", PacketParser.hexToBinary('F'));
    }

    @Test
    void sample1() {
        final String binaryData = "110100101111111000101000";
        final PacketParser parser = PacketParser.ofBinaryData(binaryData);

        expectNext(Token.VERSION, 6, parser);
        expectNext(Token.TYPE_ID, 4, parser);
        expectNext(Token.LITERAL_VALUE, 2021, parser);
        expectNext(Token.END, -1, parser);

        assertFalse(parser.hasNext());
    }

    @Test
    void sample2() {
        final String operatorPacket = "38006F45291200";
        final PacketParser parser = PacketParser.ofHexData(operatorPacket);

        assertEquals("00111000000000000110111101000101001010010001001000000000", parser.data());
        expectNext(Token.VERSION, 1, parser);
        expectNext(Token.TYPE_ID, 6, parser);
        expectNext(Token.SUB_PACKET_LENGTH, 27, parser);

        final PacketParser sub = parser.subPacket(27);
        expectNext(Token.END, -1, parser);

        expectNext(Token.VERSION, 6, sub);
        expectNext(Token.TYPE_ID, 4, sub);
        expectNext(Token.LITERAL_VALUE, 10, sub);

        expectNext(Token.VERSION, 2, sub);
        expectNext(Token.TYPE_ID, 4, sub);
        expectNext(Token.LITERAL_VALUE, 20, sub);

        expectNext(Token.END, -1, sub);
    }

    @Test
    void sample3() {
        final String operatorPacket = "EE00D40C823060";
        final PacketParser parser = PacketParser.ofHexData(operatorPacket);

        assertEquals("11101110000000001101010000001100100000100011000001100000", parser.data());
        expectNext(Token.VERSION, 7, parser);
        expectNext(Token.TYPE_ID, 3, parser);
        expectNext(Token.SUB_PACKET_COUNT, 3, parser);

        expectNext(Token.VERSION, 2, parser);
        expectNext(Token.TYPE_ID, 4, parser);
        expectNext(Token.LITERAL_VALUE, 1, parser);

        expectNext(Token.VERSION, 4, parser);
        expectNext(Token.TYPE_ID, 4, parser);
        expectNext(Token.LITERAL_VALUE, 2, parser);

        expectNext(Token.VERSION, 1, parser);
        expectNext(Token.TYPE_ID, 4, parser);
        expectNext(Token.LITERAL_VALUE, 3, parser);

        expectNext(Token.END, -1, parser);
    }

    private Pair<Token, Long> expectNext(Token token, long value, PacketParser parser) {
        final Pair<Token, Long> pair = parser.next();
        assertEquals(token, pair.getValue0());
        assertEquals(value, pair.getValue1());

        return pair;
    }
}