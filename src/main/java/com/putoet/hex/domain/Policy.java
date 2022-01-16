package com.putoet.hex.domain;

import java.util.function.Function;

public interface Policy<P, T> extends Function<P, T> {
}
