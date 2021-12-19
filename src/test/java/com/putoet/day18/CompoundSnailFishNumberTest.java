package com.putoet.day18;

import com.putoet.resources.ResourceLines;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompoundSnailFishNumberTest {
    private SnailFishNumber number;

    @BeforeEach
    void setup() {
        number = SnailFishNumbers.of(1, 2);
    }

    @Test
    void testToString() {
        assertEquals("[1,2]", number.toString());
    }

    @Test
    void parent() {
        assertNull(number.parent());
    }

    @Test
    void setParent() {
        final CompoundSnailFishNumber compound = mock(CompoundSnailFishNumber.class);
        number.setParent(compound);

        assertEquals(compound,number.parent());
    }

    @Test
    void magnitude() {
        assertEquals(7L,number.magnitude());
    }

    @Test
    void add() {
        final CompoundSnailFishNumber compound = mock(CompoundSnailFishNumber.class);
        when(compound.toString()).thenReturn("[mock]");

        final SnailFishNumber sum = number.add(compound);
        assertEquals("[[1,2],[mock]]",sum.toString());
    }

    @Test
    void canSplit() {
        assertFalse(number.canSplit());
    }

    @Test
    void split() {
        assertThrows(NotImplementedException.class, () -> number.split());
    }

    @Test
    void reduce() {
        reduceTest("[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]");
        reduceTest("[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]");
        reduceTest("[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]");
        reduceTest("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
        reduceTest("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]","[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]", "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]");
        reduceTest("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]", "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]");
        reduceTest("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]", "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]", "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]");
        reduceTest("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]", "[7,[5,[[3,8],[1,4]]]]", "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]");

        final List<SnailFishNumber> numbers = SnailFishNumbers.of(ResourceLines.list("/day19.txt"));
        SnailFishNumber root = numbers.get(0);
        for (int idx = 1; idx < numbers.size(); idx++) {
            root = root.add(numbers.get(idx));
            root.reduce();
        }
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", root.toString());
        assertEquals(3488, root.magnitude());
    }

    void reduceTest(String from, String to) {
        final SnailFishNumber number = SnailFishNumbers.of(from);
        number.reduce();
        assertEquals(to, number.toString());
    }

    void reduceTest(String first, String second, String expected) {
        SnailFishNumber number = SnailFishNumbers.of(first).add(SnailFishNumbers.of(second));
        number.reduce();
        assertEquals(expected, number.toString());
    }

    @Test
    void sample2() {
        final List<SnailFishNumber> numbers = SnailFishNumbers.of(ResourceLines.list("/day19-2.txt"));
        SnailFishNumber root = numbers.get(0);
        for (int idx = 1; idx < numbers.size(); idx++) {
            root = root.add(numbers.get(idx));
            root.reduce();
        }
        assertEquals("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]", root.toString());
        assertEquals(4140, root.magnitude());
    }

    @Test
    void max() {
        final List<String> input = ResourceLines.list("/day19-2.txt");
        long max = Long.MIN_VALUE;
        for (int a = 0; a < input.size(); a++) {
            for (int b = 0; b < input.size(); b++) {
                if (b == a)
                    continue;

                SnailFishNumber first = SnailFishNumbers.of(input.get(a));
                SnailFishNumber second = SnailFishNumbers.of(input.get(b));
                first = first.add(second);
                first.reduce();
                max = Math.max(max,first.magnitude());

                first = SnailFishNumbers.of(input.get(a));
                second = SnailFishNumbers.of(input.get(b));
                second = second.add(first);
                second.reduce();
                max = Math.max(max,second.magnitude());
            }
        }
        assertEquals(3993L,max);
    }
}