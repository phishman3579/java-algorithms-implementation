package com.jwetherell.algorithms.strings.test;

import com.jwetherell.algorithms.strings.StringInformations;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class StringInformationTest {

    @Test
    public void getPrefixSuffixes() throws Exception {
        List<Object[]> data = Arrays.asList(new Object[][]{
                {"", new ArrayList<Integer>()},
                {"a", Arrays.asList(0)},
                {"aaa", Arrays.asList(0, 1, 2)},
                {"abbabb", Arrays.asList(0, 0, 0, 1, 2, 3)},
                {"bbabbbbaab", Arrays.asList(0, 1, 0, 1, 2, 2, 2, 3, 0, 1)},
                {
                    "( ͡° ͜ʖ ͡° )( ͡° a( ͡° ͜ʖ ͡°",
                     Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                }
        });
        for(Object[] testCase: data) {
            String input = (String) testCase[0];
            List<Integer> expected = (List<Integer>) testCase[1];
            assertEquals(expected, StringInformations.getPrefSufTable(input));
        }
    }

}