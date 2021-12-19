package com.putoet.day18;

import java.util.List;
import java.util.Stack;

public class SnailFishNumbers {
    private SnailFishNumbers() {}

    public static SnailFishNumber of(int value) {
        return new RegularSnailFishNumber(value);
    }

    public static SnailFishNumber of(int left, int right) {
        return new CompoundSnailFishNumber(of(left), of(right));
    }
    public static SnailFishNumber of(SnailFishNumber left, SnailFishNumber right) {
        assert left != null;
        assert right != null;

        return new CompoundSnailFishNumber(left, right);
    }

    public static SnailFishNumber of(String text) {
        assert text != null;

        final Stack<SnailFishNumber> stack = new Stack<>();
        final SnailFishNumberTokenizer tokenizer = new SnailFishNumberTokenizer(text);
        while (tokenizer.hasNext()) {
            final String token = tokenizer.next();

            if ("[".equals(token))
                continue;

            if ("]".equals(token)) {
                if (stack.size() < 2)
                    throw new IllegalStateException("Illegal formatted snail fish number " + text);

                final SnailFishNumber right = stack.pop();
                final SnailFishNumber left = stack.pop();
                final SnailFishNumber number = SnailFishNumbers.of(left, right);
                stack.push(number);
                continue;
            }

            stack.push(SnailFishNumbers.of(Integer.parseInt(token)));
        }

        if (stack.size() != 1)
            throw new IllegalStateException("Illegal formatted snail fish number " + text);

        return stack.pop();
    }

    public static List<SnailFishNumber> of(List<String> lines) {
        assert lines != null;

        return lines.stream().map(SnailFishNumbers::of).toList();
    }
}
