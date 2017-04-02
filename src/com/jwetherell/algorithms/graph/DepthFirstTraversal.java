import java.util.Stack;

//implemented Depth First Travesal in given "Directed graph" (Adjacany matrix)
class DepthFirstTraversal {

    public static int[] arr;
    public static int k = 0;

    public static void depthFirstTraversal(int[][] a, int[] visited,int source){
        for (int i = 0; i < visited.length; i++) {
            visited[i] = -1;
        }
        Stack<Integer>stack = new Stack();
        int element = source;       
        int i = source;
        int n = visited.length - 1;
        arr[k] = element;
        k++;
        visited[source] = 1;        
        stack.push(source);
 
        while (!stack.isEmpty()) {    
            element = stack.peek();
            i = element;    
            while (i <= n) {
                if (a[element][i] == 1 && visited[i] == -1) {
                    stack.push(i);
                    visited[i] = 1;
                    element = i;
                    i = 1;
                    arr[k] = element;
                    k++;                
                    continue;
                }
                i++;
            }
            stack.pop();    
        }
    }
    

    public static void main(String[] args) {
        int n = 5;    //no. of vertic
        int[] visited = new int[n]; 
        arr = new int[n];
        // Adjacency Matrix of graph
        int[][] a = new int[][] {{0, 1, 0, 1, 0},
                                 {0, 0, 0, 1, 1},
                                 {1, 1, 0, 1, 0},
                                 {0, 0, 0, 0, 0},
                                 {0, 0, 1, 0, 0}};
        depthFirstTraversal(a, visited, 0);        
        for (int element : arr) {
            if(element != -1)   //not direct connected 
            System.out.println(element);
        }
    }
}

