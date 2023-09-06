package com.putoet.day10;

import com.putoet.day10.Chunk;
import com.putoet.day10.State;
import com.putoet.resources.ResourceLines;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChunkTest {
    private final List<String> input = ResourceLines.list("/day10.txt");

    @Test
    void validate() {
        assertEquals(State.VALID, Chunk.validate("[]").getValue0());
        assertEquals(State.VALID, Chunk.validate("([])").getValue0());
        assertEquals(State.VALID, Chunk.validate("{()()()}").getValue0());
        assertEquals(State.VALID, Chunk.validate("[<>({}){}[([])<>]]").getValue0());
        assertEquals(State.VALID, Chunk.validate("(((((((((())))))))))").getValue0());

        Pair<State, String> pair = Chunk.validate("(]");
        assertEquals(Pair.with(State.CORRUPTED, "]"), pair);
        pair = Chunk.validate("{()()()>");
        assertEquals(Pair.with(State.CORRUPTED, ">"), pair);
        pair = Chunk.validate("((()))}");
        assertEquals(Pair.with(State.CORRUPTED, "}"), pair);
        pair = Chunk.validate("<([]){()}[{}])");
        assertEquals(Pair.with(State.CORRUPTED, ")"), pair);
    }

    @Test
    void corrupted() {
        final List<String> corrupted = Chunk.corrupted(input);
        assertEquals(26397, Chunk.corruptedScore(corrupted));
    }

    @Test
    void uncomplete() {
        final List<String> uncompleted = Chunk.incomplete(input);
        assertEquals(288957, Chunk.incompleteScore(uncompleted));
    }

    @Test
    void uncompeteScoreSingle() {
        assertEquals(288957, Chunk.incompleteScore("}}]])})]"));
        assertEquals(5566, Chunk.incompleteScore(")}>]})"));
        assertEquals(1480781, Chunk.incompleteScore("}}>}>))))"));
        assertEquals(995444, Chunk.incompleteScore("]]}}]}]}>"));
        assertEquals(294, Chunk.incompleteScore("])}>"));
    }
}