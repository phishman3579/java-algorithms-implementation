import java.io.*;
import java.util.*; 
class Graph
{
    private int V;
    private int R;
    private LinkedList<Integer> adj[];
    ArrayList<Integer> trav;
    Graph(int v,int rel)
    {
        V = v;
        R = 2*rel;
        adj = new LinkedList[v];
        trav = new ArrayList<>();
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList<Integer>();
    }
    void addEdge(int l, int m)
    {
        adj[l].add(new Integer(m));
        adj[m].add(new Integer(l));
    }
    void DFSUtil(int v,boolean visited[])
    {
        visited[v] = true;
        //System.out.print(v+" ");
        trav.add(new Integer(v));
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }
    ArrayList<Integer> DFS(int v)
    {
        boolean visited[] = new boolean[V];
        DFSUtil(v, visited);

        return trav;

    }
 
    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of vertices and relations: ");
        int n = sc.nextInt();
        int rel = sc.nextInt();
        
        sc.nextLine();

        System.out.println("Enter all the vertices: ");
        String family = sc.nextLine();
        char fam[] = new char[n];
        char sorted_fam[] = new char[n];
        for(int j = 0 ; j < n ; j++) {
            fam[j] = family.charAt(2*j);
            sorted_fam[j] = fam[j];
        }

        Graph g = new Graph(n,rel);

        Arrays.sort(sorted_fam);

        System.out.println("Enter the two vertices in "+rel+" relations (space separated) : ");

        for(int j = 0 ; j <rel ; j++) {
            String relation = sc.nextLine();
            char src = relation.charAt(0);
            char dest = relation.charAt(2);
            g.addEdge(Arrays.binarySearch(sorted_fam, src),Arrays.binarySearch(sorted_fam, dest));
        }
        System.out.println("Enter the source vertex: ");
        int source = Arrays.binarySearch(sorted_fam, sc.nextLine().charAt(0));
        ArrayList<Integer> ans = g.DFS(source);

        for(int j = 0 ; j < ans.size() ; j++) {
        	System.out.print(sorted_fam[ans.get(j)] + " ");
        }

    }
}