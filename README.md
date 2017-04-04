java-algorithms-implementation ![alt tag](https://api.travis-ci.org/phishman3579/java-algorithms-implementation.svg?branch=master)
==============================

Algorithms and Data Structures implemented in Java

This is a collection of algorithms and data structures which I've implement over the years in my academic and professional life. The code isn't overly-optimized but is written to be correct and readable. The algorithms and data structures are well tested and, unless noted, are believe to be 100% correct.

## Table of Contents
- [Data Structures](#Data-Structures)
- [Mathematics](#Mathematics)
- [Numbers](#Numbers)
- [Graphs](#Graphs)
- [Search](#Search)
- [Sequences](#Sequences)
- [Sorts](#Sorts)
- [String Functions](#String-Functions)

## * Created by Justin Wetherell

* For questions use: http://groups.google.com/forum/#!forum/java-algorithms-implementation
* Google:   http://code.google.com/p/java-algorithms-implementation
* Github:   http://github.com/phishman3579/java-algorithms-implementation
* LinkedIn: http://www.linkedin.com/in/phishman3579
* E-mail:   phishman3579@gmail.com
* Twitter:  http://twitter.com/phishman3579

## Support me with a donation

<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=phishman3579%40gmail%2ecom&lc=US&item_name=Support%20open%20source&item_number=JavaAlgorithms&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_SM%2egif%3aNonHosted" target="_new"><img border="0" alt="Donate to this project" src="https://www.paypalobjects.com/en_US/i/btn/btn_donate_SM.gif"></a>

# What's been implemented:

## Data Structures
* [AVL Tree](src/com/jwetherell/algorithms/data_structures/AVLTree.java)
* [B-Tree](java-algorithms-implementation/src/com/jwetherell/algorithms/data_structures/BTree.java)
* [Binary Heap (backed by an array or a tree)](java-algorithms-implementation/src/com/jwetherell/algorithms/data_structures/BinaryHeap.java)
* [Binary Search Tree](java-algorithms-implementation/src/com/jwetherell/algorithms/data_structures/BinarySearchTree.java)
* [Compact Suffix Trie (backed by a Patricia Trie)](java-algorithms-implementation/src/com/jwetherell/algorithms/data_structures/CompactSuffixTrie.java)
* [Disjoint Set](java-algorithms-implementation/src/com/jwetherell/algorithms/data_structures/DisjointSet.java)
* [Fenwick Tree {Binary Indexed Tree (BIT)}](java-algorithms-implementation/src/com/jwetherell/algorithms/data_structures/FenwickTree.java)
* Graph
  + Undirected
  + Directed (Digraph)
* Hash Array Mapped Trie (HAMT)
* Hash Map (associative array)
* Interval Tree
* Implicit Key Treap
* KD Tree (k-dimensional tree or k-d tree)
* List [backed by an array or a linked list]
* Matrix
* Patricia Trie
* Quad-Tree (Point-Region or MX-CIF)
* Queue [backed by an array or a linked list]
* Radix Trie (associative array) [backed by a Patricia Trie]
* Red-Black Tree
* Segment Tree
* Skip List
* Splay Tree
* Stack [backed by an array or a linked list]
* Suffix Tree (Ukkonen's algorithm)
* Suffix Trie [backed by a Trie]
* Treap
* Tree Map (associative array) [backed by an AVL Tree]
* Trie
* Trie Map (associative array) [backed by a Trie]

## Mathematics
* Distance
  + chebyshev
  + euclidean
* Division
  + using a loop
  + using recursion
  + using shifts and multiplication
  + using only shifts
  + using logarithm
* Multiplication
  + using a loop
  + using recursion
  + using only shifts
  + using logarithms
  + using FFT
* Primes
  + is prime
  + prime factorization
  + sieve of eratosthenes
  + co-primes (relatively prime, mutually prime)
  + greatest common divisor
    - using Euclid's algorithm
    - using recursion
* Permutations
  + strings
  + numbers
* Modular arithmetic
  + add
  + subtract
  + multiply
  + divide
  + power

## Numbers
* Integers
  + to binary String
    - using divide and modulus
    - using right shift and modulus
    - using BigDecimal
    - using divide and double
  + is a power of 2
    - using a loop
    - using recursion
    - using logarithm
    - using bits
  + to English (e.g. 1 would return "one")
* Longs
  + to binary String
    - using divide and modulus
    - using right shift and modulus
    - using BigDecimal
* Complex
  + addition
  + subtraction
  + multiplication
  + absolute value
  + polar value
 
## Graphs
* Find shortest path(s) in a Graph from a starting Vertex
  - Dijkstra's algorithm (non-negative weight graphs)
  - Bellman-Ford algorithm (negative and positive weight graphs)
* Find minimum spanning tree
  - Prim's (undirected graphs)
  - Kruskal's (undirected graphs)
* Find all pairs shortest path
  - Johnsons's algorithm (negative and positive weight graphs)
  - Floyd-Warshall (negative and positive weight graphs)
* Cycle detection
  - Depth first search while keeping track of visited Verticies
* Topological sort
* A* path finding algorithm
* Maximum flow
  - Push-Relabel
* Graph Traversal
  - Depth First Traversal  

## Search
* Get index of value in array
  + Linear
  + Quickselect
  + Binary [sorted array input only]
  + Lower bound [sorted array input only]
  + Upper bound [sorted array input only]
  + Optimized binary (binary until a threashold then linear) [sorted array input only]
  + Interpolation [sorted array input only]

## Sequences
* Find longest common subsequence (dynamic programming)
* Find longest increasing subsequence (dynamic programming)
* Find number of times a subsequence occurs in a sequence (dynamic programming)
* Find i-th element in a Fibonacci sequence
  + using a loop
  + using recursion
  + using matrix multiplication
  + using Binet's formula
* Find total of all elements in a sequence
  + using a loop
  + using Triangular numbers
* Largest sum of contiguous subarray (Kadane's algorithm)
* Longest palin­dromic sub­se­quence (dynamic programming)

## Sorts
* American Flag Sort
* Bubble Sort
* Counting Sort (Integers only)
* Heap Sort
* Insertion Sort
* Merge Sort
* Quick Sort
* Radix Sort (Integers only)
* Shell's Sort

## String Functions
* Reverse characters in a string
  + using additional storage (a String or StringBuilder)
  + using in-place swaps
  + using in-place XOR
* Reverse words in a string
  + using char swaps and additional storage (a StringBuilder)
  + using StringTokenizer and additional (a String)
  + using split() method and additional storage (a StringBuilder and String[])
  + using in-place swaps
* Is Palindrome
  + using additional storage (a StringBuilder)
  + using in-place symetric element compares
* Subsets of characters in a String
* Edit (Levenshtein) Distance of two Strings
* KMP (Knuth–Morris–Pratt) Algorithm - Length of maximal prefix-suffix for each prefix
* String rotations
  + Findin lexicographically minimal string rotation
  + Findin lexicographically maximal string rotation

