package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * A suffix tree is a data structure that presents the suffixes of a given string in a way that allows 
 * for a particularly fast implementation of many important string operations. 
 * 
 * http://en.wikipedia.org/wiki/Suffix_tree
 * 
 * This is adapted from http://marknelson.us/1996/08/01/suffix-trees/ which is based on the Ukkonen's algorithm.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class SuffixTree<C extends CharSequence> {

    private static String string = null;
    private static char[] characters = null;

    private static Map<Integer,Node> nodes = new HashMap<Integer,Node>();
    
    private int originNode = 0;
    private int firstCharIndex = 0;
    private int lastCharIndex = -1;

    public SuffixTree(String string) {
        SuffixTree.string = string;
        SuffixTree.characters = string.toCharArray();

        int length = string.length();
        for (int i=0; i<length; i++) {
            addPrefix(i);
        }
        Edge.printEdges(lastCharIndex);
    }

    private void addPrefix(int last_char_index) {
        int parent_node;
        int last_parent_node = -1;

        while (true) {
            Edge edge = null;
            parent_node = originNode;
            if (isExplicit()) {
                edge = Edge.find(originNode, SuffixTree.characters[last_char_index]);
                if (edge != null)
                    break;
            } else { 
                //implicit node, a little more complicated
                edge = Edge.find(originNode, SuffixTree.characters[firstCharIndex]);
                int span = lastCharIndex-firstCharIndex;
                if (SuffixTree.characters[edge.firstCharIndex+span+1] == SuffixTree.characters[last_char_index]) 
                    break;
                parent_node = edge.split(originNode,firstCharIndex,lastCharIndex);
            }
            edge = new Edge(last_char_index, SuffixTree.characters.length-1, parent_node);
            Edge.insert(edge);
            System.out.printf("Created edge to new leaf: "+edge+"\n");
            if (last_parent_node > 0) {
                System.out.printf("Creating suffix link from node "+last_parent_node+" to node "+parent_node+".\n");
                nodes.get(last_parent_node).suffixNode = parent_node;
            }
            last_parent_node = parent_node;
            if (originNode == 0) {
                System.out.printf("Can't follow suffix link, I'm at the root\n");
                firstCharIndex++;
            } else {
                System.out.printf("Following suffix link from node "+originNode+" to node "+nodes.get(originNode).suffixNode+".\n");
                originNode = nodes.get(originNode).suffixNode;
            }
            canonize();
        }
        if (last_parent_node > 0) {
            System.out.printf("Creating suffix link from node "+last_parent_node+" to node "+parent_node+".\n");
            nodes.get(last_parent_node).suffixNode = parent_node;
        }
        last_parent_node = parent_node;
        lastCharIndex++;  //Now the endpoint is the next active point
        canonize();
    };

    private boolean isExplicit() { 
        return firstCharIndex > lastCharIndex;
    }

    private void canonize() {
        if (!isExplicit()) {
            Edge edge = Edge.find(originNode, SuffixTree.characters[ firstCharIndex]);
            int edge_span = edge.lastCharIndex-edge.firstCharIndex;
            System.out.printf("Canonizing");
            while (edge_span <= (lastCharIndex-firstCharIndex)) {
                firstCharIndex = firstCharIndex + edge_span + 1;
                originNode = edge.endNode;
                System.out.printf(" "+this);
                if (firstCharIndex <= lastCharIndex) {
                    edge = Edge.find(edge.endNode, SuffixTree.characters[firstCharIndex]);
                    edge_span = edge.lastCharIndex - edge.firstCharIndex;
                }
            }
            System.out.printf(".\n");
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Suffixes of '"+SuffixTree.string+"'").append("\n");
        for (String s : Edge.getSuffixes()) {
            builder.append(s).append("\n");
        }
        return builder.toString();
    }
    
    
    private static class Node {

        private static int count = 0;
        private int suffixNode = -1;


        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("count=").append(count).append("\n");
            builder.append("suffix_node=").append(suffixNode).append("\n");
            return builder.toString();
        }
    };
    
    private static class Edge {

        private static final int HASH_SEED = 2179;  //A prime roughly 10% larger

        private static final Map<Integer,Edge> map = new TreeMap<Integer,Edge>();
        
        private int startNode = -1;
        private int endNode = 0;
        private int firstCharIndex = 0;
        private int lastCharIndex = 0;


        private Edge() { }
        
        private Edge(int init_first, int init_last, int parent_node ) {
            firstCharIndex = init_first;
            lastCharIndex = init_last;
            startNode = parent_node;
            endNode = Node.count++;
        }

        private int getKey() {
            return key(startNode, SuffixTree.characters[firstCharIndex]);
        }
        
        private static int key(int node, char c) {
            return ((node<<8)+c)%HASH_SEED;
        }

        private static void insert(Edge edge) {
            map.put(edge.getKey(), edge);
        }
        
        private static void remove(Edge edge) {
            map.remove(edge.getKey());
        }
        
        private static Edge find(int node, char c) {
            int key = key(node, c);
            return map.get(key);
        }

        private int split(int originNode, int firstCharIndex, int lastCharIndex) {
            System.out.printf("Splitting edge: "+this+"\n");
            Edge.remove(this);
            Edge new_edge = new Edge(firstCharIndex, firstCharIndex+lastCharIndex-firstCharIndex, originNode);
            Edge.insert(new_edge);
            Node node = SuffixTree.nodes.get(new_edge.endNode);
            if (node==null) {
                node = new Node();
                SuffixTree.nodes.put(new_edge.endNode, node);
            }
            SuffixTree.nodes.get(new_edge.endNode).suffixNode = originNode;
            firstCharIndex += lastCharIndex-firstCharIndex+1;
            startNode = new_edge.endNode;
            Edge.insert(this);
            System.out.printf("New edge: "+new_edge+"\n");
            System.out.printf("Old edge: "+this+"\n");
            return new_edge.endNode;
        }

        private static void printEdges(int lastCharIndex) {
            System.out.printf("Hash\tStart\tEnd\tSuf\tfirst\tlast\tString\n");
            for (int key : map.keySet()) {
                Edge e = map.get(key);
                if (e == null) continue;
                Node n = nodes.get(e.endNode);
                int suffix = (n!=null)?n.suffixNode:-1;
                System.out.printf(key+"\t"+e.startNode+"\t"+e.endNode+"\t"+suffix+"\t"+e.firstCharIndex+"\t"+e.lastCharIndex+"\t");
                int begin = e.firstCharIndex;
                int end = (lastCharIndex < e.lastCharIndex)?lastCharIndex:e.lastCharIndex;
                System.out.printf(SuffixTree.string.substring(begin, end+1));
                System.out.printf("\n");
            }
        }

        private static List<String> getSuffixes() {
            List<String> list = getSuffixes(0);
            return list;
        }
        
        private static List<String> getSuffixes(int start) {
            List<String> list = new ArrayList<String>();
            for (int key : map.keySet()) {
                Edge e = map.get(key);
                if (e == null) continue;
                if (e.startNode!=start) continue;

                int begin = e.firstCharIndex;
                int end = e.lastCharIndex+1;
                String s = (SuffixTree.string.substring(begin,end));
 
                Node n = nodes.get(e.endNode);
                if (n==null) {
                    list.add(s);
                } else {
                    List<String> list2 = getSuffixes(e.endNode);
                    for (String s2 : list2) {
                        list.add(s+s2);
                    }
                }
            }
            return list;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("start_node=").append(startNode).append("\n");
            builder.append("end_node=").append(endNode).append("\n");
            builder.append("first_char_index=").append(firstCharIndex).append("\n");
            builder.append("last_char_index=").append(lastCharIndex).append("\n");
            String string = SuffixTree.string.substring(firstCharIndex, lastCharIndex+1);
            builder.append("string=").append(string).append("\n");
            return builder.toString();
        }
    }
}
