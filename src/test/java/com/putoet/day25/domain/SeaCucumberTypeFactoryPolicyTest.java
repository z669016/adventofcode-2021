package com.putoet.day25.domain;

import com.putoet.hex.domain.PolicyViolation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeaCucumberTypeFactoryPolicyTest {

    @Test
    void from() {
        assertThrows(PolicyViolation.class, () -> SeaCucumberTypeFactoryPolicy.from(null));
        assertThrows(PolicyViolation.class, () -> SeaCucumberTypeFactoryPolicy.from(""));
        assertThrows(PolicyViolation.class, () -> SeaCucumberTypeFactoryPolicy.from(">v"));
        assertThrows(PolicyViolation.class, () -> SeaCucumberTypeFactoryPolicy.from("V"));
        assertThrows(PolicyViolation.class, () -> SeaCucumberTypeFactoryPolicy.from("<"));

        assertEquals(SeaCucumberType.DOWN, SeaCucumberTypeFactoryPolicy.from("v"));
        assertEquals(SeaCucumberType.RIGHT, SeaCucumberTypeFactoryPolicy.from(">"));
    }
}