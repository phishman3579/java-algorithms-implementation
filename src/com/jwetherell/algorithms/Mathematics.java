package com.jwetherell.algorithms;

import java.text.DecimalFormat;

import com.jwetherell.algorithms.mathematics.Division;
import com.jwetherell.algorithms.mathematics.Multiplication;

public class Mathematics {
    
    private static final DecimalFormat FORMAT = new DecimalFormat("#.######");
    
    public static void main(String[] args) {
        //MULTIPLICATION
        {
            int a=5;
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
            int a=Integer.MAX_VALUE/4;
            int b=a;
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
        
        //DIVISION
        {
            int a=39;
            int b=3;
            long before = System.currentTimeMillis();
            long result = Division.divisionUsingLoop(a, b);
            long after = System.currentTimeMillis();
            long check = Division.division(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"/"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Division.divisionUsingLogs(a, b);
            after = System.currentTimeMillis();
            check = Division.division(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"/"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Division.divisionUsingShift(a, b);
            after = System.currentTimeMillis();
            check = Division.division(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"/"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
            
            before = System.currentTimeMillis();
            result = Division.divisionUsingMultiplication(a, b);
            after = System.currentTimeMillis();
            check = Division.division(a,b);
            if (result != check) System.out.println("ERROR with a="+a+" b="+b+" result="+result+" check="+check);
            else System.out.println(a+"/"+b+"="+result);
            System.out.println("Computed in "+FORMAT.format(after-before));
        }
    }

}
