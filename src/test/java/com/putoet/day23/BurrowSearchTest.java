package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BurrowSearchTest {

    @Test
    void bfs1() {
        final Burrow burrow = Burrow.of(ResourceLines.list("/day23.txt"));
        final Optional<BurrowSearch.BurrowNode> node = BurrowSearch.bfs(burrow);

        assertTrue(node.isPresent());
        assertEquals(12521, node.get().state().energyUsed());
    }

    @Test
    void bfs2() {
        final Burrow burrow = Burrow.of(ResourceLines.list("/day23-2.txt"));
        final Optional<BurrowSearch.BurrowNode> node = BurrowSearch.bfs(burrow);

        assertTrue(node.isPresent());
        assertEquals(44169, node.get().state().energyUsed());
    }
}