package com.jwetherell.algorithms.mathematics;

import java.util.ArrayList;
import java.util.List;

/**
 * The Ramer–Douglas–Peucker algorithm (RDP) is an algorithm for reducing the number of points in a 
 * curve that is approximated by a series of points.
 * 
 * http://en.wikipedia.org/wiki/Ramer%E2%80%93Douglas%E2%80%93Peucker_algorithm
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class RamerDouglasPeucker {

    private RamerDouglasPeucker() { }

    private static final double sqr(double x) { 
        return Math.pow(x, 2);
    }

    private static final double distanceBetweenPoints(double vx, double vy, double wx, double wy) {
        return sqr(vx - wx) + sqr(vy - wy);
    }

    private static final double distanceToSegmentSquared(double px, double py, double vx, double vy, double wx, double wy) {
        final double l2 = distanceBetweenPoints(vx, vy, wx, wy);
        if (l2 == 0) 
            return distanceBetweenPoints(px, py, vx, vy);
        final double t = ((px - vx) * (wx - vx) + (py - vy) * (wy - vy)) / l2;
        if (t < 0) 
            return distanceBetweenPoints(px, py, vx, vy);
        if (t > 1) 
            return distanceBetweenPoints(px, py, wx, wy);
        return distanceBetweenPoints(px, py, (vx + t * (wx - vx)), (vy + t * (wy - vy)));
    }

    private static final double perpendicularDistance(Double[] v, Double[] w, Double[] p) {
        return Math.sqrt(distanceToSegmentSquared(p[0], p[1], v[0], v[1], w[0], w[1]));
    }

    private static final List<Double[]> douglasPeucker(List<Double[]> list, int s, int e, double epsilon) {
        // Find the point with the maximum distance
        double dmax = 0;
        int index = 0;

        final int start = s;
        final int end = e-1;
        for (int i=start+1; i<end; i++) {
            double d = perpendicularDistance(list.get(start), list.get(end), list.get(i)); 
            if (d > dmax) {
                index = i;
                dmax = d;
            }
        }
        // If max distance is greater than epsilon, recursively simplify
        final List<Double[]> resultList;
        if (dmax > epsilon) {
            // Recursive call
            final List<Double[]> recResults1 = douglasPeucker(list, s, index, epsilon);
            final List<Double[]> recResults2 = douglasPeucker(list, index, e, epsilon);
     
            // Build the result list
            resultList = recResults1;
            resultList.addAll(recResults2);
        } else {
            if ((end-start)>0) {
                resultList = new ArrayList<Double[]>(2);
                resultList.add(list.get(start));
                resultList.add(list.get(end));   
            } else {
                resultList = new ArrayList<Double[]>(1);
                resultList.add(list.get(start));
            }
        }
        // Return the result
        return resultList;
    }

    public static final List<Double[]> douglasPeucker(List<Double[]> list, double epsilon) {
        return douglasPeucker(list, 0, list.size(), epsilon);
    }
}
