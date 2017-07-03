package com.jwetherell.algorithms.data_structures.test;

import com.jwetherell.algorithms.data_structures.LCPArray;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LCPArrayTest {

    @Test
    public void smallTest(){
        String string = "asdasdd";
        LCPArray<String> LCPArrayBuilder = new LCPArray<String>(string);
        ArrayList<Integer> LCPArray = LCPArrayBuilder.getLCPArray();
        ArrayList<Integer> result = new ArrayList<Integer>();

        result.addAll(Arrays.asList(null, 0, 3, 0, 1, 1, 0, 2));

        assertEquals(LCPArray, result);
    }

    @Test
    public void longTest(){
        String string = "aasfaasdsadasdfasdasdasdasfdasfassdfas";
        LCPArray<String> LCPArrayBuilder = new LCPArray<String>(string);
        ArrayList<Integer> LCPArray = LCPArrayBuilder.getLCPArray();
        ArrayList<Integer> result = new ArrayList<Integer>();

        result.addAll(Arrays.asList(null, 0, 3, 1, 1, 2, 8, 5, 3, 3, 2, 4, 3, 2, 0,
                6, 4, 3, 4, 1, 4, 1, 0, 2, 3, 3, 1, 0, 1, 1, 7, 4, 2, 5, 2, 1, 3, 2, 1));

        assertEquals(LCPArray, result);
    }

    @Test
    public void singleLetterTest(){
        String string = "aaaaaaaaaaaa";
        LCPArray<String> LCPArrayBuilder = new LCPArray<String>(string);
        ArrayList<Integer> LCPArray = LCPArrayBuilder.getLCPArray();
        ArrayList<Integer> result = new ArrayList<Integer>();

        result.addAll(Arrays.asList(null , 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));

        assertEquals(LCPArray, result);
    }

}