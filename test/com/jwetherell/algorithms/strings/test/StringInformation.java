package com.jwetherell.algorithms.strings.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.jwetherell.algorithms.strings.StringInformations;

public class StringInformation {

    @Test
    @SuppressWarnings("unchecked")
    public void getPrefixSuffixes() throws Exception {
        final List<Object[]> data = Arrays.asList(
            new Object[][]{
                {"", Arrays.asList()},
                {"a", Arrays.asList(0)},
                {"aaa", Arrays.asList(0, 1, 2)},
                {"abbabb", Arrays.asList(0, 0, 0, 1, 2, 3)},
                {"bbabbbbaab", Arrays.asList(0, 1, 0, 1, 2, 2, 2, 3, 0, 1)},
                {
                    "( ͡° ͜ʖ ͡° )( ͡° a( ͡° ͜ʖ ͡°",
                     Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                }
            }
        );
        for(Object[] testCase: data) {
            final String input = (String) testCase[0];
            final List<Integer> expected = (List<Integer>) testCase[1];
            assertEquals(expected, StringInformations.getPrefSufTable(input));
        }
    }
}