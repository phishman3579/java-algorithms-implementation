
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Arrays;

public class test
{
	static class FastReader
	{
		BufferedReader br;
		StringTokenizer st;
 
		public FastReader()
		{
			br =new BufferedReader(new InputStreamReader(System.in));
		}
 
		String next()
		{
			while (st==null || (!st.hasMoreElements())) 
			{
				try
				{
					st =new StringTokenizer(br.readLine());
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
			}
			return st.nextToken();
		}
 
		int nextInt()
		{
			return Integer.parseInt(next());
		}		
	} // END OF USER-DEFINED CLASS FOR FAST INPUT 
 
/* Given Function below , 'search' implements modified form of binary search .Here ,the function returns the INDEX at which the given
number should be inserted in the given sorted array to maintain the ascending order of the elements in the sorted array 
(no matter ,no or many copies of the number is/are present in the given array) . */

	public static int search( int[] arr,int e )
	{
		int l =0 ,r =arr.length - 1 ,mid ;	
 
		while( l<r )
		{
			mid =(l+r)/2;
 
			if(e < arr[mid])
				r =mid ;
			else
				l =mid + 1 ;
		}
 
		return l ;
	} // END OF 'search' function 
 
	public static void main(String[] args)
	{
		FastReader fr =new FastReader(); //Object for the fast i/p class
 
		PrintWriter op =new PrintWriter(System.out); //Object for the fast o/p class

		int T =fr.nextInt(); //Number of test-cases

		int i ,N ,ans ,num ;

		int[] arr ;

		while(T-- > 0)
		{
			N =fr.nextInt(); //Length of the array being stored

			arr =new int[N]; //Dynamic declaration of the array

			for(i =0;i<N;i++)
				arr[i] =fr.nextInt(); //Storing the i/ps to the array

			Arrays.sort(arr); /*Sorting the array in ascending order using in-built 
								function 'sort' of 'Arrays' class */

			num =fr.nextInt(); //Nnumber to be searched in the given array 

			ans =search(arr , num); //invoking the static method 'search'

			op.println(ans);
		}
		op.flush();	op.close(); //Flushing and closing o/p stream
	} //END OF MAIN FUNCTION
} //END OF CLASS
