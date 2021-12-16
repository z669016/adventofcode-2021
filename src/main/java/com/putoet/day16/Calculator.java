package com.putoet.day16;

import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;
import java.util.Set;

public class Calculator {
    public static final int SUM = 0;
    public static final int PRODUCT = 1;
    public static final int MINIMUM = 2;
    public static final int MAXIMUM = 3;
    public static final int LITERAL_VALUE = 4;
    public static final int GREATER_THAN = 5;
    public static final int LESS_THAN = 6;
    public static final int EQUAL_TO = 7;

    public static long compute(String hexData) {
        return computeOne(PacketParser.ofHexData(hexData));
    }

    private static long computeOne(PacketParser parser) {
        Pair<Token, Long> pair = parser.next();

        if (pair.getValue0() == Token.VERSION)
            pair = parser.next();

        if (pair.getValue0() != Token.TYPE_ID)
            throw new IllegalStateException("unexpected token type encountered");

        final int typeId = (int) pair.getValue1().longValue();
        return switch (typeId) {
            case SUM -> {
                final List<Long> sum = compute(parser);
                yield sum.stream().mapToLong(v -> v).sum();
            }
            case PRODUCT -> {
                final List<Long> mul = compute(parser);
                yield mul.stream().mapToLong(v -> v).reduce(1, (a, b) -> a * b);
            }
            case MINIMUM -> {
                final List<Long> minList = compute(parser);
                final OptionalLong min = minList.stream().mapToLong(v -> v).min();
                if (min.isEmpty())
                    throw new IllegalStateException("Cannot determine minimum for " + minList);
                yield min.getAsLong();
            }
            case MAXIMUM -> {
                final List<Long> maxList = compute(parser);
                final OptionalLong max = maxList.stream().mapToLong(v -> v).max();
                if (max.isEmpty())
                    throw new IllegalStateException("Cannot determine minimum for " + maxList);
                yield max.getAsLong();
            }
            case LITERAL_VALUE -> parser.next().getValue1();
            case GREATER_THAN -> {
                final List<Long> gt = compute(parser);
                if (gt.size() != 2)
                    throw new IllegalStateException("Invalid GT package encountered");
                yield gt.get(0) > gt.get(1) ? 1L : 0L;
            }
            case LESS_THAN -> {
                final List<Long> lt = compute(parser);
                if (lt.size() != 2)
                    throw new IllegalStateException("Invalid LT package encountered");
                yield lt.get(0) < lt.get(1) ? 1L : 0L;
            }
            case EQUAL_TO -> {
                final List<Long> eq = compute(parser);
                if (eq.size() != 2)
                    throw new IllegalStateException("Invalid EQ package encountered");
                yield eq.get(0).longValue() == eq.get(1).longValue() ? 1L : 0L;
            }
            default -> throw new IllegalStateException("Invalid type id " + typeId);
        };
    }

    private static List<Long> compute(PacketParser parser) {
        final List<Long> values = new ArrayList<>();
        final Set<Token> skip = Set.of(Token.VERSION, Token.TYPE_ID, Token.END);

        Pair<Token, Long> pair = parser.next();
        while (parser.hasNext() && skip.contains(pair.getValue0()))
            pair = parser.next();

        switch (pair.getValue0()) {
            case LITERAL_VALUE -> values.add(pair.getValue1());
            case SUB_PACKET_LENGTH -> {
                final PacketParser sub = parser.subPacket(pair.getValue1());
                while (sub.hasNext()) {
                    values.add(computeOne(sub));
                }
            }
            case SUB_PACKET_COUNT -> {
                final int count = (int) pair.getValue1().longValue();
                for (int i = 0; i < count; i++) {
                    values.add(computeOne(parser));
                }
            }
        }
        return values;
    }

    public static long addVersionNumbers(String hexData) {
        return addVersionNumbers(PacketParser.ofHexData(hexData));
    }

    public static long addVersionNumbers(PacketParser parser) {
        long count = 0;
        while (parser.hasNext()) {
            final Pair<Token, Long> pair = parser.next();
            switch (pair.getValue0()) {
                case VERSION:
                    count += pair.getValue1();
                    break;

                case SUB_PACKET_LENGTH:
                    count += addVersionNumbers(parser.subPacket(pair.getValue1()));
                    break;

                case SUB_PACKET_COUNT:
                    for (int i = 0; i < pair.getValue1(); i++) {
                        count += addVersionNumbers(parser);
                    }
            }
        }
        return count;
    }
}
