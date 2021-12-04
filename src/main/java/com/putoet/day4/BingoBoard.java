package com.putoet.day4;

import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;

public class BingoBoard {
    public static final int HEIGHT = 5;
    public static final int WIDTH = 5;
    public static String BOLD = "\033[0;1m";
    public static String PLAIN = "\033[0;0m";
    private final int[][] numbers;
    private final boolean[][] marked = new boolean[HEIGHT][WIDTH];
    private OptionalInt lastCalled = OptionalInt.empty();

    public BingoBoard(int[][] numbers) {
        this.numbers = numbers;
    }

    public static BingoBoard of(List<String> board) {
        Objects.requireNonNull(board);
        assert board.size() == HEIGHT;

        final int[][] numbers = new int[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            final String[] split = board.get(y).trim().split("\\s+");
            if (split.length != WIDTH)
                throw new IllegalArgumentException("Row " + y + " is invalid " + board);

            for (int x = 0; x < WIDTH; x++)
                numbers[y][x] = Integer.parseInt(split[x]);
        }

        return new BingoBoard(numbers);
    }

    public int[][] numbers() {
        return numbers;
    }

    public OptionalInt lastDraw() {
        return lastCalled;
    }

    public int score() {
        if (lastDraw().isEmpty())
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
            if (count == WIDTH)
                return true;
        }

        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (x > 0) sb.append(' ');
                if (marked[y][x]) sb.append(BOLD);
                sb.append(String.format("%02d", numbers[y][x]));
                if (marked[y][x]) sb.append(PLAIN);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}


