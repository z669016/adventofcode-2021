package com.putoet.day24;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

record MONAD(String[] instructions) {
    public MONAD {
        assert instructions != null;
    }

    public static MONAD of(@NotNull List<String> lines) {
        return new MONAD(lines.toArray(new String[0]));
    }

    public long run(@NotNull String number) {
        assert number.length() == 14;
        assert number.matches("^\\d+$");

        final ALU alu = new ALU(new Supplier<>() {
            int offset = 0;

            @Override
            public Long get() {
                return (long) (number.charAt(offset++) - '0');
            }
        });

        for (var ip = 0; ip < instructions.length; ip++) {
            if (instructions[ip].isEmpty())
                continue;

            final var split = instructions[ip].split(" ");
            switch (split[0]) {
                case "#" -> alu.nop();
                case "inp" -> alu.inp(split[1]);
                case "add" -> alu.add(split[1], split[2]);
                case "mul" -> alu.mul(split[1], split[2]);
                case "div" -> alu.div(split[1], split[2]);
                case "mod" -> alu.mod(split[1], split[2]);
                case "eql" -> alu.eql(split[1], split[2]);
                default -> throw new IllegalArgumentException("Invalid instruction " + instructions[ip] + " at line " + ip);
            }
        }

        return alu.z();
    }

    public long shortcut(@NotNull String number) {
        // push digit(1) + 12
        // push digit(2) + 7
        // push digit(3) + 8
        // push digit(4) + 8
        // push digit(5) + 15
        // check digit(6) = pop - 16 ==> digit(6) == digit(5) + 15 - 16 == digit(5) - 1
        // push digit(7) + 8
        // check digit(8) == pop - 11 ==> digit(8) == digit(7) + 8 - 11 == digit(7) - 3
        // check digit(9) == pop - 13 ==> digit(9) == digit(4) + 8 - 13 == digit(4) - 5
        // push digit(10) + 13
        // check digit(11) == pop - 8 ==> digit(11) == digit(10) + 13 - 8 == digit(10) + 5
        // check digit(12) == pop - 1 ==> digit(12) == digit(3) + 8 - 1 == digit(3) + 7
        // check digit(13) == pop - 4 ==> digit(13) == digit(2) + 7 - 4 == digit(2) + 3
        // check digit(14) == pop - 14 ==> digit(14) == digit(1) + 12 - 14 == digit(1) - 2

        final var digit = IntStream.range(0, number.length()).map(i -> number.charAt(i) - '0').toArray();
        System.out.println();
        if (digit[5] == digit[4] - 1 &&
                digit[7] == digit[6] - 3 &&
                digit[8] == digit[3] - 5 &&
                digit[10] == digit[9] + 5 &&
                digit[11] == digit[2] + 7 &&
                digit[12] == digit[1] + 3 &&
                digit[13] == digit[0] - 2)
            return 0;

        return 1;
    }
}
