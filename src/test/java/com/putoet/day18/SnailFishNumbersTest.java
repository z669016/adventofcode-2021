package com.putoet.day18;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SnailFishNumbersTest {

    @Test
    void of() {
        final var text = "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]";
        assertEquals(text, SnailFishNumbers.of(text).toString());
    }

    @Test
    void ofList() {
        final var list = List.of(
                "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                "[7,[5,[[3,8],[1,4]]]]",
                "[[2,[2,2]],[8,[8,1]]]",
                "[2,9]",
                "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                "[[[5,[7,4]],7],1]",
                "[[[[4,2],2],6],[8,7]]"
        );

        final var numbers = SnailFishNumbers.of(list);
        assertEquals(list, numbers.stream().map(Object::toString).toList());
    }
}