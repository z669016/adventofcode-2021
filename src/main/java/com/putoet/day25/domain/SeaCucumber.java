package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.Entity;
import com.putoet.hex.domain.Id;

import java.util.Objects;

public record SeaCucumber(Id id, SeaCucumberType type, Point location) implements Entity, Comparable<SeaCucumber> {
    public SeaCucumber {
        assert id != null;
        assert type != null;
        assert location != null;
    }

    public char symbol() {
        return type.toString().charAt(0);
    }

    @Override
    public int compareTo(SeaCucumber other) {
        Objects.requireNonNull(other);

        final int c = Integer.compare(location.y(), other.location.y());
        return c != 0 ? c : Integer.compare(this.location.x(), other.location.x());
    }
}
