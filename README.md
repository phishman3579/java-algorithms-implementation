java-algorithms-implementation ![alt tag](https://api.travis-ci.org/phishman3579/java-algorithms-implementation.svg?branch=master)
==============================

Algorithms and Data Structures implemented in Java

This is a collection of algorithms and data structures which I've implement over the years in my academic and professional life. The code isn't overly-optimized but is written to be correct and readable. The algorithms and data structures are well tested and, unless noted, are believe to be 100% correct.

## Table of Contents
- [Data Structures](#data-structures)
- [Mathematics](#mathematics)
- [Numbers](#numbers)
- [Graphs](#graphs)
- [Search](#search)
- [Sequences](#sequences)
- [Sorts](#sorts)
- [String Functions](#string-functions)

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
* [B-Tree](src/com/jwetherell/algorithms/data_structures/BTree.java)
* [Binary Heap (backed by an array or a tree)](src/com/jwetherell/algorithms/data_structures/BinaryHeap.java)
* [Binary Search Tree](src/com/jwetherell/algorithms/data_structures/BinarySearchTree.java)
* [Compact Suffix Trie (backed by a Patricia Trie)](src/com/jwetherell/algorithms/data_structures/CompactSuffixTrie.java)
* [Disjoint Set](src/com/jwetherell/algorithms/data_structures/DisjointSet.java)
* [Fenwick Tree {Binary Indexed Tree (BIT)}](src/com/jwetherell/algorithms/data_structures/FenwickTree.java)
* [Graph](src/com/jwetherell/algorithms/data_structures/Graph.java)
  + Undirected
  + Directed (Digraph)
* [Hash Array Mapped Trie (HAMT)](src/com/jwetherell/algorithms/data_structures/HashArrayMappedTrie.java)
* [Hash Map (associative array)](src/com/jwetherell/algorithms/data_structures/HashMap.java)
* [Interval Tree](src/com/jwetherell/algorithms/data_structures/IntervalTree.java)
* [Implicit Key Treap]((src/com/jwetherell/algorithms/data_structures/ImplicitKeyTreap.java))
* [KD Tree (k-dimensional tree or k-d tree)](src/com/jwetherell/algorithms/data_structures/KDTree.java)
* [List [backed by an array or a linked list]]((src/com/jwetherell/algorithms/data_structures/List.java))
* [Matrix](src/com/jwetherell/algorithms/data_structures/Matrix.java)
* [Patricia Trie](src/com/jwetherell/algorithms/data_structures/PatriciaTrie.java)
* [Quad-Tree (Point-Region or MX-CIF)](src/com/jwetherell/algorithms/data_structures/QuadTree.java)
* [Queue [backed by an array or a linked list]](src/com/jwetherell/algorithms/data_structures/Queue.java)
* [Radix Trie (associative array) [backed by a Patricia Trie]](src/com/jwetherell/algorithms/data_structures/RadixTree.java)
* [Red-Black Tree](src/com/jwetherell/algorithms/data_structures/RedBlackTree.java)
* [Segment Tree](src/com/jwetherell/algorithms/data_structures/SegmentTree.java)
* [Skip List](src/com/jwetherell/algorithms/data_structures/SkipList.java)
* [Splay Tree](src/com/jwetherell/algorithms/data_structures/SplayTree.java)
* [Stack [backed by an array or a linked list]](src/com/jwetherell/algorithms/data_structures/Stack.java)
* [Suffix Tree (Ukkonen's algorithm)](src/com/jwetherell/algorithms/data_structures/SuffixTree.java)
* [Suffix Trie [backed by a Trie]](src/com/jwetherell/algorithms/data_structures/SufficTrie.java)
* [Treap](src/com/jwetherell/algorithms/data_structures/Treap.java)
* [Tree Map (associative array) [backed by an AVL Tree]](src/com/jwetherell/algorithms/data_structures/TreeMap.java)
* [Trie](src/com/jwetherell/algorithms/data_structures/Trie.java)
* [Trie Map (associative array) [backed by a Trie]](src/com/jwetherell/algorithms/data_structures/TrieMap.java)

## Mathematics
* [Distance](src/com/jwetherell/algorithms/mathematics/Distance.java)
  + chebyshev
  + euclidean
* [Division](src/com/jwetherell/algorithms/mathematics/Division.java)
  + using a loop
  + using recursion
  + using shifts and multiplication
  + using only shifts
  + using logarithm
* [Multiplication](src/com/jwetherell/algorithms/mathematics/Multiplication.java)
  + using a loop
  + using recursion
  + using only shifts
  + using logarithms
  + [Fast Fourier Transform](src/com/jwetherell/algorithms/mathematics/FastFourierTransform.java)
* [Primes](src/com/jwetherell/algorithms/mathematics/Primes.java)
  + is prime
  + prime factorization
  + sieve of eratosthenes
  + [Co-Primes (relatively prime, mutually prime)](src/com/jwetherell/algorithms/mathematics/Coprimes.java)
  + [Greatest Common Divisor](src/com/jwetherell/algorithms/mathematics/GreatestCommonDivisor.java)
    - using Euclid's algorithm
    - using recursion
* [Permutations](src/com/jwetherell/algorithms/mathematics/Permutations.java)
  + strings
  + numbers
* [Modular arithmetic](src/com/jwetherell/algorithms/mathematics/Modular.java)
  + add
  + subtract
  + multiply
  + divide
  + power
* [Knapsack](src/com/jwetherell/algorithms/mathematics/Knapsack.java) 
* [Ramer Douglas Peucker](src/com/jwetherell/algorithms/mathematics/RamerDouglasPeucker.java)   

## Numbers
* [Integers](src/com/jwetherell/algorithms/numbers/Integers.java) 
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
* [Longs](src/com/jwetherell/algorithms/numbers/Longs.java)
  + to binary String
    - using divide and modulus
    - using right shift and modulus
    - using BigDecimal
* [Complex](src/com/jwetherell/algorithms/numbers/Complex.java)
  + addition
  + subtraction
  + multiplication
  + absolute value
  + polar value
 
## Graphs
* Find shortest path(s) in a Graph from a starting Vertex
  - [Dijkstra's algorithm (non-negative weight graphs)](src/com/jwetherell/algorithms/graph/Dijkstra.java)
  - [Bellman-Ford algorithm (negative and positive weight graphs)](src/com/jwetherell/algorithms/graph/BellmanFord.java)
* Find minimum spanning tree
  - [Prim's (undirected graphs)](src/com/jwetherell/algorithms/graph/Prim.java)
  - [Kruskal's (undirected graphs)](src/com/jwetherell/algorithms/graph/Kruskal.java)
* Find all pairs shortest path
  - [Johnsons's algorithm (negative and positive weight graphs)](src/com/jwetherell/algorithms/graph/Johnsons.java)
  - [Floyd-Warshall (negative and positive weight graphs)](src/com/jwetherell/algorithms/graph/FloydWarshall.java)
* [Cycle detection](src/com/jwetherell/algorithms/graph/CycleDetection.java)
  - Depth first search while keeping track of visited Verticies
  - [Connected Components](src/com/jwetherell/algorithms/graph/ConnectedComponents.java)
* [Topological sort](src/com/jwetherell/algorithms/graph/TopologicalSort.java)
* [A* path finding algorithm](src/com/jwetherell/algorithms/graph/AStar.java)
* Maximum flow
  - [Push-Relabel](src/com/jwetherell/algorithms/graph/PushRelabel.java)
* Graph Traversal
  - [Depth First Traversal](src/com/jwetherell/algorithms/graph/DepthFirstTravesal.java)
* [Edmonds Karp](src/com/jwetherell/algorithms/graph/EdmondsKarp.java)    

## Search
* Get index of value in array
  + [Linear](src/com/jwetherell/algorithms/Sequences/LinearSearch.java)
  + [Quickselect](src/com/jwetherell/algorithms/Sequences/QuickSelect.java)
  + [Binary [sorted array input only]](src/com/jwetherell/algorithms/Sequences/BinarySearch.java)
  + [Lower bound [sorted array input only]](src/com/jwetherell/algorithms/Sequences/LpperBound.java)
  + [Upper bound [sorted array input only]](src/com/jwetherell/algorithms/Sequences/UpperBound.java)
  + Optimized binary (binary until a threashold then linear) [sorted array input only]
  + [Interpolation [sorted array input only]](src/com/jwetherell/algorithms/Sequences/InterpolationSearch.java)

## Sequences
* [Find longest common subsequence (dynamic programming)](src/com/jwetherell/algorithms/Sequences/LongestCommonSubsequence.java)
* [Find longest increasing subsequence (dynamic programming)](src/com/jwetherell/algorithms/Sequences/LongestIncreasingSubsequence.java)
* [Find number of times a subsequence occurs in a sequence (dynamic programming)](src/com/jwetherell/algorithms/Sequences/SubsequenceCounter.java)
* [Find i-th element in a Fibonacci sequence](src/com/jwetherell/algorithms/Sequences/FibonacciSequence.java)
  + using a loop
  + using recursion
  + using matrix multiplication
  + using Binet's formula
* [Find total of all elements in a sequence(Arithmetic Progression)](src/com/jwetherell/algorithms/Sequences/ArithmeticProgression.java)
  + using a loop
  + using Triangular numbers
* [Largest sum of contiguous subarray (Kadane's algorithm)](src/com/jwetherell/algorithms/Sequences/LargestSumContiguousSubarray.java)
* [Longest palin­dromic sub­se­quence (dynamic programming)](src/com/jwetherell/algorithms/Sequences/LongestPalin­dromicSub­se­quence.java)

## Sorts
* [American Flag Sort](src/com/jwetherell/algorithms/Sorts/AmericanFlagSort.java)
* [Bubble Sort](src/com/jwetherell/algorithms/Sorts/BubbleSort.java)
* [Counting Sort (Integers only)](src/com/jwetherell/algorithms/Sorts/CountingSort.java)
* [Heap Sort](src/com/jwetherell/algorithms/Sorts/HeapSort.java)
* [Insertion Sort](src/com/jwetherell/algorithms/Sorts/InsertionSort.java)
* [Merge Sort](src/com/jwetherell/algorithms/Sorts/AMergeSort.java)
* [Quick Sort](src/com/jwetherell/algorithms/Sorts/QuickSort.java)
* [Radix Sort (Integers only)](src/com/jwetherell/algorithms/Sorts/RadixSort.java)
* [Shell's Sort](src/com/jwetherell/algorithms/Sorts/ShellSort.java)

## String Functions
### [String Functions](src/com/jwetherell/algorithms/strings/StringFunctions.java)
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
* [KMP (Knuth–Morris–Pratt) Algorithm - Length of maximal prefix-suffix for each prefix](src/com/jwetherell/algorithms/strings/KnuthMorrisPratt.java)
* [String rotations](src/com/jwetherell/algorithms/strings/Rotation.java)
  + Findin lexicographically minimal string rotation
  + Findin lexicographically maximal string rotation
