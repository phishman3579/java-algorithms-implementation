package com.jwetherell.algorithms.data_structures.timing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import junit.framework.Assert;

import com.jwetherell.algorithms.data_structures.KdTree;
import com.jwetherell.algorithms.data_structures.QuadTree;

public class SpatialDataStructuresTiming {

    private static final int NUMBER_OF_TESTS = 3; // There will always be NUMBER_OF_TESTS+1 runs since the first round is thrown away (JITing)
    private static final Random RANDOM = new Random();
    private static final int ARRAY_SIZE = 1024*10;
    private static final int RANDOM_SIZE = 1000 * ARRAY_SIZE;

    private static final DecimalFormat FORMAT = new DecimalFormat("0.##");
    private static final int TESTS = 3;
    private static final String[] testNames = new String[TESTS]; // Array to hold the test names
    private static final long[][] testResults = new long[TESTS][]; // Array to hold the test results

    private static int[][] data = null;
    private static String stringifiedData = null;

    public static void main(String[] args) {
        System.out.println("Starting tests.");
        for (int i=0; i<NUMBER_OF_TESTS+1; i++)
            runTests(i);
    }

    private static String generateTestData(int size, int[][] unsorteds) {
        System.out.println("Generating data.");
        StringBuilder builder = new StringBuilder();
        builder.append("Array=");
        for (int i = 0; i < size; i++) {
            Integer j = RANDOM.nextInt(RANDOM_SIZE);
            Integer k = RANDOM.nextInt(RANDOM_SIZE);
            unsorteds[i] = new int[]{j,k};
            if (i!=size-1) 
                builder.append(j).append(',').append(k).append(' ');
        }
        System.out.println("Generated data.");
        return builder.toString();
    }

    private static void runTests(int round) {
        data = new int[ARRAY_SIZE][ARRAY_SIZE];
        stringifiedData = generateTestData(ARRAY_SIZE, data);
        System.out.println();

        int test = 0;

        testKdTree("KdTree", round, test++, data);
        DataStructuresTiming.putOutTheGarbage();
        System.out.println();

        QuadTree<QuadTree.XYPoint> prTree = new QuadTree.PointRegionQuadTree<QuadTree.XYPoint>(0,0,RANDOM_SIZE,RANDOM_SIZE);
        testQuadTree("PointRegionQuadTree", round, test++, 100, data, prTree);
        DataStructuresTiming.putOutTheGarbage();
        System.out.println();

        QuadTree<QuadTree.AxisAlignedBoundingBox> aaTree = new QuadTree.MxCifQuadTree<QuadTree.AxisAlignedBoundingBox>(0,0,RANDOM_SIZE,RANDOM_SIZE,10000,10000);
        testQuadTree("MxCifQuadTree", round, test++, 100, data, aaTree);
        DataStructuresTiming.putOutTheGarbage();
        System.out.println();

        if (round!=0) {
            // Skip first round to avoid JIT
            System.out.println(getTestResults(round, testNames, testResults));
        }
    }

    private static boolean testKdTree(String name, int testRound, int testNum, int[][] unsorteds) {
        if (testRound != 0) {
            // Skip first round to avoid JIT
            testNames[testNum] = name;
            if (testResults[testNum] == null)
                testResults[testNum] = new long[5];
        }

        int test = 0;

        List<KdTree.XYZPoint> points = new ArrayList<KdTree.XYZPoint>(ARRAY_SIZE);
        for (int i=0; i<ARRAY_SIZE; i++) {
            KdTree.XYZPoint p = new KdTree.XYZPoint(unsorteds[i][0],unsorteds[i][1]);
            points.add(p);
        }

        // init
        long beforeMemory = DataStructuresTiming.getMemoryUse();
        long beforeAddTime = System.nanoTime();
        KdTree<KdTree.XYZPoint> tree = new KdTree<KdTree.XYZPoint>(points, 2); // 2 since we only care about two dimensions (x,y) 
        long afterAddTime = System.nanoTime();
        long afterMemory = DataStructuresTiming.getMemoryUse();

        long memory = afterMemory - beforeMemory;
        if (testRound != 0)
            testResults[testNum][test++] += memory;
        System.out.println(name+" memory use = " + (memory / ARRAY_SIZE) + " bytes");
        long addTime = afterAddTime - beforeAddTime;
        if (testRound != 0)
            testResults[testNum][test++] += addTime;
        System.out.println(name + " add time = " + (addTime / ARRAY_SIZE) + " ns");

        // contains
        long beforeContainsTime = System.nanoTime();
        for (KdTree.XYZPoint p : points) {
            boolean r = tree.contains(p);
            assertTrue("Point not found.", p, tree, r==true);
        }
        long afterContainsTime = System.nanoTime();

        long containsTime = afterContainsTime - beforeContainsTime;
        if (testRound != 0)
            testResults[testNum][test++] += containsTime;
        System.out.println(name + " contains time = " + (containsTime / ARRAY_SIZE) + " ns");

        // nearest neighbor
        long beforeNnTime = System.nanoTime();
        for (KdTree.XYZPoint p : points) {
            Collection<KdTree.XYZPoint> c = tree.nearestNeighbourSearch(4, p);
            assertTrue("nearest neighbor didn't find anyone.", p, tree, c.size()>0);
        }
        long afterNnTime = System.nanoTime();

        long nnTime = afterNnTime - beforeNnTime;
        if (testRound != 0)
            testResults[testNum][test++] += nnTime;
        System.out.println(name + " nearest neighbor time = " + (nnTime / ARRAY_SIZE) + " ns");

        // remove
        long beforeRemovesTime = System.nanoTime();
        for (KdTree.XYZPoint p : points) {
            boolean r = tree.remove(p);
            assertTrue("Point not removed.", p, tree, r==true);
        }
        long afterRemovesTime = System.nanoTime();

        long removesTime = afterRemovesTime - beforeRemovesTime;
        if (testRound != 0)
            testResults[testNum][test++] += removesTime;
        System.out.println(name + " removes time = " + (removesTime / ARRAY_SIZE) + " ns");

        return true;
    }

    private static <Q extends QuadTree.XYPoint>  boolean testQuadTree(String name, int testRound, int testNum, int range, int[][] unsorteds, QuadTree<Q> tree) {
        if (testRound != 0) {
            // Skip first round to avoid JIT
            testNames[testNum] = name;
            if (testResults[testNum] == null)
                testResults[testNum] = new long[5];
        }

        int test = 0;

        List<QuadTree.XYPoint> points = new ArrayList<QuadTree.XYPoint>(ARRAY_SIZE);
        for (int i=0; i<ARRAY_SIZE; i++) {
            QuadTree.XYPoint p = new QuadTree.XYPoint(unsorteds[i][0],unsorteds[i][1]);
            points.add(p);
        }

        long beforeMemory = DataStructuresTiming.getMemoryUse();
        long beforeAddTime = System.nanoTime();
        for (QuadTree.XYPoint p : points)
            tree.insert(p.getX(), p.getY());
        long afterAddTime = System.nanoTime();
        long afterMemory = DataStructuresTiming.getMemoryUse();

        long memory = afterMemory - beforeMemory;
        if (testRound != 0)
            testResults[testNum][test++] += memory;
        System.out.println(name+" memory use = " + (memory / ARRAY_SIZE) + " bytes");
        long addTime = afterAddTime - beforeAddTime;
        if (testRound != 0)
            testResults[testNum][test++] += addTime;
        System.out.println(name + " add time = " + (addTime / ARRAY_SIZE) + " ns");

        // contains
        long beforeContainsTime = System.nanoTime();
        for (QuadTree.XYPoint p : points) {
            Collection<Q> l = tree.queryRange(p.getX(), p.getY(), 1, 1); // looking for a single point
            assertTrue("Point not found.", p, tree, l.size()>0);
        }
        long afterContainsTime = System.nanoTime();

        long containsTime = afterContainsTime - beforeContainsTime;
        if (testRound != 0)
            testResults[testNum][test++] += containsTime;
        System.out.println(name + " contains time = " + (containsTime / ARRAY_SIZE) + " ns");

        // query range
        long beforeQrTime = System.nanoTime();
        for (QuadTree.XYPoint p : points) {
            Collection<Q> l = tree.queryRange(p.getX(), p.getY(), range, range);
            assertTrue("Range query returned no values.", p, tree, l.size()>0);
        }
        long afterQrTime = System.nanoTime();

        long qrTime = afterQrTime - beforeQrTime;
        if (testRound != 0)
            testResults[testNum][test++] += qrTime;
        System.out.println(name + " query range time = " + (qrTime / ARRAY_SIZE) + " ns");

        // remove
        long beforeRemovesTime = System.nanoTime();
        for (QuadTree.XYPoint p : points) {
            boolean r = tree.remove(p.getX(), p.getY());
            assertTrue("Point not removed.", p, tree, r==true);
        }
        long afterRemovesTime = System.nanoTime();

        long removesTime = afterRemovesTime - beforeRemovesTime;
        if (testRound != 0)
            testResults[testNum][test++] += removesTime;
        System.out.println(name + " removes time = " + (removesTime / ARRAY_SIZE) + " ns");

        return true;
    }

    private static final String getTestResults(int number, String[] names, long[][] results) {
        StringBuilder resultsBuilder = new StringBuilder();
        String format = "%-35s %-10s %-15s %-15s %-15s %-15s\n";
        Formatter formatter = new Formatter(resultsBuilder, Locale.US);
        formatter.format(format, "Data Structure ("+ARRAY_SIZE+" items)", "Add time", "Remove time", "Lookup time", "Query", "Size");

        double KB = 1000;
        double MB = 1000 * KB;

        double MILLIS = 1000000;
        double SECOND = 1000;
        double MINUTES = 60 * SECOND;

        for (int i=0; i<TESTS; i++) {
            String name = names[i];
            long[] result = results[i];
            if (name != null && result != null) {

                double size = result[0];
                size /= number;
                String sizeString = null;
                if (size > MB) {
                    size = size / MB;
                    sizeString = FORMAT.format(size) + " MB";
                } else if (size > KB) {
                    size = size / KB;
                    sizeString = FORMAT.format(size) + " KB";
                } else {
                    sizeString = FORMAT.format(size) + " Bytes";
                }

                double addTime = result[1] / MILLIS;
                addTime /= number;
                String addTimeString = null;
                if (addTime > MINUTES) {
                    addTime /= MINUTES;
                    addTimeString = FORMAT.format(addTime) + " m";
                } else if (addTime > SECOND) {
                    addTime /= SECOND;
                    addTimeString = FORMAT.format(addTime) + " s";
                } else {
                    addTimeString = FORMAT.format(addTime) + " ms";
                }

                double lookupTime = result[2] / MILLIS;
                lookupTime /= number;
                String lookupTimeString = null;
                if (lookupTime > MINUTES) {
                    lookupTime /= MINUTES;
                    lookupTimeString = FORMAT.format(lookupTime) + " m";
                } else if (lookupTime > SECOND) {
                    lookupTime /= SECOND;
                    lookupTimeString = FORMAT.format(lookupTime) + " s";
                } else {
                    lookupTimeString = FORMAT.format(lookupTime) + " ms";
                }

                double addQueryTime = result[3] / MILLIS;
                addQueryTime /= number;
                String queryTimeString = null;
                if (addQueryTime > MINUTES) {
                    addQueryTime /= MINUTES;
                    queryTimeString = FORMAT.format(addQueryTime) + " m";
                } else if (addQueryTime > SECOND) {
                    addQueryTime /= SECOND;
                    queryTimeString = FORMAT.format(addQueryTime) + " s";
                } else {
                    queryTimeString = FORMAT.format(addQueryTime) + " ms";
                }

                double removeTime = result[4] / MILLIS;
                removeTime /= number;
                String removeTimeString = null;
                if (removeTime > MINUTES) {
                    removeTime /= MINUTES;
                    removeTimeString = FORMAT.format(removeTime) + " m";
                } else if (removeTime > SECOND) {
                    removeTime /= SECOND;
                    removeTimeString = FORMAT.format(removeTime) + " s";
                } else {
                    removeTimeString = FORMAT.format(removeTime) + " ms";
                }

                formatter.format(format, name, addTimeString, removeTimeString, lookupTimeString, queryTimeString, sizeString);
            }
        }
        formatter.close();

        return resultsBuilder.toString();
    }

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final <P extends KdTree.XYZPoint> void assertTrue(String msg, P p, KdTree<P> obj, boolean isTrue) {
        String toString = "";
        if (isTrue==false)
            toString = p.toString()+"\n"+"data=["+stringifiedData+"]\n"+obj.toString();
        Assert.assertTrue(msg+toString, isTrue);
    }

    // Assertion which won't call toString on the tree unless the assertion fails
    private static final <P extends QuadTree.XYPoint, Q extends QuadTree.XYPoint> void assertTrue(String msg, P p, QuadTree<Q> obj, boolean isTrue) {
        String toString = "";
        if (isTrue==false)
            toString = p.toString()+"\n"+"data=["+stringifiedData+"]\n"+obj.toString();
        Assert.assertTrue(msg+toString, isTrue);
    }
}
