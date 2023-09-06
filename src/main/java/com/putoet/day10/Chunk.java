package com.putoet.day10;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Stack;

class Chunk {
    public static char[] OPEN_SYMBOL = new char[] {'(', '{', '[', '<'};
    public final static String OPEN = String.valueOf(OPEN_SYMBOL);
    public static char[] CLOSE_SYMBOL = new char[] {')', '}', ']', '>'};
    public final static String CLOSE = String.valueOf(CLOSE_SYMBOL);

    public static Pair<State,String> validate(@NotNull String line) {
        assert !line.isEmpty();

        final var stack = new Stack<Integer>();
        for (var i = 0; i < line.length(); i++) {
            final var c = line.charAt(i);
            var open = OPEN.indexOf(c);

            if (open != -1)
                stack.push(open);
            else {
                final var close = CLOSE.indexOf(c);
                if (close == -1)
                    throw new IllegalStateException("Found invalid character in line " + line + " at offset " + i + "'" + c + "'");

                if (stack.isEmpty())
                    return Pair.with(State.CORRUPTED, String.valueOf(CLOSE_SYMBOL[close]));

                open = stack.pop();
                if (open != close)
                    return Pair.with(State.CORRUPTED, String.valueOf(CLOSE_SYMBOL[close]));
            }
        }

        if (!stack.isEmpty()) {
            final var sb = new StringBuilder();
            while (!stack.isEmpty())
                sb.append(CLOSE_SYMBOL[stack.pop()]);
            return Pair.with(State.INCOMPLETE, sb.toString());
        }

        return Pair.with(State.VALID, null);
    }

    public static List<String> corrupted(@NotNull List<String> lines) {
        return lines.stream()
                .map(Chunk::validate)
                .filter(pair -> pair.getValue0() == State.CORRUPTED)
                .map(Pair::getValue1)
                .toList();
    }

    public static List<String> incomplete(@NotNull List<String> lines) {
        return lines.stream()
                .map(Chunk::validate)
                .filter(pair -> pair.getValue0() == State.INCOMPLETE)
                .map(Pair::getValue1)
                .toList();
    }

    public static long corruptedScore(List<String> corrupted) {
        return corrupted.stream()
                .mapToLong(c -> switch (c) {
                    case ")" -> 3;
                    case "]" -> 57;
                    case "}" -> 1197;
                    case ">" -> 25137;
                    default -> throw new IllegalArgumentException("Invalid character in list " + corrupted);
                })
                .sum();
    }

    public static long incompleteScore(List<String> uncompleted) {
        final var scores = uncompleted.stream().map(Chunk::incompleteScore).sorted().toList();
        final var middle = scores.size() / 2;
        return scores.get(middle);
    }

    public static long incompleteScore(String uncompleted) {
        var score = 0L;
        for (var i = 0;i < uncompleted.length(); i++) {
            score *= 5;
            score += switch (uncompleted.charAt(i)) {
                case ')' -> 1;
                case ']' -> 2;
                case '}' -> 3;
                case '>' -> 4;
                default -> throw new IllegalArgumentException("Invalid character in uncompleted " + uncompleted + " at pos " + i);
            };
        }

        return score;
    }
}
