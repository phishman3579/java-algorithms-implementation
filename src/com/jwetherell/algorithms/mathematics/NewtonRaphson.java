import java.util.Scanner;

import java.lang.*;

class NewtonRaphson
{
	
	public static void main(String arg[])
	{
		int n;
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter the degree of the polynomial: ");
		n=sc.nextInt();
		//Taking input of the Polynomial:
		
		
		int coef[]=new int[n+1];
		
		System.out.println("Enter the coefficients of the desired polynomial(MAXIMUM FIRST) : ");
		
		for(int i=n;i>=0;i--)
		{
		coef[i]=sc.nextInt();
		}
		
		System.out.print("The Polynomial you Entered is f(x)= ");
		for(int i=n;i>0;i--)
		{
		System.out.print(coef[i]+"x^"+i+" + ");
			
		}
		System.out.print(coef[0]+"\n");
		
		//factorisation of the polynomial:
		
		//                              Given f(x)=A*x^n;
		//                              f'(x)=A*n*x^(n-1);
		System.out.print("The First Derivative of the function f'(x)=");
		for(int i=n;i>1;i--)
		{
			System.out.print((coef[i]*i)+"x^"+(i-1)+" + ");
		}
		System.out.print(coef[1]+"\n");
		
		double x=5;
		double t=0;
		
		//System.out.print("f("+x+") = ");
		
		for(int i=n;i>=0;i--)
		{
			t+=coef[i]*Math.pow(x,i);	// Gives the value of the function at x;
		}
		//System.out.println(t);
	    double d=0;   // Gives value of derivative at x;
	    
	    for(int i=n;i>0;i--)
	    {
	    	d+=coef[i]*i*Math.pow(x,i-1);
		}
	   // System.out.println(d);
	    int count=0;
	    
	    while(d!=0 && count<500)
	    {  
	    	t=0;
	    	for(int i=n;i>=0;i--)
			{
				t+=coef[i]*Math.pow(x,i);	// Gives the value of the function at x;
			}
	    	
	    	if(t>-0.0000001 && t<0.0000001)
	    		{System.out.printf("The approximate Root is %.4f",x);
	    	    break;}
	    	else
	    		
		    {    
	    		  d=0;
		          for(int i=n;i>0;i--)
		          {
		        	  d+=coef[i]*i*Math.pow(x,i-1);
		          }
		          		x=x-(t/d);
		          		for(int i=n;i>0;i--)
		          		{
		          			d+=coef[i]*i*Math.pow(x,i-1);
		          		}
		          		count++;
		    }    
	    }
	    if(count==500)
	    	System.out.println("\n\n\nNo Real Roots Exist for the given function.");
	    
	   // System.out.print(d);
	    
	    
	}
	
}