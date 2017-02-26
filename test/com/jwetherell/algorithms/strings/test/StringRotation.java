package com.jwetherell.algorithms.strings.test;

import com.jwetherell.algorithms.strings.Rotation;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringRotation {

    private String reverse(String text) {
        return new StringBuilder(text).reverse().toString();
    }

    @Test
    public void minimalRotationTest() {
        assertEquals("",
                Rotation.getLexicographicallyMinimalRotation(""));

        assertEquals("a",
                Rotation.getLexicographicallyMinimalRotation("a"));

        assertEquals("abcdefgh",
                Rotation.getLexicographicallyMinimalRotation("abcdefgh"));

        assertEquals("abcdefgh",
                Rotation.getLexicographicallyMinimalRotation("bcdefgha"));

        assertEquals("abbbbbbb",
                Rotation.getLexicographicallyMinimalRotation("bbbbbabb"));


        StringBuilder builder = new StringBuilder();
        for(char c = 'Z'; c > 'A'; c--) {
            for(int i = 0; i<4000; i++)
                builder.append(c);
            for(char c2 = c; c2 <= 'Z'; c2++)
                builder.append(c2);
        }
        String str = builder.toString();

        assertEquals('A'+str+str,
                Rotation.getLexicographicallyMinimalRotation(str + 'A' + str));

        assertEquals('A'+str+reverse(str)+str,
                Rotation.getLexicographicallyMinimalRotation(reverse(str) + str + 'A' + str));
    }

    @Test
    public void maximalRotationTest() {
        assertEquals("",
                Rotation.getLexicographicallyMaximalRotation(""));

        assertEquals("a",
                Rotation.getLexicographicallyMaximalRotation("a"));

        assertEquals("habcdefg",
                Rotation.getLexicographicallyMaximalRotation("abcdefgh"));

        assertEquals("habcdefg",
                Rotation.getLexicographicallyMaximalRotation("habcdefg"));

        assertEquals("cbbbbbbb",
                Rotation.getLexicographicallyMaximalRotation("bbbbbcbb"));

        StringBuilder builder = new StringBuilder();
        for(char c = 'A'; c < 'Z'; c++) {
            for(int i = 0; i<4000; i++)
                builder.append(c);
            for(char c2 = c; c2 >='A'; c2--)
                builder.append(c2);
        }
        String str = builder.toString();

        assertEquals('Z'+str+str,
                Rotation.getLexicographicallyMaximalRotation(str + 'Z' + str));

        assertEquals('Z'+str+reverse(str)+str,
                Rotation.getLexicographicallyMaximalRotation(reverse(str) + str + 'Z' + str));
    }
}
