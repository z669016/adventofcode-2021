package com.putoet.day25.domain;

import com.putoet.grid.Point;
import com.putoet.hex.domain.PolicyViolation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaCucumberFactoryPolicyTest {

    @Test
    void from() {
        assertThrows(PolicyViolation.class, () -> SeaCucumberFactoryPolicy.from("", Point.ORIGIN));
        assertThrows(PolicyViolation.class, () -> SeaCucumberFactoryPolicy.from("v", null));
    }
}