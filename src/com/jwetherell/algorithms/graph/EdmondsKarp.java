package com.jwetherell.algorithms.graph;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * In computer science, the Edmonds–Karp algorithm is an
 * implementation of the Ford–Fulkerson method for computing
 * the maximum flow in a flow network in O(V*E^2) time.
 * <p>
 * https://en.wikipedia.org/wiki/Edmonds%E2%80%93Karp_algorithm
 *
 * @author Mateusz Cianciara <e.cianciara@gmail.com>
 */
public class EdmondsKarp {
    private long[][] flow; //max flow beetween i and j verticles
    private long[][] capacity; // edge capacity
    private int[] P; //parent
    private boolean[] visited; //just for checking if visited
    private int n, m; //s - source, t - target

    public EdmondsKarp(int NumOfVerticles, int NumOfEdges) {
        this.n = NumOfVerticles;
        this.m = NumOfEdges;
        flow = new long[n][n];
        capacity = new long[n][n];
        P = new int[n];
        visited = new boolean[n];
    }

    public void AddEdge(int from, int to, long capacity) {
        assert capacity >= 0;
        this.capacity[from][to] += capacity;
    }

    public Long GetMaxFlow(int s, int t) {
        while (true) {
            boolean check = false;
            Queue<Integer> Q = new ArrayDeque<Integer>();
            Q.add(s);
            int current;
            for (int i = 0; i < this.n; ++i) {
                visited[i] = false;
            }
            visited[s] = true;
            while (!Q.isEmpty()) {
                current = Q.peek();
                if (current == t) {
                    check = true;
                    break;
                }
                Q.remove();
                for (int i = 0; i < n; ++i) {
                    if (!visited[i] && capacity[current][i] > flow[current][i]) {
                        visited[i] = true;
                        Q.add(i);
                        P[i] = current;
                    }
                }
            }
            if (check == false) break;

            long temp = capacity[P[t]][t] - flow[P[t]][t];
            for (int i = t; i != s; i = P[i]) {
                temp = Math.min(temp, (capacity[P[i]][i] - flow[P[i]][i]));
            }
            for (int i = t; i != s; i = P[i]) {
                flow[P[i]][i] += temp;
                flow[i][P[i]] -= temp;
            }
        }
        long result = 0;
        for (int i = 0; i < n; ++i) {
            result += flow[s][i];
        }
        return result;
    }

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
}
