package com.jwetherell.algorithms.mathematics;

public class Multiplication {
    
    public static final long multiplication(int a, int b) {
        long result = (long)a*(long)b;
        return result;
    }
    
    public static final long multiplyUsingLoop(int a, int b) {
        long result = a;
        int absB = Math.abs(b);
        for (int i=1; i<absB; i++) {
            result += a;
        }
        return (b<0)?-result:result;
    }
    
    public static final long multiplyUsingShift(int a, int b) {
        //Find the 2^b which is larger than "a" which turns out to be the 
        //ceiling of (Log base 2 of b) == numbers of digits to shift
        double logBase2 = Math.log(Math.abs(a)) / Math.log(2);
        long bits = (long)Math.ceil(logBase2);
        
        //Get the value of 2^bit
        long biggerInteger = (int)Math.pow(2, bits);
        
        //Find the difference of the bigger integer and "b"
        long difference = biggerInteger - b;
        
        //Shift "bits" to the left
        long result = a<<bits;
        
        //Subtract the difference times "a"
        result -= difference*a;
        
        return result;
    }
    
    public static final long multiplyUsingLogs(int a, int b) {
        long absA = Math.abs(a);
        long absB = Math.abs(b);
        long result = Math.round(Math.pow(10, (Math.log10(absA)+Math.log10(absB))));
        return (a>0&&b>0 || a<0&&b<0)?result:-result;
    }
}
