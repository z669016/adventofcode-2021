package com.putoet.day21;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiracDiceTest {
    private static final String BLOCK_LINE = "%2d %2d %2d";
    private static final String BLOCK = """
            %2d %2d %2d
            %2d %2d %2d
            %2d %2d %2d""";

    @Test
    void play() {
        final DiracDice game = new DiracDice(4, 8);
        game.play();

        assertEquals(745L, game.looserScore());
        assertEquals(993, game.turns());
    }

    @Test
    void experiment() {
//        for (int turn = 1; turn < 22; turn++ ) {
//            int level = (int) Math.ceil(turn / 2.0);
//            int max = level * 3;
//            System.out.println(turn + " -> (" + level + ", " + max + ")");
//        }

//        BigInteger three = BigInteger.valueOf(3);
//        BigInteger big = three;
//        for (int i = 1; i < 42; i++) {
//            big = big.multiply(three);
//            System.out.println(i + ": " + big);
//        }

        for (int level = 0; level < 5; level++) {
            if (level == 0) {
                System.out.printf(BLOCK_LINE, 1, 2, 3);
                System.out.println();
            } else {
                for (int blocks = 0; blocks < Math.pow(3, level - 1); blocks++) {
                    int bl = level;
                    System.out.printf(BLOCK,
                            bl + blocks+ 0, bl + blocks + 1, bl + blocks + 2,
                            bl+ blocks + 1, bl + blocks + 2, bl + blocks + 3,
                            bl+ blocks + 2, bl + blocks + 3, bl + blocks + 4
                    );
                    System.out.println();
                    System.out.println();
                }
            }
            System.out.println("--------");
        }

    }
}