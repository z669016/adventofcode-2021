package com.putoet.day18;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

class SnailFishNumberTokenizer implements Iterator<String> {
    private final String text;
    private int offset;

    public SnailFishNumberTokenizer(@NotNull String text) {
        assert !text.isBlank();
        this.text = text.strip();
        this.offset = 0;
    }

    @Override
    public boolean hasNext() {
        return offset < text.length();
    }

    @Override
    public String next() {
        var token = text.substring(offset, offset + 1);
        if ("[".equals(token)) {
            offset++;
            return token;
        }

        if ("]".equals(token)) {
            offset++;
            skipCommas();
            return token;
        }

        final var sb = new StringBuilder();
        while (Character.isDigit(text.charAt(offset))) {
            sb.append(text.charAt(offset));
            offset++;
        }
        skipCommas();

        return sb.toString();
    }

    private void skipCommas() {
        while (offset < text.length() && text.charAt(offset) == ',') {
            offset++;
        }
    }
}
