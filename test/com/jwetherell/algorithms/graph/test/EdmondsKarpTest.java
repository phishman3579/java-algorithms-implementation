package com.jwetherell.algorithms.graph.test;

import com.jwetherell.algorithms.graph.EdmondsKarp;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


/**
 * Created by mateusz on 27.02.17.
 */
public class EdmondsKarpTest {

    @Test
    public void Test1() {
        int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6;
        EdmondsKarp ek = new EdmondsKarp(7, 11);
        ek.addEdge(A, D, 3);
        ek.addEdge(D, F, 6);
        ek.addEdge(A, B, 3);
        ek.addEdge(E, B, 1);
        ek.addEdge(E, G, 1);
        ek.addEdge(F, G, 9);
        ek.addEdge(D, E, 2);
        ek.addEdge(B, C, 4);
        ek.addEdge(C, A, 3);
        ek.addEdge(C, D, 1);
        ek.addEdge(C, E, 2);
        assertTrue(ek.getMaxFlow(A, G) == 5);
    }

    @Test
    public void Test2() {
        EdmondsKarp ek = new EdmondsKarp(2, 1);
        ek.addEdge(1, 0, 10);
        assertTrue(ek.getMaxFlow(0, 1) == 0);
        assertTrue(ek.getMaxFlow(1, 0) == 10);
    }

    @Test
    public void Test3() {
        EdmondsKarp ek = new EdmondsKarp(4, 5);
        ek.addEdge(0, 1, 1000);
        ek.addEdge(0, 2, 1000);
        ek.addEdge(1, 3, 1000);
        ek.addEdge(2, 3, 1000);
        ek.addEdge(1, 2, 1);
        assertTrue(ek.getMaxFlow(0, 3) == 2000);
    }

    @Test
    public void Test4() {
        EdmondsKarp ek = new EdmondsKarp(6, 8);
        ek.addEdge(0, 1, 3);
        ek.addEdge(0, 3, 3);
        ek.addEdge(1, 3, 2);
        ek.addEdge(1, 2, 3);
        ek.addEdge(3, 4, 2);
        ek.addEdge(4, 5, 3);
        ek.addEdge(2, 4, 4);
        ek.addEdge(2, 5, 2);
        assertTrue(ek.getMaxFlow(0, 5) == 5);
    }
/*
    public static void main(String[] a) {
        int A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6;
        EdmondsKarp ek = new EdmondsKarp(7, 11);
        ek.AddEdge(A, D, 3);
        ek.AddEdge(D, F, 6);
        ek.AddEdge(A, B, 3);
        ek.AddEdge(E, B, 1);
        ek.AddEdge(E, G, 1);
        ek.AddEdge(F, G, 9);
        ek.AddEdge(D, E, 2);
        ek.AddEdge(B, C, 4);
        ek.AddEdge(C, A, 3);
        ek.AddEdge(C, D, 1);
        ek.AddEdge(C, E, 2);
        System.out.println(ek.GetMaxFlow(0, 6));
        EdmondsKarp ek2 = new EdmondsKarp(2, 1);
        ek2.AddEdge(1, 0, 10);
        System.out.println(ek2.GetMaxFlow(0, 1));
        System.out.println(ek2.GetMaxFlow(1, 0));
    }
*/
}
