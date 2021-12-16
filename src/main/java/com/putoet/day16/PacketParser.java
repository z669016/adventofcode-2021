package com.putoet.day16;

import org.javatuples.Pair;

import java.util.Iterator;
import java.util.stream.Collectors;

public class PacketParser implements Iterator<Pair<Token, Long>> {
    public static final int LITERAL_VALUE = 4;
    public static final int LENGTH_TYPE_11 = '1';

    private final String binaryData;
    private int offset;
    private ParserState state;

    private PacketParser(String binaryData) {
        this.binaryData = binaryData;
        this.state = ParserState.BEFORE_VERSION;
    }

    public String data() {
        return binaryData;
    }

    @Override
    public String toString() {
        return "{offset=" + offset + ", data=" + binaryData.substring(offset) + "}";
    }

    public static PacketParser ofHexData(String hexData) {
        final String data = hexData.chars().mapToObj(i -> hexToBinary((char) i)).collect(Collectors.joining());
        return new PacketParser(data);
    }

    public static PacketParser ofBinaryData(String binaryData) {
        return new PacketParser(binaryData);
    }

    public static String hexToBinary(char hex) {
        assert (hex >= '0' && hex <= '9') || (hex >= 'A' && hex <= 'F');

        final int value = Integer.parseInt(String.valueOf(hex), 16);
        final StringBuilder bin = new StringBuilder(Integer.toBinaryString(value));
        while (bin.length() < 4)
            bin.insert(0, "0");

        return bin.toString();
    }

    @Override
    public boolean hasNext() {
        return offset < binaryData.length();
    }

    @Override
    public Pair<Token, Long> next() {
        final Pair<Token, Long> pair = nextToken();
//        System.out.println("[" + pair.getValue0() + "," + pair.getValue1() + "]");
        return pair;
    }

    public PacketParser subPacket(long length) {
        assert length < Integer.MAX_VALUE;

        if (length > binaryData.length() - offset)
            throw new IllegalStateException("Binary data left is " + (binaryData.length() - offset)
                    + " bits, no packet of " + length + " available");

        final String data = binaryData.substring(offset, offset + (int) length);
        offset += length;
        return PacketParser.ofBinaryData(data);
    }

    public Pair<Token, Long> nextToken() {
        return switch(state) {
            case BEFORE_VERSION -> {
                if (trailingZeroes(binaryData.substring(offset))) {
                    offset = Integer.MAX_VALUE;
                    yield Pair.with(Token.END, -1L);
                }
                final String data = binaryData.substring(offset,offset + 3);
                offset += 3;
                state = ParserState.BEFORE_TYPE_ID;
                yield Pair.with(Token.VERSION, Long.parseLong(data, 2));
            }
            case BEFORE_TYPE_ID -> {
                final String data = binaryData.substring(offset,offset + 3);
                final long value = Long.parseLong(data, 2);
                offset += 3;

                if (value == LITERAL_VALUE)
                    state = ParserState.BEFORE_VALUE;
                else {
                    if (binaryData.charAt(offset) == LENGTH_TYPE_11)
                        state = ParserState.BEFORE_LENGTH_TYPE_11;
                    else
                        state = ParserState.BEFORE_LENGTH_TYPE_15;
                    offset++;
                }

                yield Pair.with(Token.TYPE_ID, value);
            }
            case BEFORE_VALUE -> {
                final StringBuilder sb = new StringBuilder();
                String subPacket = binaryData.substring(offset, offset + 5);
                offset += 5;
                while (subPacket.charAt(0) == '1') {
                    sb.append(subPacket.substring(1));
                    subPacket = binaryData.substring(offset, offset + 5);
                    offset += 5;
                }
                sb.append(subPacket.substring(1));
                state = ParserState.BEFORE_VERSION;
                yield Pair.with(Token.LITERAL_VALUE, Long.parseLong(sb.toString(),2));
            }
            case BEFORE_LENGTH_TYPE_11 -> {
                final String data = binaryData.substring(offset, offset + 11);
                offset += 11;
                state = ParserState.BEFORE_VERSION;
                yield Pair.with(Token.SUB_PACKET_COUNT, Long.parseLong(data, 2));
            }
            case BEFORE_LENGTH_TYPE_15 -> {
                final String data = binaryData.substring(offset,offset + 15);
                offset += 15;
                state = ParserState.BEFORE_VERSION;
                yield Pair.with(Token.SUB_PACKET_LENGTH, Long.parseLong(data, 2));
            }
        };
    }

    private boolean trailingZeroes(String zeroes) {
        return zeroes.matches("^0*$");
    }
}
