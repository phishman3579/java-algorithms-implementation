package com.jwetherell.algorithms.data_structures.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.jwetherell.algorithms.data_structures.KdTree;

public class KdTreeTests {

    @Test
    public void testKdTree() {
        java.util.List<KdTree.XYZPoint> points = new ArrayList<KdTree.XYZPoint>();
        KdTree.XYZPoint p1 = new KdTree.XYZPoint(2, 3);
        points.add(p1);
        KdTree.XYZPoint p2 = new KdTree.XYZPoint(5, 4);
        points.add(p2);
        KdTree.XYZPoint p3 = new KdTree.XYZPoint(9, 6);
        points.add(p3);
        KdTree.XYZPoint p4 = new KdTree.XYZPoint(4, 7);
        points.add(p4);
        KdTree.XYZPoint p5 = new KdTree.XYZPoint(8, 1);
        points.add(p5);
        KdTree.XYZPoint p6 = new KdTree.XYZPoint(7, 2);
        points.add(p6);
        KdTree<KdTree.XYZPoint> kdTree = new KdTree<KdTree.XYZPoint>(points);

        Collection<KdTree.XYZPoint> result = kdTree.nearestNeighbourSearch(1, p3);
        assertTrue("K-D Tree query error. query=(k=1, p=(9, 6)) returned="+result, result.contains(p3));

        KdTree.XYZPoint search = new KdTree.XYZPoint(1, 4);
        result = kdTree.nearestNeighbourSearch(4, search);
        assertTrue("K-D Tree query error. query=(k=4, p=(1, 4)) returned="+result, (result.contains(p1) &&
                                                                                    result.contains(p2) &&
                                                                                    result.contains(p4) &&
                                                                                    result.contains(p6))
        );

        kdTree.remove(p6);
        kdTree.remove(p4);
        kdTree.remove(p3);
        kdTree.remove(p5);
        kdTree.remove(p1);
        kdTree.remove(p2);
    }
}
