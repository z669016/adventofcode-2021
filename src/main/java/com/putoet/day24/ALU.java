package com.putoet.day24;

import java.util.function.Supplier;

public class ALU {
    private final Supplier<Long> input;

    private long w, x, y, z;

    public ALU(Supplier<Long> input) {
        this.input = input;
    }

    public long w() {
        return w;
    }

    public long x() {
        return x;
    }

    public long y() {
        return y;
    }

    public long z() {
        return z;
    }

    protected void nop() {}

    protected void inp(String a) {
        to(a, input.get());
    }

    protected void add(String a, String b) {
        to(a, from(a) + from(b));
    }

    protected void mul(String a, String b) {
        to(a, from(a) * from(b));
    }

    protected void div(String a, String b) {
        to(a, from(a) / from(b));
    }

    protected void mod(String a, String b) {
        to(a, from(a) % from(b));
    }

    protected void eql(String a, String b) {
        to(a, from(a) == from(b) ? 1 : 0);
    }

    private long from(String reg) {
        return switch (reg) {
            case "w" -> w;
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> Long.parseLong(reg);
        };
    }

    private void to(String reg, long value) {
        switch (reg) {
            case "w" -> w = value;
            case "x" -> x = value;
            case "y" -> y = value;
            case "z" -> z = value;
            default -> throw new IllegalArgumentException("Invalid register " + reg);
        }
    }
}
