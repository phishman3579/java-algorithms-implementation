import java.util.Scanner;
import java.lang.*;

class Simpson_Rule
{
		public static void main(String arg[])
		{
			int n;
			Scanner obj=new Scanner(System.in);
			System.out.print("Enter the degree of the polynomial: ");
			n=obj.nextInt();
			//Taking input of the Polynomial:
			
			
			int coef[]=new int[n+1];
			
			System.out.println("Enter the coefficients of the desired polynomial.(MAXIMUM FIRST) ");
			
			for(int i=n;i>=0;i--)
			{
			coef[i]=obj.nextInt();
			}
			
			System.out.print("The Polynomial you Entered is f(x)= ");   //Printing the polynomial.
			for(int i=n;i>0;i--)
			{
			System.out.print(coef[i]+"x^"+i+" + ");
				
			}
			
			System.out.print(coef[0]+"\n");
			
			
			//        Given f(x)=A*x^n;
			//              f(x)dx=a*[x^(n+1)]/n+1;
			System.out.print("The integration of the given function f'(x)=");  // Printing the integration.
			for(int i=n;i>0;i--)
			{   
				System.out.print((("("+coef[i]+"/"+(i+1)+")"+"x^"+(i+1)))+" + ");
			}
			
			System.out.println(coef[0]+"x");
			

		    System.out.print("Enter the lower bound of the region: ");
		    float a=obj.nextFloat();
		    System.out.print("Enter the upper bound of the region: ");
		    float b=obj.nextFloat();
		    
		    //Applying the Simpson's rule.
		    
		    System.out.print("Enter the number of Partitions: ");
		    int p=obj.nextInt();
		    
		    float h=((b-a)/p);
		    
		    double part[]=new double[p+1];
		    for(int i=0;i<=p;i++)
		    {
		    	part[i]=a+i*h;
		    }
		    
		    
		    //aFSR == Area from Simpson's Rule;
		  double aFSR=0;
		   double t=0;
		   
		 double fnctn[]=new double[p+1];
		 
		  for(int j=0;j<=p;j++)
		  {  t=0;
		   for(int i=0;i<=n;i++)
			{  
				t+=coef[i]*Math.pow(part[j],i);	// Gives the value of the function at x;
			}
		   fnctn[j]=t;
		 
		  }
		  
		  aFSR+=(h/3)*(fnctn[0]+fnctn[p]);    // Applying Simpson's Rule.
		  for(int i=1;i<p;i++)
		  {  
			  if(i%2!=0)
			  aFSR+=(h/3)*4*fnctn[i];
		      else
			  aFSR+=(h/3)*2*fnctn[i];
		  }
		  if(aFSR>0)
	 System.out.printf("The area bounded by the curve as calculated by SIMPSON'S RULE is: %.5f",aFSR);
		  else
	 System.out.printf("The area bounded by the curve as calculated by SIMPSON'S RULE is: %.5f",(-1*aFSR));

	      double ub=0,lb=0;
	      // aFAI == Area from Actual Integration.
	      
	      
	      for(int i=n;i>=0;i--)
	      {
	    	  ub+=((coef[i]*Math.pow(b,i+1))/(i+1));
	      }
	      for(int i=n;i>=0;i--)
	      {
	    	  lb+=((coef[i]*Math.pow(a,i+1))/(i+1));
	      }
	      double aFAI=ub-lb;
	      if(aFAI>0)
	System.out.printf("\n\nThe area bounded by the curve as calculated from INTEGRATION is %.5f",aFAI);
	      else
	System.out.printf("\n\nThe area bounded by the curve as calculated from INTEGRATION is %.5f",(-1*aFAI));
		
		double error=aFSR-aFAI;
		if (error<0)
			System.out.printf("\n\nThe error in the area calculation is %.5f",(-1*error));
		else
			System.out.printf("\n\nThe error in the area calculation is %.5f ",error);
		
		}
	
}