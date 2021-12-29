package com.putoet.day24;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MONADTest {
    @Test
    void run() {
        final MONAD monad = MONAD.of(ResourceLines.list("/day24.txt"));

        final long z = monad.run("13579246899999");
        System.out.println();
        System.out.println(z);
    }

    @Test
    void shortcut() {
        final MONAD monad = MONAD.of(ResourceLines.list("/day24.txt"));
        assertEquals(1, monad.shortcut("13579246899999"));
        assertEquals(0, monad.shortcut("96299896449997"));
        assertEquals(0, monad.shortcut("31162141116841"));
    }

}