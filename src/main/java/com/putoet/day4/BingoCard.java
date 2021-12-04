package com.putoet.day4;

import com.diogonunes.jcolor.Attribute;

import java.util.List;
import java.util.OptionalInt;

import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.BLACK_TEXT;
import static com.diogonunes.jcolor.Attribute.WHITE_BACK;

public class BingoCard {
    public static final int HEIGHT = 5;
    public static final int WIDTH = 5;

    private final int[][] numbers;
    private final boolean[][] marked = new boolean[HEIGHT][WIDTH];

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private OptionalInt lastCalled = OptionalInt.empty();

    public BingoCard(int[][] numbers) {
        this.numbers = numbers;
    }

    public static BingoCard of(List<String> board) {
        assert board != null;
        assert board.size() == HEIGHT;

        final int[][] numbers = new int[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            final String[] split = board.get(y).trim().split("\\s+");
            if (split.length != WIDTH)
                throw new IllegalArgumentException("Row " + y + " is invalid " + board);

            for (int x = 0; x < WIDTH; x++)
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

        int sum = 0;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (!marked[y][x])
                    sum += numbers[y][x];
            }
        }

        return sum * lastCalled.getAsInt();
    }

    public boolean call(int number) {
        lastCalled = OptionalInt.of(number);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
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
        for (int y = 0; y < HEIGHT; y++) {
            int count = 0;
            for (int x = 0; x < WIDTH; x++) {
                if (marked[y][x])
                    count++;
            }
            if (count == WIDTH)
                return true;
        }

        return false;
    }

    public boolean columnComplete() {
        for (int x = 0; x < WIDTH; x++) {
            int count = 0;
            for (int y = 0; y < HEIGHT; y++) {
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
        final Attribute[] BOLD = new Attribute[]{BLACK_TEXT(), WHITE_BACK()};
        final Attribute[] CLEAR = new Attribute[]{};

        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (x > 0) sb.append(' ');
                sb.append(colorize(String.format("%2d", numbers[y][x]), marked[y][x] ? BOLD : CLEAR));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


