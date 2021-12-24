package com.putoet.day23;

import com.putoet.resources.ResourceLines;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BurrowSearchTest {

    @Test
    void bfs() {
        final Burrow burrow = Burrow.of(ResourceLines.list("/day23.txt"));
        final Optional<BurrowSearch.BurrowNode> node = BurrowSearch.bfs(burrow, BurrowSearch::goalTest, BurrowSearch::successors);

        System.out.println(node);
    }
}