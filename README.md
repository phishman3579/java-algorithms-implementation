java-algorithms-implementation
==============================

Algorithms and Data Structures implemented in Java


This is a collection of algorithms and data structures which I've implement over the years in my academic and professional life. The code isn't overly-optimized but is written to be correct and readable. The algorithms and data structures are well tested and, unless noted, are believe to be 100% correct. All code is Java 1.6 compliant and was tested on a Intel Core 2 Duo Processor which has 2.0 GHz per core and 2 Gbytes of memory. The following VM Arguments were used: "-server -Xms1500m -Xmx1500m".

* Created by Justin Wetherell
* google: http://code.google.com/p/java-algorithms-implementation
* github: http://github.com/phishman3579/java-algorithms-implementation
* linkedIn: http://www.linkedin.com/in/phishman3579
* e-mail: phishman3579@gmail.com
* Twitter: @phishman3579

# What's been implemented:

## Data Structures
* AVL Tree
* B-Tree
* Binary Heap [backed by an array or a tree]
* Binary Search Tree
* Compact Suffix Trie [backed by a Patricia Trie]
* Graph
  + Undirected
  + Directed (Digraph)
* Hash Array Mapped Trie (HAMT)
* Hash Map (associative array)
* Interval Tree
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
* Primes
  + is prime
  + prime factorization
  + sieve of eratosthenes

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
  + greatest common divisor
    - using Euclid's algorithm
  + to English (e.g. 1 would return "one")
* Longs
  + to binary String
    - using divide and modulus
    - using right shift and modulus
    - using BigDecimal

## Path
* Find shortest path(s) in a Graph from a starting Vertex
  - Dijkstra's algorithm (non-negative weight graphs)
  - Bellman-Ford algorithm (negative and positive weight graphs)
* Find minimum spanning tree
  - Prim's (undirected graphs)
* Find all pairs shortest path
  - Johnsons's algorithm (negative and positive weight graphs)
  - Floyd-Warshall (negative and positive weight graphs)
* Cycle detection
  - Depth first search while keeping track of visited Verticies
* Topological sort

## Search
* Get index of value in array
  + Linear
  + Quickselect
  + Binary [sorted array input only]
  + Optimized binary (binary until a threashold then linear) [sorted array input only]
  + Interpolation [sorted array input only]

## Sequences
* Find longest common subsequence (dynamic programming)
* Find number of times a subsequence occurs in a sequence (dynamic programming)
* Find i-th element in a Fibonacci sequence
  + using a loop
  + using recursion
  + using matrix multiplication
  + using Binet's formula
* Find total of all elements in a sequence
  + using a loop
  + using Triangular numbers

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

