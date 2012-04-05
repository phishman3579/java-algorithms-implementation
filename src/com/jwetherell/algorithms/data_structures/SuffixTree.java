package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;
import java.util.List;


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
    private static Node[] nodes = null;

    private Suffix active = null;
    

    public SuffixTree(String string) {
        SuffixTree.string = string;
        SuffixTree.characters = string.toCharArray();

        int length = string.length();
        int numberOfNodes = 2*length;
        SuffixTree.nodes = new Node[numberOfNodes];

        //root node
        active = new Suffix(0, 0, -1);
        Node.count++;
        
        for (int i=0; i<length; i++) {
            addPrefix(active,i);
        }
        Edge.printEdges(active);
    }

    private void addPrefix(Suffix active, int last_char_index) {
        int parent_node;
        int last_parent_node = -1;

        while (true) {
            Edge edge = null;
            parent_node = active.originNode;
            if (active.isExplicit()) {
                edge = Edge.find(active.originNode, SuffixTree.characters[last_char_index]);
                if (edge != null)
                    break;
            } else { 
                //implicit node, a little more complicated
                edge = Edge.find(active.originNode, SuffixTree.characters[active.firstCharIndex]);
                int span = active.lastCharIndex-active.firstCharIndex;
                if (SuffixTree.characters[edge.firstCharIndex+span+1] == SuffixTree.characters[last_char_index]) 
                    break;
                parent_node = edge.split(active);
            }
            edge = new Edge(last_char_index, SuffixTree.characters.length-1, parent_node);
            edge.insert();
            System.out.printf("Created edge to new leaf: "+edge+"\n");
            AddSuffixLink(last_parent_node, parent_node);
            last_parent_node = parent_node;
            if (active.originNode == 0) {
                System.out.printf("Can't follow suffix link, I'm at the root\n");
                active.firstCharIndex++;
            } else {
                System.out.printf("Following suffix link from node "+active.originNode+" to node "+nodes[active.originNode].suffixNode+".\n");
                active.originNode = nodes[active.originNode].suffixNode;
                System.out.printf("New prefix : "+active+"\n");
            }
            active.canonize();
        }
        AddSuffixLink( last_parent_node, parent_node );
        last_parent_node = parent_node;
        active.lastCharIndex++;  //Now the endpoint is the next active point
        active.canonize();
    };
    
    void AddSuffixLink(int last_parent, int parent) {
        if (last_parent > 0) {
            System.out.printf("Creating suffix link from node "+last_parent+" to node "+parent+".\n");
            nodes[last_parent].suffixNode = parent;
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
    
    private static class Suffix {

        private int originNode = 0;
        private int firstCharIndex = 0;
        private int lastCharIndex = 0;


        private Suffix(int node, int start, int stop) {
            originNode = node;
            firstCharIndex= start;
            lastCharIndex = stop;
        }

        private boolean isExplicit() { 
            return firstCharIndex > lastCharIndex;
        }

        private boolean isImplicit() {
            return lastCharIndex >= firstCharIndex;
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
            builder.append("origin_node=").append(originNode).append("\n");
            builder.append("first_char_index=").append(firstCharIndex).append("\n");
            builder.append("last_char_index=").append(lastCharIndex).append("\n");
            if (isImplicit()) {
                String string = SuffixTree.string.substring(firstCharIndex, lastCharIndex+1);
                builder.append("string=").append(string).append("\n");
            }
            return builder.toString();
        }
    };
    
    private static class Edge {

        private static final int HASH_TABLE_SIZE = 2179;  //A prime roughly 10% larger
        private static final Edge EDGES[] = new Edge[HASH_TABLE_SIZE];

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

        private static int hash( int node, char c ) {
            return ((node<<8)+c)%HASH_TABLE_SIZE;
        }

        private void insert() {
            int i = hash(startNode, SuffixTree.characters[firstCharIndex]);
            Edge edge = EDGES[i];
            if (edge==null) {
                edge = new Edge();
                EDGES[i] = edge;
            }
            while (EDGES[i].startNode != -1)
                i = ++i % HASH_TABLE_SIZE;
            EDGES[i] = this;
        }
        
        private void remove() {
            int i = hash(startNode, SuffixTree.characters[firstCharIndex]);
            while (EDGES[i].startNode != startNode || EDGES[i].firstCharIndex != firstCharIndex)
                i = ++i % HASH_TABLE_SIZE;
            while (true) {
                EDGES[i].startNode = -1;
                int j = i;
                while (true) {
                    i = ++i % HASH_TABLE_SIZE;
                    if (EDGES[i] == null)
                        return;
                    int r = hash(EDGES[i].startNode, SuffixTree.characters[EDGES[i].firstCharIndex]);
                    if (i>=r && r>j)
                        continue;
                    if (r>j && j>i)
                        continue;
                    if (j>i && i>=r)
                        continue;
                    break;
                }
                EDGES[j] = EDGES[i];
            }
        }
        
        private int split(Suffix s) {
            System.out.printf("Splitting edge: "+this+"\n");
            remove();
            Edge new_edge = new Edge(firstCharIndex, firstCharIndex+s.lastCharIndex-s.firstCharIndex, s.originNode);
            new_edge.insert();
            Node node = SuffixTree.nodes[new_edge.endNode];
            if (node==null) {
                node = new Node();
                SuffixTree.nodes[new_edge.endNode] = node;
            }
            SuffixTree.nodes[new_edge.endNode].suffixNode = s.originNode;
            firstCharIndex += s.lastCharIndex-s.firstCharIndex+1;
            startNode = new_edge.endNode;
            insert();
            System.out.printf("New edge: "+new_edge+"\n");
            System.out.printf("Old edge: "+this+"\n");
            return new_edge.endNode;
        }
        
        private static Edge find( int node, char c ) {
            int i = hash(node, c);
            while (true) {
                if (EDGES[i] == null) {
                    return null;
                }
                if (EDGES[i].startNode == node) {
                    if (c == SuffixTree.characters[EDGES[i].firstCharIndex]) {
                        return EDGES[i];
                    }
                }
                i = ++i % HASH_TABLE_SIZE;
            }
        }

        private static void printEdges(Suffix s) {
            System.out.printf("Hash\tStart\tEnd\tSuf\tfirst\tlast\tString\n");
            for (int j=0; j<HASH_TABLE_SIZE; j++) {
                Edge e = EDGES[j];
                if (e == null) continue;
                Node n = nodes[e.endNode];
                int suffix = (n!=null)?n.suffixNode:-1;
                System.out.printf(j+"\t"+e.startNode+"\t"+e.endNode+"\t"+suffix+"\t"+e.firstCharIndex+"\t"+e.lastCharIndex+"\t");
                int begin = e.firstCharIndex;
                int end = (s.lastCharIndex < e.lastCharIndex)?s.lastCharIndex:e.lastCharIndex;
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
            for (int j=0; j<HASH_TABLE_SIZE; j++) {
                Edge e = EDGES[j];
                if (e == null) continue;
                if (e.startNode!=start) continue;

                int begin = e.firstCharIndex;
                int end = e.lastCharIndex+1;
                String s = (SuffixTree.string.substring(begin,end));
 
                Node n = nodes[e.endNode];
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
