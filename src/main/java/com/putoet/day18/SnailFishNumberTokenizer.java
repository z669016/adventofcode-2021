package com.putoet.day18;

import java.util.Iterator;

public class SnailFishNumberTokenizer implements Iterator<String> {
    private final String text;
    private int offset;

    public SnailFishNumberTokenizer(String text) {
        assert text != null && text.strip().length() > 0;
        this.text = text.strip();
        this.offset = 0;
    }

    @Override
    public boolean hasNext() {
        return offset < text.length();
    }

    @Override
    public String next() {
        String token = text.substring(offset, offset + 1);
        if ("[".equals(token)) {
            offset++;
            return token;
        }

        if ("]".equals(token)) {
            offset++;
            skipCommas();
            return token;
        }

        final StringBuilder sb = new StringBuilder();
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
