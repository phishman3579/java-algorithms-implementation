package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.mathematics.Multiplication;

public class Mathematics {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");
    
    public static void main(String[] args) {
        {
            int a=30;
            int b=577;
            long before = System.currentTimeMillis();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.currentTimeMillis();
            long check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            System.out.println();
        }

        {
            int a=-5;
            int b=5;
            long before = System.currentTimeMillis();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.currentTimeMillis();
            long check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            System.out.println();
        }
        
        {
            int a=5;
            int b=-5;
            long before = System.currentTimeMillis();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.currentTimeMillis();
            long check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            System.out.println();
        }
        
        {
            int a=-5;
            int b=-5;
            long before = System.currentTimeMillis();
            long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.currentTimeMillis();
            long check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            System.out.println();
        }
        
        {
            int a=Integer.MAX_VALUE;
            int b=Integer.MAX_VALUE;
            long before = System.currentTimeMillis();
            Long result = Multiplication.multiplyUsingLoop(a, b);
            long after = System.currentTimeMillis();
            Long check = Multiplication.multiplication(a,b);
            if (result.compareTo(check)!=0) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingShift(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result.compareTo(check)!=0) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Multiplication.multiplyUsingLogs(a, b);
            after = System.currentTimeMillis();
            check = Multiplication.multiplication(a,b);
            if (result.compareTo(check)!=0) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"x"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            System.out.println();
        }
    }

}
