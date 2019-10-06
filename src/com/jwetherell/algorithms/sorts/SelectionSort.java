

//Complexity : O(n^2)
	public static void selection_sort(int arr[])
	    {
	        int n = arr.length;
	        for (int i = 0; i < n-1; i++)
	        {
	            int min = i;
	            for (int j = i+1; j < n; j++)
	            {
	            	if (arr[j] < arr[min])
	            	{
	            		min = j;
	            	}

	            // Swap the found minimum element with the first element
	            int temp = arr[min];
	            arr[min] = arr[i];
	            arr[i] = temp;
	            }
	        }
	        
	        for(int x: arr)
	        {
	        	System.out.print(x + " ");
	        }
	    }
