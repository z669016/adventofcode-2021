package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BurrowSearchTest {

    @Test
    void bfs1() {
        final var burrow = Burrow.of(ResourceLines.list("/day23.txt"));
        final var node = BurrowSearch.bfs(burrow).orElseThrow();

        assertEquals(12521, node.state().energyUsed());
    }

    @Test
    void bfs2() {
        final var burrow = Burrow.of(ResourceLines.list("/day23-2.txt"));
        final var node = BurrowSearch.bfs(burrow).orElseThrow();

        assertEquals(44169, node.state().energyUsed());
    }
}