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

    private static Pair<Token, Long> pairWith(Token token, String binaryData) {
        return Pair.with(token, Long.parseLong(binaryData, 2));
    }

    public String data() {
        return binaryData;
    }

    @Override
    public String toString() {
        return "{offset=" + offset + ", data=" + binaryData.substring(offset) + "}";
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

    @Override
    public boolean hasNext() {
        return offset < binaryData.length();
    }

    @Override
    public Pair<Token, Long> next() {
        return switch (state) {
            case BEFORE_VERSION -> {
                if (trailingZeroes()) {
                    offset = Integer.MAX_VALUE;
                    yield Pair.with(Token.END, -1L);
                }
                state = ParserState.BEFORE_TYPE_ID;
                yield pairWith(Token.VERSION, takeVersion());
            }
            case BEFORE_TYPE_ID -> {
                final Pair<Token, Long> pair = pairWith(Token.TYPE_ID, takeTypeId());

                if (pair.getValue1() == LITERAL_VALUE)
                    state = ParserState.BEFORE_LITERAL_VALUE;
                else {
                    if (binaryData.charAt(offset) == LENGTH_TYPE_11)
                        state = ParserState.BEFORE_LENGTH_TYPE_11;
                    else
                        state = ParserState.BEFORE_LENGTH_TYPE_15;
                    offset++;
                }

                yield pair;
            }
            case BEFORE_LITERAL_VALUE -> {
                state = ParserState.BEFORE_VERSION;
                yield pairWith(Token.LITERAL_VALUE, takeLiteralValue());
            }
            case BEFORE_LENGTH_TYPE_11 -> {
                state = ParserState.BEFORE_VERSION;
                yield pairWith(Token.SUB_PACKET_COUNT, takeLength11());
            }
            case BEFORE_LENGTH_TYPE_15 -> {
                state = ParserState.BEFORE_VERSION;
                yield pairWith(Token.SUB_PACKET_LENGTH, takeLength15());
            }
        };
    }

    private String takeLength15() {
        final String data = binaryData.substring(offset, offset + 15);
        offset += 15;
        return data;
    }

    private String takeLength11() {
        final String data = binaryData.substring(offset, offset + 11);
        offset += 11;
        return data;
    }

    private String takeLiteralValue() {
        final StringBuilder sb = new StringBuilder();
        boolean last;
        do {
            final String valueBlock = takeValueBlock();
            sb.append(valueBlock.substring(1));
            last = valueBlock.charAt(0) == '0';
        } while (!last);
        return sb.toString();
    }

    private String takeValueBlock() {
        final String valueBlock = binaryData.substring(offset, offset + 5);
        offset += 5;
        return valueBlock;
    }

    private String takeTypeId() {
        final String data = binaryData.substring(offset, offset + 3);
        offset += 3;
        return data;
    }

    private String takeVersion() {
        final String data = binaryData.substring(offset, offset + 3);
        offset += 3;
        return data;
    }

    private boolean trailingZeroes() {
        return offset == Integer.MAX_VALUE || binaryData.substring(offset).matches("^0*$");
    }

    private enum ParserState {
        BEFORE_VERSION,
        BEFORE_TYPE_ID,
        BEFORE_LITERAL_VALUE,
        BEFORE_LENGTH_TYPE_15,
        BEFORE_LENGTH_TYPE_11
    }
}
