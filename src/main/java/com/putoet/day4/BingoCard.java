package com.putoet.day4;

import com.diogonunes.jcolor.Attribute;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.OptionalInt;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BLACK_TEXT;
import static com.diogonunes.jcolor.Attribute.WHITE_BACK;

class BingoCard {
    public static final int HEIGHT = 5;
    public static final int WIDTH = 5;

    private final int[][] numbers;
    private final boolean[][] marked = new boolean[HEIGHT][WIDTH];

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private OptionalInt lastCalled = OptionalInt.empty();

    public BingoCard(int[][] numbers) {
        this.numbers = numbers;
    }

    public static BingoCard of(@NotNull List<String> board) {
        assert board.size() == HEIGHT;

        final var numbers = new int[HEIGHT][WIDTH];
        for (var y = 0; y < HEIGHT; y++) {
            final var split = board.get(y).trim().split("\\s+");
            if (split.length != WIDTH)
                throw new IllegalArgumentException("Row " + y + " is invalid " + board);

            for (var x = 0; x < WIDTH; x++)
                numbers[y][x] = Integer.parseInt(split[x]);
        }

        return new BingoCard(numbers);
    }

    public int[][] numbers() {
        return numbers;
    }

    public int score() {
        if (lastCalled.isEmpty())
            throw new IllegalStateException("No numbers called yet");

        var sum = 0;
        for (var y = 0; y < HEIGHT; y++) {
            for (var x = 0; x < WIDTH; x++) {
                if (!marked[y][x])
                    sum += numbers[y][x];
            }
        }

        return sum * lastCalled.getAsInt();
    }

    public boolean call(int number) {
        lastCalled = OptionalInt.of(number);

        for (var y = 0; y < HEIGHT; y++) {
            for (var x = 0; x < WIDTH; x++) {
                if (numbers[y][x] == number)
                    marked[y][x] = true;
            }
        }

        return complete();
    }

    public boolean complete() {
        return rowComplete() || columnComplete();
    }

    public boolean rowComplete() {
        for (var y = 0; y < HEIGHT; y++) {
            var count = 0;
            for (var x = 0; x < WIDTH; x++) {
                if (marked[y][x])
                    count++;
            }
            if (count == WIDTH)
                return true;
        }

        return false;
    }

    public boolean columnComplete() {
        for (var x = 0; x < WIDTH; x++) {
            var count = 0;
            for (var y = 0; y < HEIGHT; y++) {
                if (marked[y][x])
                    count++;
            }
            if (count == HEIGHT)
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        final var BOLD = new Attribute[]{BLACK_TEXT(), WHITE_BACK()};
        final var CLEAR = new Attribute[]{};

        final var sb = new StringBuilder();
        for (var y = 0; y < HEIGHT; y++) {
            for (var x = 0; x < WIDTH; x++) {
                if (x > 0) sb.append(' ');
                sb.append(colorize(String.format("%2d", numbers[y][x]), marked[y][x] ? BOLD : CLEAR));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


