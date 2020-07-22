
import java.util.*;
import java.lang.*;

public class TrapezoidalRule {

	public static void main(String[] args) {
		
		int a,b;
		int d,n;
		int h;
		int x,y;
		int s=0;
		int sum;
		int Area;
		
		Scanner sc= new Scanner(System.in);
	    
		//Taking input of the Polynomial:
		
		System.out.print("Enter the Degree of Polynomial : ");
		d =sc.nextInt();
		System.out.print("\n");
		int coef[]=new int[d+1];
		System.out.print("Enter the Coefficients : ");
		for(int i=d;i>=0;i--){
			coef[i]=sc.nextInt();
		}
		System.out.print("\n");
		
		System.out.print("Enter the Lower Limit : ");
		a=sc.nextInt();
		System.out.print("Enter the Upper Limit : ");
		b=sc.nextInt();
		System.out.print("Enter the number of partitions : ");
		n=sc.nextInt();
		
		//Printing the entered Polynomial:
		
		System.out.print("The Polynomial you Entered is f(x)= ");
		for(int i=d;i>0;i--)
		{
		System.out.print(coef[i]+"x^"+i+" + ");
			
		}
		System.out.print(coef[0]+"\n");
		System.out.println("Upper Limit : "+b+", Lower LImit : "+a+", Partitions : "+n);
		
		//Calculation
		
		h=(b-a)/n;
		int yarr[]= new int[n+1];
		for(int i=0;i<=n;i++){
			s=0;
			x=a+i*h;
			
			for(int j=0;j<=n;j++){
				for(int k=0;k<=d;k++){
				s+=(int) (coef[j]*Math.pow(x,k));
				}
			}
			yarr[i]=s;
			
		}
		sum=yarr[0];
		for(int i=1;i<=n-1;i++){
			yarr[i]=2*yarr[i];
			sum+=yarr[i];
		}
		sum+=yarr[n];
		Area= (h*sum)/2;
		System.out.println("The Area under the Given curve : "+Area);
	}
}
