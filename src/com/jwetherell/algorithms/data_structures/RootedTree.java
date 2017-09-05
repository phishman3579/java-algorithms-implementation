package com.jwetherell.algorithms.data_structures;

import java.util.ArrayList;

/**
 * Structure for storing rooted tree which allows to find lowest common ancestor.
 *
 * @param <T> type of value stored in nodes.
 */
public class RootedTree<T> {
    private T value = null;
    private int depth = 0;
    private final ArrayList<RootedTree<T>> ancestors = new ArrayList<>();
    private final ArrayList<RootedTree<T>> children = new ArrayList<>();

    /**
     * Exception which can be thrown by lowestCommonAncestor function if two
     * nodes are in different trees.
     *
     */
    public static class NodesNotInSameTreeException extends Exception {}

    /**
     * Finds lower common ancestor of two nodes.
     *
     * Complexity O(log n) where n is the height of the tree.
     *
     * @param node1 first node
     * @param node2 second node
     * @return lower common ancestor
     * @throws NodesNotInSameTreeException if nodes don't have common root
     */
    public static <S> RootedTree<S> lowestCommonAncestor(RootedTree<S> node1, RootedTree<S> node2) throws NodesNotInSameTreeException {
        if(node1 == node2) return node1;
        else if(node1.depth < node2.depth) return lowestCommonAncestor(node2, node1);
        else if(node1.depth > node2.depth) {
            int diff = node1.depth - node2.depth;
            int jump = 0;
            while(diff > 0) {
                if(diff % 2 == 1)
                    node1 = node1.ancestors.get(jump);
                jump++;
                diff /= 2;
            }
            return lowestCommonAncestor(node1, node2);
        }
        else {
            try {
                int step = 0;
                while(1<<(step+1) <= node1.depth) step++;
                while(step >= 0) {
                    if(step < node1.ancestors.size() && node1.ancestors.get(step) != node2.ancestors.get(step)) {
                        node1 = node1.ancestors.get(step);
                        node2 = node2.ancestors.get(step);
                    }
                    step--;
                }
                return node1.ancestors.get(0);
            } catch (Exception e) {
                throw new NodesNotInSameTreeException();
            }

        }

    }

    /**
     * Creates tree with root only.
     *
     */
    public RootedTree() {

    }

    /**
     * Cretes tree with root (storing value) only.
     *
     * @param value value to be stored in root
     */
    public RootedTree(T value) {
        this.value = value;
    }

    private RootedTree(RootedTree<T> parent) {
        parent.children.add(this);
        this.ancestors.add(parent);
        this.depth = parent.depth + 1;
        int dist = 0;
        while(true) {
            try {
                this.ancestors.add(this.ancestors.get(dist).ancestors.get(dist));
                dist++;
            } catch (Exception e){
                break;
            }
        }
    }

    public RootedTree<T> setValue(T value) {
        this.value = value;
        return this;
    }

    /**
     * Creates new child for this node and returns it.
     *
     * Complexity O(log depth)
     *
     * @return added child
     */
    public RootedTree<T> addChild() {
        return new RootedTree<>(this);
    }

    /**
     * Creates new child (storing value) for this node and returns it.
     *
     * Complexity O(log depth)
     *
     * @param value value to be stored in new child
     * @return added child
     */
    public RootedTree<T> addChild(T value) {
        return addChild().setValue(value);
    }

    /**
     * Returns value stored in node.
     *
     * @return node's value.
     */
    public T getValue() {
        return value;
    }

    /**
     * Finds subtree with given value in the root.
     *
     * @param value value to be find
     * @return subtree with given value in the root
     */
    public RootedTree<T> find(T value) {
        if(this.value == null) {
            if(value == null)
                return this;
        }
        else if(this.value.equals(value))
            return this;
        for(RootedTree<T> child: children) {
            RootedTree<T> res = child.find(value);
            if(res != null)
                return res;
        }
        return null;
    }

    /**
     * Returns true if tree contains a node with given value
     *
     * @param value to be checked
     * @return true if tree contains node with given value, false otherwise
     */
    public boolean contains(T value) {
        return find(value) != null;
    }

}
