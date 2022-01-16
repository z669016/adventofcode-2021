package com.putoet.day25.domain;

public enum SeaCucumberType {
    RIGHT, DOWN;

    @Override
    public String toString() {
        return this == RIGHT ? ">" : "v";
    }
}
