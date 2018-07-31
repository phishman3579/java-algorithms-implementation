import java.util.ArrayList;
import java.util.Random;
/**
 * This class is used to generate n positive numbers randomly which sum upto a given positive value
 * Currently negative sum is not supported
 * @author Vaibhav Tripathi <tripathivaibhav140@gmail.com> 
 * @version (a version number or a date)
 */
public class RandomNumbers
{
 
/**
 * @param numberOfDraws 
 *                  Number of random numbers needed
 * @param targetSum
 *                  The sum needed
 * @return An arraylist which contains n numbers which sum upto the given value
 */  
public ArrayList<Integer> n_random(int targetSum, int numberOfDraws) {
    Random r = new Random();
    ArrayList<Integer> load = new ArrayList<Integer>();

    //random numbers
    int sum = 0;
    for (int i = 0; i < numberOfDraws; i++) {
        int next = r.nextInt(targetSum) + 1;
        load.add(next);
        sum += next;
    }

    //scale to the desired target sum
    double scale = 1d * targetSum / sum;
    sum = 0;
    for (int i = 0; i < numberOfDraws; i++) {
        load.set(i, (int) (load.get(i) * scale));
        sum += load.get(i);
    }

    //take rounding issues into account
    while(sum++ < targetSum) {
        int i = r.nextInt(numberOfDraws);
        load.set(i, load.get(i) + 1);
    }

    //System.out.println("Random arraylist " + load);
    //System.out.println("Sum is "+ (sum - 1));
    return load;
}
}