package com.jwetherell.algorithms;

public class InstrumentationCounter {
    // increment the entry in array below corresponding to the
    // intrumentation point when it is hit. Index is calculated
    // as function_index * 100 + number_of_instr_point_in_function
    public static int[] pointHits = new int[1000];
    // add number of instrumentation points infunction in array below
    // at entry corresponding to function index
    public static int[] pointCounts = new int[] {0,0,0,0,0,0,0,0,0,0};

    /*
    Example use:

    To add instrumentation to a function a in class A:
    - Call dibs on a free function index in the github issue
        (assume we have index 7 for function a)

    - In class A import this class
        import com.jwetherell.algorithms.InstrumentationCounter;

    - Inside the code of a, add this line in every place you
      want to check for coverage (should be in each execution branch)
        InstrumentationCounter.pointHits[index]++;
      The index is calculated as described above, the first
      instrumentation point in a should be 700, the next 701...

    - When all instrumentation is added, count the total number of
      instrumentation points you've added and place that number in
      the entry of pointCounts corresponding to your function index.
      Say we've added three points to a, then the 8th (index 7) entry
      of pointCounts should be changed to 3 on line 18 in this file.

    - Finally, go to CoverageAnalyzer in the tests directory and add
      ATests.class (the class that tests your instrumented class) in
      the arguments list of the call to JUnitCore.runClasses().
     */
}
