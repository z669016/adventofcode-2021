package com.putoet.day25.domain;

import com.putoet.hex.domain.Policy;
import com.putoet.hex.domain.PolicyViolation;

public class SeaCucumberTypeFactoryPolicy implements Policy<String, SeaCucumberType> {
    private static class LoadSingleton {
        static final SeaCucumberTypeFactoryPolicy INSTANCE = new SeaCucumberTypeFactoryPolicy();
    }

    private SeaCucumberTypeFactoryPolicy() {}

    @Override
    public SeaCucumberType apply(String type) {
        if (type == null || !">v".contains(type) || type.length() != 1)
            throw new PolicyViolation(String.format("Cannot convert \"%s\" into a valid sea cucumber type.", type));

        return ">".equals(type) ? SeaCucumberType.RIGHT : SeaCucumberType.DOWN;
    }

    public static SeaCucumberTypeFactoryPolicy getInstance() {
        return LoadSingleton.INSTANCE;
    }

    public static SeaCucumberType from(String type) {
        return getInstance().apply(type);
    }
}
