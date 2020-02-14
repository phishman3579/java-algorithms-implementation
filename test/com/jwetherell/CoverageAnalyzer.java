package com.jwetherell;

import com.jwetherell.algorithms.data_structures.test.MatrixTests;
import org.junit.runner.JUnitCore;
import com.jwetherell.algorithms.InstrumentationCounter;

public class CoverageAnalyzer {
    public static void main(String[] args) {
        // Add all files with instrumentation inside here
        JUnitCore.runClasses(
                MatrixTests.class
                );

        for (int i = 0; i < 10; i++) {
            int baseIndex = 100*i;
            int pointCount = InstrumentationCounter.pointCounts[i];
            int hitCount = 0;
            for (int j = 0; j < pointCount; j++) {
               int index = baseIndex + j;
                if (InstrumentationCounter.pointHits[index] > 0) {
                    hitCount++;
                }
            }
            if (pointCount != 0) {
                System.out.printf(
                        "Function with index %d has coverage percentage %f\n",
                        i,
                        (float) hitCount / (float) pointCount);
            } else {
                System.out.println("No instrumentation for function with index " + i);
            }
        }
    }
}