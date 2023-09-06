package com.putoet.day18;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Stack;

class SnailFishNumbers {
    private SnailFishNumbers() {}

    public static SnailFishNumber of(int value) {
        return new RegularSnailFishNumber(value);
    }

    public static SnailFishNumber of(int left, int right) {
        return new CompoundSnailFishNumber(of(left), of(right));
    }

    public static SnailFishNumber of(@NotNull SnailFishNumber left, @NotNull SnailFishNumber right) {
        return new CompoundSnailFishNumber(left, right);
    }

    public static SnailFishNumber of(@NotNull String text) {
        final var stack = new Stack<SnailFishNumber>();
        final var tokenizer = new SnailFishNumberTokenizer(text);
        while (tokenizer.hasNext()) {
            final var token = tokenizer.next();

            if ("[".equals(token))
                continue;

            if ("]".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalStateException("Illegal formatted snail fish number " + text);

                final var right = stack.pop();
                final var left = stack.pop();
                final var number = SnailFishNumbers.of(left, right);
                stack.push(number);
                continue;
            }

            stack.push(SnailFishNumbers.of(Integer.parseInt(token)));
        }

        if (stack.size() != 1)
            throw new IllegalStateException("Illegal formatted snail fish number " + text);

        return stack.pop();
    }

    public static List<SnailFishNumber> of(@NotNull List<String> lines) {
        return lines.stream().map(SnailFishNumbers::of).toList();
    }
}
