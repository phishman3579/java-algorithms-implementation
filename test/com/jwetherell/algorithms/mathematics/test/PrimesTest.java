package com.jwetherell.algorithms.mathematics.test;

import java.util.*;

import org.junit.Test;

import com.jwetherell.algorithms.mathematics.Primes;

import static org.junit.Assert.*;

public class PrimesTest {
    /**
     *  Here is a test suite for getPrimeFactorization method
     *  This test suite has 2 test cases
     *  It covers 100% coverage according to Data Flow Criteria All-DU-Paths
     *
     * @author: hoannv41@gmail.com
     */

    @Test
    public void getPrimeFactorization01() {
        int number = 5;
        Map<Long, Long> factorization = Primes.getPrimeFactorization(number);
        Map<Long, Long> check = new HashMap<Long, Long>();
        {
            check.put(2l, 0L);
            check.put(5l, 1L);
        }
        for (Long k : factorization.keySet()) {
            Long f = factorization.get(k);
            Long c = check.get(k);
            assertTrue("PrimeFactorization error. expected=" + c + " got=" + f, (c == f));
        }
    }

    @Test
    public void getPrimeFactorization02() {
        int number = 6;
        Map<Long, Long> factorization = Primes.getPrimeFactorization(number);
        Map<Long, Long> check = new HashMap<Long, Long>();
        {
            check.put(2l, 1L);
            check.put(3l, 1L);
        }
        for (Long k : factorization.keySet()) {
            Long f = factorization.get(k);
            Long c = check.get(k);
            assertTrue("PrimeFactorization error. expected=" + c + " got=" + f, (c == f));
        }
    }

}

