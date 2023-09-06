package com.putoet.day18;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnailFishNumberTokenizerTest {

    @Test
    void next() {
        final var text = "[[[[[9,8],1],2],3],4]";
        final var tokenizer = new SnailFishNumberTokenizer(text);
        assertEquals("[", tokenizer.next());
        assertEquals("[", tokenizer.next());
        assertEquals("[", tokenizer.next());
        assertEquals("[", tokenizer.next());
        assertEquals("[", tokenizer.next());
        assertEquals("9", tokenizer.next());
        assertEquals("8", tokenizer.next());
        assertEquals("]", tokenizer.next());
        assertEquals("1", tokenizer.next());
        assertEquals("]", tokenizer.next());
        assertEquals("2", tokenizer.next());
        assertEquals("]", tokenizer.next());
        assertEquals("3", tokenizer.next());
        assertEquals("]", tokenizer.next());
        assertEquals("4", tokenizer.next());
        assertEquals("]", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}