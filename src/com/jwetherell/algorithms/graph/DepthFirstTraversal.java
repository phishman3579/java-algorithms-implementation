package com.jwetherell.algorithms.graph;

import java.util.Stack;

/**
 * Implemented Depth First Traversal in given the "Directed graph" (Adjacency matrix)
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 *
 */
class DepthFirstTraversal {

    public static int[] arr;
    public static int k = 0;

    public static void depthFirstTraversal(int[][] a, int[] visited,int source){
        for (int i = 0; i < visited.length; i++)
            visited[i] = -1;

        final Stack<Integer> stack = new Stack<Integer>();
        int element = source;       
        int i = source;
        int n = visited.length - 1;
        arr[k] = element;
        k++;
        visited[source] = 1;        
        stack.push(source);
 
        while (!stack.isEmpty()) {    
            element = stack.peek();
            i = element;    
            while (i <= n) {
                if (a[element][i] == 1 && visited[i] == -1) {
                    stack.push(i);
                    visited[i] = 1;
                    element = i;
                    i = 1;
                    arr[k] = element;
                    k++;                
                    continue;
                }
                i++;
            }
            stack.pop();    
        }
    }
}
