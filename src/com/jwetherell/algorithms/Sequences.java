package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.sequence.TotalOfSequence;


public class Sequences {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");
    
    public static void main(String[] args) {
        //SEQUENCES
        {
            int start=14;
            int length=10000;
            System.out.println("Computing sequence total using a loop.");
            long before = System.nanoTime();
            long result = TotalOfSequence.sequenceTotalUsingLoop(start, length);
            long after = System.nanoTime();
            System.out.println("start="+start+" length="+length+" result="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
            
            System.out.println("Computing sequence total using algorithm.");
            before = System.nanoTime();
            result = TotalOfSequence.sequenceTotalUsingTriangularNumbers(start, length);
            after = System.nanoTime();
            System.out.println("start="+start+" length="+length+" result="+result);
            System.out.println("Computed in "+FORMAT.format(after-before)+" ns");
        }
    }
}
