package com.jwetherell.algorithms.data_structures;

import java.util.Random;


/**
 * The treap and the randomized binary search tree are two closely related forms of binary search tree 
 * data structures that maintain a dynamic set of ordered keys and allow binary searches among the keys. 
 * After any sequence of insertions and deletions of keys, the shape of the tree is a random variable with 
 * the same probability distribution as a random binary tree; in particular, with high probability its height 
 * is proportional to the logarithm of the number of keys, so that each search, insertion, or deletion 
 * operation takes logarithmic time to perform.
 * http://en.wikipedia.org/wiki/Treap
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class Treap {

	private static final int RANDOM_SIZE = 100; //This should be larger than the number of Nodes (inclusive)
	private static final Random RANDOM = new Random();
	private static enum DIRECTION { left, right };
	
	private Node root = null;
	
	public Treap() { }

	/* Testing method
	public void add(int priority, Character character) {
		add(new Node(null,priority,character));
	}
	*/
	
	public void add(char character) {
		add(new Node(null,character));
	}
	
	private void add(Node node) {
		System.out.println("Adding "+node.key);
		if (root==null) {
			root = node;
		} else {
			addToSubtree(root,node);
		}
		System.out.println(this.toString());
	}
	
	private void addToSubtree(Node subtreeRoot, Node node) {
		addAsBinarySearchTree(subtreeRoot,node);
		System.out.println("Added "+node.key+"\n"+this.toString());
		heapify(node);
		System.out.println("Heapify "+node.key+"\n"+this.toString());
	}
	
	private void addAsBinarySearchTree(Node subtree, Node node) {
		//Add like a binary search tree
		DIRECTION direction = null;
		Node previous = null;
		Node current = subtree;
		while (current!=null) {
			if (node.key.compareTo(current.key) <= 0) {
				//Less than or equal to -- go left
				direction = DIRECTION.left;
				previous = current;
				current = current.left;
			} else {
				//Greater than -- go right
				direction = DIRECTION.right;
				previous = current;
				current = current.right;
			}
		}
		node.parent = previous;
		if (direction==DIRECTION.left) {
			previous.left = node;
		} else {
			previous.right = node;
		}
	}
	
	private void heapify(Node current) {
		//Bubble up the heap, if needed
		Node parent = current.parent;
		while (parent!=null && current.priority>parent.priority) {
			Node parentLeft = parent.left;
			Node parentRight = parent.right;
			Node grandParent = parent.parent;

			current.parent = grandParent;
			if (parentLeft!=null && parentLeft==current) {
				//LEFT
				if (grandParent!=null) grandParent.left = current;
				parent.left = null;

				if (current.right==null) {
					current.right = parent;
					parent.parent = current;
				} else {
					Node lost = current.right;
					lost.parent = null;
					current.right = parent;
					parent.parent = current;
					addToSubtree(parent,lost);
				}
			} else if (parentRight!=null && parentRight==current) {
				//RIGHT
				if (grandParent!=null) grandParent.right = current;
				parent.right = null;
				
				if (current.left==null) {
					current.left = parent;
					parent.parent = current;
				} else {
					Node lost = current.left;
					lost.parent = null;
					current.left = parent;
					parent.parent = current;
					addToSubtree(parent,lost);
				}
			} else {
				//We really shouldn't get here
				System.err.println("YIKES!");
			}
			if (parent==root) root = current;
			parent = current.parent;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(root.toString());
		return builder.toString();
	}

	
	private static class Node {
		
		private Node parent = null;
		private Integer priority = Integer.MIN_VALUE;
		private Character key = ' ';

		private Node left = null;
		private Node right = null;
		
		private Node(Node parent, int priority, char character) {
			this.parent = parent;
			this.priority = priority;
			this.key = character;
		}

		private Node(Node parent, char character) {
			this(parent,RANDOM.nextInt(RANDOM_SIZE),character);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("priorty=").append(priority).append(" key=").append(key);
			if (parent!=null) builder.append(" parent=").append(parent.key);
			builder.append("\n");
			if (left!=null) builder.append("left=").append(left.toString()).append("\n");
			if (right!=null) builder.append("right=").append(right.toString()).append("\n");
			return builder.toString();
		}
	}
}
